package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.subclasses.Error;
import it.polimi.ingsw.communication.message.subclasses.*;
import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Game {

    private final static Logger logger = Logger.getLogger(Game.class);
    private final Board board;
    private final Turn turn;
    private final int MAX_STUDENTS_TO_MOVE;
    private int movedStudents;
    private boolean isLastTurn = false; //FIXME: implement for Ending position, out of resources.

    public Game(GameType gameType, List<String> usernames) {
        this.board = BoardFactory.getBoard(usernames, gameType.expertMode);
        this.turn = board.getTurn();
        this.movedStudents = 0;
        MAX_STUDENTS_TO_MOVE = (gameType.nPlayer == 3) ? 4 : 3;
        updateAll();
    }

    public @NotNull MessageUsernameSet executeCommand(@NotNull Command command) {
        return switch (command.getType()) {
            case PLAY_CARD -> playCardCommand(command);
            case MOVE_STUDENT_TO_CASTLE -> moveStudentToDiningRoomCommand(command);
            case MOVE_STUDENT_TO_ISLAND -> moveStudentToIslandCommand(command);
            case MOVE_MOTHER_NATURE -> moveMotherNatureCommand(command);
            case CHOOSE_CLOUD -> chooseCloudCommand(command);
            case PAY_CHARACTER -> payCharCommand(command);
            };
    }


    private @NotNull MessageUsernameSet playCardCommand(@NotNull Command command) {
        try {
            board.playCard(command.getUsername(), command.getCardId());
        } catch (NotYourTurnException | IllegalArgumentException | PhaseNotRightException e) {
            return errorMessage(e, command.getUsername());
        }
        return updateAll();
    }

    private @NotNull MessageUsernameSet moveStudentToDiningRoomCommand(@NotNull Command command) {
        List<StudentColor> students = command.getStudents();
        movedStudents += students.size();
        if (movedStudents > MAX_STUDENTS_TO_MOVE) {
            movedStudents -= students.size();
            return MessageUsernameSet.of(new Error("You are trying to move too many students"), command.getUsername());
        }
        try {
            board.moveStudentsToDiningRoom(command.getUsername(), command.getStudents());
        } catch (NoSuchStudentException | TooManyStudentsException | NotYourTurnException | PhaseNotRightException e) {
            movedStudents -= students.size();
            return errorMessage(e, command.getUsername());
        }
        if (movedStudents == MAX_STUDENTS_TO_MOVE) {
            movedStudents = 0;
            turn.changePhase();
        }
        return updateAll();
    }

    private @NotNull MessageUsernameSet moveStudentToIslandCommand(@NotNull Command command) {
        List<StudentColor> students = command.getStudents();
        movedStudents += students.size();
        if (movedStudents > MAX_STUDENTS_TO_MOVE) {
            return MessageUsernameSet.of(new Error("you are trying to move too many students"), command.getUsername());
        }
        try {
            board.moveStudentToIsland(command.getUsername(), command.getIslandId() - 1, command.getStudents());
        } catch (NoSuchStudentException | NotYourTurnException | PhaseNotRightException e) {
            return errorMessage(e, command.getUsername());
        }
        if (movedStudents == MAX_STUDENTS_TO_MOVE) {
            movedStudents = 0;
            turn.changePhase();
        }
        return updateAll();
    }

    private @NotNull MessageUsernameSet moveMotherNatureCommand(@NotNull Command command) {
        try {
            board.moveMotherNature(command.getMotherNaturePositionShift());
        } catch (PhaseNotRightException | IllegalArgumentException e) {
            e.printStackTrace();
            return errorMessage(e, command.getUsername());
        }
        if (board.isWinningState()) {
            try {
                winUpdate(board.getWinner());
            } catch (DrawException e) {
                return errorMessage(e, command.getUsername());
                //FIXME: handle game-end.
                //parità?
            }
        }
        return updateAll();
    }

    private @NotNull MessageUsernameSet chooseCloudCommand(@NotNull Command command) {
        try {
            board.chooseCloud(command.getUsername(), command.getCloudId() - 1);
            logger.info(board.getData(command.getUsername()));
        } catch (NotYourTurnException | TooManyStudentsException | PhaseNotRightException e) {
            return errorMessage(e, command.getUsername());
        }
        if (board.isWonByResources()) {
            logger.info("the game is ending!!");
            try {
                return winUpdate(board.getWinner());
            } catch (DrawException e) {
                return null; //FIXME WinUpdate
            }
        }
        return updateAll();
    }

    private @NotNull MessageUsernameSet payCharCommand(@NotNull Command command) {
        //MONK WORKS!
        //FARMER
        //GUARD
        //MAILMAN WORKS!
        //WITCH
        //CENTAUR
        //JESTER WORKS!
        //KNIGHT
        //COOK
        //STORYTELLER WORKS!
        //QUEEN WORKS!
        //TAXMAN
        try {
            board.playExpertCard(command.getCharId(), command.getIslandId() - 1, command.getStudents());
        } catch (WrongGameModeException | CoinException | StudentException | PhaseNotRightException e) {
            return errorMessage(e, command.getUsername());
        }
        return updateAll();
    }

    public @NotNull MessageUsernameSet updateAll() {
        MessageUsernameSet messageUsernameSet = new MessageUsernameSet();
        board   .getCastleMap()
                .keySet()
                .forEach(i->messageUsernameSet.add(new Update(board.getData(i)), i));
        return messageUsernameSet;
    }

    private @NotNull MessageUsernameSet winUpdate(@Nullable Team winner) {
        MessageUsernameSet messageUsernameSet = new MessageUsernameSet();
                board.getCastleMap()
                        .forEach((key, value) -> {
                            if (value.getTeam() == winner)
                                messageUsernameSet.add(new WinUpdate(board.getData(key), "HEY"), key);
                            messageUsernameSet.add(new Ping(), "PIPPO"); //FIXME
                        });
        return messageUsernameSet;
    }

    private @NotNull MessageUsernameSet errorMessage(@NotNull Throwable error, @NotNull String addressee) {
        logger.info("game command error", error);
        return MessageUsernameSet.of(new Error(error.getMessage()), addressee);
    }

}