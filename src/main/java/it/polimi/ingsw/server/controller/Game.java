package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.subclasses.Error;
import it.polimi.ingsw.communication.message.subclasses.*;
import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;
import javafx.scene.control.Alert;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.random.RandomGenerator;

/**
 * This class encapsulates the controller. It is used to perform commands, as well as checking whehter or not the game is
 * over, and checking for the validity of a command.
 * Upon performing a command, an update is returned to its GameInterface, which will proceed to notify
 * all players of the action.
 */
public class Game {

    private final static Logger logger = Logger.getLogger(Game.class);
    private final Board board;
    private final Turn turn;
    private final int MAX_STUDENTS_TO_MOVE;
    private final boolean isLastTurn = false;
    //FIXME: implement for Ending position, out of resources..
    private int movedStudents = 0;

    public Game(GameType gameType, List<String> usernames) {
        this.board = BoardFactory.getBoard(usernames, gameType.expertMode, RandomGenerator.getDefault().nextLong());
        this.turn = board.getTurn();
        MAX_STUDENTS_TO_MOVE = (gameType.nPlayer == 3) ? 4 : 3;
    }

    public Game(GameType gameType, List<String> usernames, long seed) {
        this.board = BoardFactory.getBoard(usernames, gameType.expertMode, seed);
        this.turn = board.getTurn();
        MAX_STUDENTS_TO_MOVE = (gameType.nPlayer == 3) ? 4 : 3;
    }

    /**
     * Checks if the {@link Command#username()} is the current player.
     * if he is the current player he can play the command on the model.
     *
     * @return either an error message addressed to the single player or
     * an update message for all the player in the game
     * @see Turn#getCurrentPlayer()
     */
    public @NotNull Map<String, Message> executeCommand(@NotNull Command command) {
        if (!command.username().equals(turn.getCurrentPlayer()))
            return errorMessage(command.username(), new Exception("You can't play! It's " + turn.getCurrentPlayer() + "'s turn"));
        return switch (command.getType()) {
            case PLAY_CARD -> playCardCommand(command);
            case MOVE_STUDENT_TO_CASTLE -> moveStudentToDiningRoomCommand(command);
            case MOVE_STUDENT_TO_ISLAND -> moveStudentToIslandCommand(command);
            case MOVE_MOTHER_NATURE -> moveMotherNatureCommand(command);
            case CHOOSE_CLOUD -> chooseCloudCommand(command);
            case PAY_CHARACTER -> payCharCommand(command);
        };
    }

    private @NotNull Map<String, Message> playCardCommand(@NotNull Command command) {
        try {
            board.playCard(command.username(), command.getCardId());
        } catch (IllegalArgumentException | PhaseNotRightException e) {
            logger.info(e);
            return errorMessage(command.username(), e);
        }
        turn.changePhase();
        return updateAll();
    }

    private @NotNull Map<String, Message> moveStudentToDiningRoomCommand(@NotNull Command command) {
        List<StudentColor> students = command.getStudents();
        String playerID = command.username();
        movedStudents += students.size();
        if (movedStudents > MAX_STUDENTS_TO_MOVE) {
            movedStudents -= students.size();
            return Map.of(playerID, new Error("You are trying to move too many students"));
        }
        try {
            board.moveStudentsToDiningRoom(playerID, students);
        } catch (NoSuchStudentException | TooManyStudentsException | PhaseNotRightException e) {
            logger.info(e);
            movedStudents -= students.size();
            return errorMessage(playerID, e);
        }
        if (movedStudents == MAX_STUDENTS_TO_MOVE) {
            movedStudents = 0;
            turn.changePhase();
        }
        return updateAll();
    }

    private @NotNull Map<String, Message> moveStudentToIslandCommand(@NotNull Command command) {
        List<StudentColor> students = command.getStudents();
        movedStudents += students.size();
        if (movedStudents > MAX_STUDENTS_TO_MOVE) {
            movedStudents -= students.size();
            return Map.of(command.username(), new Error("you are trying to move too many students"));
        }
        try {
            board.moveStudentToIsland(command.username(), command.getIslandId() - 1, command.getStudents());
        } catch (NoSuchStudentException | PhaseNotRightException e) {
            logger.info(e);
            return errorMessage(command.username(), e);
        }
        if (movedStudents == MAX_STUDENTS_TO_MOVE) {
            movedStudents = 0;
            turn.changePhase();
        }
        return updateAll();
    }

    private @NotNull Map<String, Message> moveMotherNatureCommand(@NotNull Command command) {
        try {
            board.moveMotherNature(command.getMotherNaturePositionShift());
        } catch (PhaseNotRightException | IllegalArgumentException e) {
            logger.info(e);
            return errorMessage(command.username(), e);
        }
        if (turn.isLastTurn())
            board.endOfRound();
        if (board.isWinningState() || board.isEndGame()) {
            try {
                return winUpdate(board.getWinner());
            } catch (DrawException e) {
                logger.info(e);
                return errorMessage(command.username(), e);
            }
        }
        turn.changePhase();
        return updateAll();
    }

    private @NotNull Map<String, Message> chooseCloudCommand(@NotNull Command command) {
        try {
            board.chooseCloud(command.username(), command.getCloudId() - 1);
            logger.info(board.getData(command.username()));
        } catch (TooManyStudentsException | PhaseNotRightException | IllegalArgumentException e) {
            logger.info(e);
            return errorMessage(command.username(), e);
        }
        board.endOfRound();
        turn.changePhase();
        return updateAll();
    }

    private @NotNull Map<String, Message> payCharCommand(@NotNull Command command) {
        try {
            board.playExpertCard(command.getCharId(), command.getIslandId() - 1, command.getStudents());
        } catch (WrongGameModeException | CoinException | StudentException | PhaseNotRightException |
                 RuntimeException e) {
            logger.info(e);
            return errorMessage(command.username(), e);
        }
        return updateAll();
    }

    public @NotNull Map<String, Message> updateAll() {
        Map<String, Message> usernameMessageMap = new HashMap<>();
        board.getCastleMap()
                .keySet()
                .forEach(i ->
                        usernameMessageMap.put(i, new Update(board.getData(i))));
        return usernameMessageMap;
    }

    private @NotNull Map<String, Message> winUpdate(@Nullable Team winner) {
        Map<String, Message> usernameMessageMap = new HashMap<>();
                    board.getCastleMap()
                            .keySet()
                            .forEach(i -> {
                                    usernameMessageMap.put(i, new EndGame("The game is over. Winner: " + winner +
                                        "\nDo you want to play another game? (y/n)", Alert.AlertType.INFORMATION, i, winner, board.getData(i)));
                            });
        return usernameMessageMap;
    }

    private @NotNull Map<String, Message> errorMessage(@NotNull String addressee, @NotNull Throwable error) {
        logger.info("game command error", error);
        return Map.of(addressee, new Error(error.getMessage()));
    }

    public Board getBoard() {
        return board;
    }

    public Turn getTurn() {
        return turn;
    }
}