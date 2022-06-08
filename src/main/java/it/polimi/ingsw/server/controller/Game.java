package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.command.CommandAttribute;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.subclasses.Error;
import it.polimi.ingsw.communication.message.subclasses.*;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.server.communication.ServerReceiver;
import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class Game {
    private final GameType gameType;
    private final Board board;

    private final Turn turn;
    private final Map<String, Socket> usernameSocketMap;
    private final Map<String, ServerReceiver> usernameServerReceiverMap;
    // TODO: there could be a better way of doing this. The parameter is used to ensure that the player can move students
    // TODO: more times per turn.
    private int movedStudents;

    private final int MAX_STUDENTS_TO_MOVE;

    public Game(GameType gameType, List<ServerReceiver> gameSocketList) {
        this.gameType = gameType;
        this.usernameServerReceiverMap = new HashMap<>();
        this.usernameSocketMap = new HashMap<>();
        this.movedStudents = 0;
        for (int i = 0; i < gameType.nPlayer; i++) {
            usernameServerReceiverMap.put(gameSocketList.get(i).getUsername(), gameSocketList.get(i));
            usernameSocketMap.put(gameSocketList.get(i).getUsername(), gameSocketList.get(i).getSocket());
        }
        this.board = BoardFactory.getBoard(usernameSocketMap.keySet().stream().toList(), gameType.expertMode);
        this.turn = board.getTurn();
        MAX_STUDENTS_TO_MOVE = (gameType.nPlayer == 3) ? 4 : 3;

        sendAllUpdate();
    }

    /**
     * execute the command requested
     * @param command description of the command requested
     */
    public void executeCommand(Command command) {
        System.out.println("Executing command...");
        if(command.getType() == null){
            send(createError(0, "Command not valid; please, try again."), usernameSocketMap.get(command.getUsername()));
        } else {
            switch (command.getType()) {
                case PLAY_CARD -> playCardCommand(command);
                case MOVE_STUDENT_TO_CASTLE -> moveStudentToDiningRoomCommand(command);
                case MOVE_STUDENT_TO_ISLAND -> moveStudentToIslandCommand(command);
                case MOVE_MOTHER_NATURE -> moveMotherNatureCommand(command);
                case CHOOSE_CLOUD -> chooseCloudCommand(command);
                case PAY_CHARACTER -> payCharCommand(command);
                case CHARACTER_INFO -> getCharInfo(command);
            }
        }
    }

    public void playerDisconnected(Socket s) {
        String user = null;
        for (String username : usernameSocketMap.keySet()) {
            if (usernameSocketMap.get(username).equals(s)) user = username;
        }
        if (user == null) throw new IllegalArgumentException("the player isn't part of this game");
        Message message = new EndGame(user + " disconnected");
        for (String username : usernameSocketMap.keySet()) {
            send(message, usernameSocketMap.get(username));
        }
    }

    private void sendWinUpdate(Team t) {
        for (String username : usernameSocketMap.keySet()) {
            Message winner = new WinUpdate(board.getData(username), t.name());
            send(winner, usernameSocketMap.get(username));
            Message gameOver = new EndGame("Game over. Changing state...");
            send(gameOver, usernameSocketMap.get(username)); //FIXME: qui non si capisce cosa succede idk
        }
    }

    private void sendAllUpdate() {
        for (String username : usernameSocketMap.keySet()) {
            //Fixme: maybe I shouldn't do it this way, but at least it prints out the right board...
            Message message = createUpdate(board.getData(username));
            send(message, usernameSocketMap.get(username));
        }
    }

    private void send(Message message, Socket socket) {
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.println(message.toJson());
    }

    private Message createUpdate(BoardData boardData) {
        return new Update(boardData); //FIXME
    }

    private Message createError(int errorCode, String errorMessage) {
        return new Error(errorCode, errorMessage); //FIXME never used code
    }

    private void playCardCommand(Command command) {
        try {
            board.playCard(command.getUsername() ,Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
        } catch (NotYourTurnException | IllegalArgumentException | PhaseNotRightException e) {
            send(createError(0, e.getMessage()), usernameSocketMap.get(command.getUsername()));
            return;
        }
        turn.changePhase();
        sendAllUpdate();
    }

    private void moveStudentToDiningRoomCommand(@NotNull Command command){
        // Here for now I assume that the list of students in input is
        // given as a single string of Color separated by commas.
        // Needs to be changed accordingly if the convention changes.
        List<StudentColor> students = getStudentsFromCommand(command.getAttributesMap().get(CommandAttribute.WHAT));
        movedStudents += students.size();
        if(movedStudents > MAX_STUDENTS_TO_MOVE)
            send(new Error(1, "too many students"),
                    usernameSocketMap.get(command.getUsername() // FIXME: questo fa schifo
                    ));
        try {
            board.moveStudentsToDiningRoom(command.getUsername(), students);
        } catch (NoSuchStudentException | TooManyStudentsException | NotYourTurnException | PhaseNotRightException e) {
                movedStudents -= students.size();
                send(createError(0, e.getMessage()), usernameSocketMap.get(command.getUsername()));
                return;
        }
        if (movedStudents == MAX_STUDENTS_TO_MOVE) {
            movedStudents = 0;
            turn.changePhase();
        }
        sendAllUpdate();
    }

    private void moveStudentToIslandCommand(@NotNull Command command){
        List<StudentColor> students = getStudentsFromCommand(command.getAttributesMap().get(CommandAttribute.WHAT));
        movedStudents += students.size();
        if(movedStudents > MAX_STUDENTS_TO_MOVE){
            send(createError(0, "Too many students selected, try again."), usernameSocketMap.get(command.getUsername()));
            return;
        }
        int islandIndex = Integer.parseInt(command.getAttributesMap().get(CommandAttribute.WHERE)) - 1;
        try {
            board.moveStudentToIsland(command.getUsername(), islandIndex, students);
        } catch (NoSuchStudentException | NotYourTurnException | PhaseNotRightException e) {
            send(createError(0, e.getMessage()), usernameSocketMap.get(command.getUsername()));
            return;
        }
        if (movedStudents == MAX_STUDENTS_TO_MOVE) {
            movedStudents = 0;
            turn.changePhase();
        }
        sendAllUpdate();
    }

    private void moveMotherNatureCommand(Command command) {
        System.out.println("MoveMotherNature: " + command);
        try {
            board.moveMotherNature(Integer.parseInt(command.getAttributesMap().get(CommandAttribute.DISTANCE)));
        } catch (PhaseNotRightException | IllegalArgumentException e) {
            e.printStackTrace();
            send(createError(0, e.getMessage()), usernameSocketMap.get(command.getUsername()));
            return;
        }
        if (board.isWinningState()) {
            try {
                sendWinUpdate(board.getWinner());
            } catch (DrawException e) {
                send(new EndGame(e.getMessage()), new Socket()); //FIXME
                //FIXME: handle game-end.
                return;
            }
        }
        turn.changePhase();
        sendAllUpdate();
    }

    private void chooseCloudCommand(Command command){
        try {
            board.chooseCloud(command.getUsername(), Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
        } catch (NotYourTurnException | TooManyStudentsException | PhaseNotRightException e) {
            send(createError(0, e.getMessage()), usernameSocketMap.get(command.getUsername()));
            return;
        }
        if (board.isWonByResources())
            try {
                Team winner = board.getWinner();
            } catch (DrawException e){
                new EndGame(e.getMessage());
            }
        else
            turn.changePhase();
        sendAllUpdate();
    }

    private void payCharCommand(Command command) {
        try {
            int idChar = CharacterUtility.getChar(command.getAttributesMap().get(CommandAttribute.WHO)).getId();
            List<String> studentList = new ArrayList<>(Arrays.asList(command.getAttributesMap().get(CommandAttribute.WHAT).split(",")));
            List<StudentColor> students = new ArrayList<>();
            for (String student : studentList) {
                StudentColor c = StudentColor.getColor(student);
                students.add(c);
            }
            board.playExpertCard(idChar, Integer.parseInt(command.getAttributesMap().get(CommandAttribute.WHERE)), students);
        } catch (NotTheRightGameModeException | CoinException | StudentException | PhaseNotRightException e) {
            e.printStackTrace(); // todo: Error message
        }
    }

    @Contract(pure = true)
    private void getCharInfo(Command command) {
        //The method does not throw exceptions as everyone can use it anytime during the game.
        int idChar = CharacterUtility.getChar(command.getAttributesMap().get(CommandAttribute.WHO)).getId();
        try {
            Message charInfo = new CharInfo(board.getCharInfo(idChar));
            send(charInfo, usernameSocketMap.get(command.getUsername()));
        } catch (NotTheRightGameModeException e) {
            send(createError(0, "You can't use this command in this gamemode."), usernameSocketMap.get(command.getUsername()));
        }
    }

    private List<StudentColor> getStudentsFromCommand(String string) { //FIXME this should go in the command
        List<String> studentList =  new ArrayList<>(Arrays.asList(string.split(",")));
        List<StudentColor> students = new ArrayList<>();
        StudentColor color;
        for (String s : studentList) {
            color = StudentColor.getColor(s);
            students.add(color);
        }
        return students;
    }

    public Board getBoard() {
        return board;
    }

    public GameType getGameType() {
        return gameType;
    }

    public String toStringPlayers() {
        StringBuilder r = new StringBuilder();
        for (String username : usernameSocketMap.keySet()) {
            r.append(username).append(" ");
        }
        return r.toString();
    }


    public List<Socket> getGameSocketList() {
        return new ArrayList<>(usernameSocketMap.values());
    }

    public List<ServerReceiver> getGameServerReceiverList() {
        return new ArrayList<>(usernameServerReceiverMap.values());
    }
}