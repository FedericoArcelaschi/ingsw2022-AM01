package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.communication.packet.message.Error;
import it.polimi.ingsw.communication.packet.message.command.Command;
import it.polimi.ingsw.communication.packet.message.command.CommandAttribute;
import it.polimi.ingsw.server.communication.ServerReceiver;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.ModelDataBuilder;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.*;
import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
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
    //TODO: there could be a better way of doing this. The parameter is used to ensure that the player can move students
    //TODO: more times per turn.
    private int movedStudents;

    public Game(GameType gameType, int gameId, List<ServerReceiver> gameSocketList) {
        this.gameType = gameType;
        this.usernameServerReceiverMap = new HashMap<>();
        this.usernameSocketMap = new HashMap<>();
        this.movedStudents = 0;
        System.out.println(gameType.nPlayer);
        for (int i = 0; i < gameType.nPlayer; i++) {
            usernameServerReceiverMap.put(gameSocketList.get(i).getUsername(), gameSocketList.get(i));
            usernameSocketMap.put(gameSocketList.get(i).getUsername(), gameSocketList.get(i).getSocket());
        }
        turn = new Turn(usernameSocketMap.keySet().stream().toList());
        this.board = BoardFactory.getBoard(usernameSocketMap.keySet().stream().toList(), gameType.expertMode, turn);
        sendAllUpdate();
    }

    /**
     * execute the command requested
     * @param command description of the command requested
     * @return response to the command
     */
    public void executeCommand(Command command){
        System.out.println("Executing command...");
        if(command.getType() == null){
            send(createError(0, "Command not valid; please, try again."), usernameSocketMap.get(command.getUsername()));
            sendAllUpdate();
        }else {
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

    public void playerDisconnected(Socket s){
        String user = null;
        for (String username: usernameSocketMap.keySet()) {
            if(usernameSocketMap.get(username).equals(s)) user = username;
        }
        if(user == null) throw new IllegalArgumentException("the player isn't part of this game");
        Message message = new EndGame(user + " disconnected");
        Packet packet = new Packet(message);
        for (String username: usernameSocketMap.keySet()) {
            PrintWriter out = null;
            send(packet, usernameSocketMap.get(username));
        }
    }

    public void playerWin(String winner){
        for (String username: usernameSocketMap.keySet()) {
            PrintWriter out = null;
            Message message = new WinUpdate(ModelDataBuilder.newBoardData(username, board), winner);
            Packet packet  = new Packet(message);
            send(packet, usernameSocketMap.get(username));
        }
    }

    private void sendAllUpdate() {
        for (String username: usernameSocketMap.keySet()) {
            PrintWriter out = null;
            Message message = new Update(ModelDataBuilder.newBoardData(username, board));
            Packet packet  = new Packet(message);
            send(packet, usernameSocketMap.get(username));
        }
    }

    private void send(Packet packet, Socket socket) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.println(packet.toJson());
    }

    private Packet createUpdate(BoardData boardData){
        Message message = new Update(boardData);
        return new Packet(message);
    }

    private Packet createError(int errorCode, String errorMessage){
        Message message = new Error(errorCode, errorMessage);
        return new Packet(message);
    }

    private void playCardCommand(Command command){
        try {
            board.playCard(command.getUsername() ,Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
            turn.changePhase();
        } catch (NotYourTurnException | IllegalArgumentException | PhaseNotRightException e) {
            send(createError(0, e.getMessage()), usernameSocketMap.get(command.getUsername()));
        }
        sendAllUpdate();
    }

    private void moveStudentToDiningRoomCommand(@NotNull Command command){
        //Here for now I assume that the list of students in input is
        //given as a single string of Color separated by commas.
        //Needs to be changed accordingly if the convention changes.

        //List of students in the command
        List<StudentColor> students = getStudentsFromCommand(command.getAttributesMap().get(CommandAttribute.WHAT));
        movedStudents += students.size();

        try {
            board.moveStudentsToDiningRoom(command.getUsername(), students);
            if(movedStudents == 3) {
                movedStudents = 0;
                turn.changePhase();
            }
        } catch (NoSuchStudentException | TooManyStudentsException | NotYourTurnException | PhaseNotRightException e) {
            send(createError(0, e.getMessage()), usernameSocketMap.get(command.getUsername()));
        }
        sendAllUpdate();
    }

    private void moveStudentToIslandCommand(@NotNull Command command){
        List<StudentColor> students = getStudentsFromCommand(command.getAttributesMap().get(CommandAttribute.WHAT));
        movedStudents += students.size();
        if(movedStudents > 3){
            send(createError(0, "Too many students selected, try again."), usernameSocketMap.get(command.getUsername()));
        }
        else {
            try {
                //The -1 is needed because islands are indexed starting from 0; if the player inputs 1 the chosen island
                //becomes the first one, which is island number 0.
                board.moveStudentToIsland(command.getUsername(), Integer.parseInt(command.getAttributesMap().get(CommandAttribute.WHERE)) - 1, students);
                if (movedStudents == 3) {
                    movedStudents = 0;
                    turn.changePhase();
                }
            } catch (NoSuchStudentException e) { //fixme e.getmessage()
                send(createError(0, "There aren't enough students!"), usernameSocketMap.get(command.getUsername()));
            } catch (NotYourTurnException e) {
                send(createError(0, "It's not your turn yet!"), usernameSocketMap.get(command.getUsername()));
            } catch (PhaseNotRightException e) {
                send(createError(0, "You can't use this command in this phase of the game."), usernameSocketMap.get(command.getUsername()));
            }
            sendAllUpdate();
        }
    }

    private void moveMotherNatureCommand(Command command){
        if(Integer.parseInt(command.getAttributesMap().get(CommandAttribute.DISTANCE)) > board.getPossibleMovingSteps())
            send(createError(0, "Too many steps; please try again."), usernameSocketMap.get(command.getUsername()));
        else {
            try{
                board.moveMotherNature(Integer.parseInt(command.getAttributesMap().get(CommandAttribute.DISTANCE)));
            }catch(PhaseNotRightException e){
                send(createError(0, "You can't use this command in this stage of the game."), usernameSocketMap.get(command.getUsername()));
            }
            Team t = board.isWinningPosition();
            if (t != null) {
                sendWinUpdate(t);  //Also changes the state of the client to GAME_ENDED now.
            }
            turn.changePhase();
            sendAllUpdate();
        }
    }

    private void chooseCloudCommand(Command command){
        try {
            board.chooseCloud(command.getUsername(), Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
            Team t = board.isWonByResources();
            if(t != null){
                sendWinUpdate(t);  //Also changes the state of the client to GAME_ENDED now.
            }
            turn.changePhase();
        } catch (NotYourTurnException e) {
            send(createError(0, "It's not your turn yet!"), usernameSocketMap.get(command.getUsername()));
        } catch (TooManyStudentsException e) {
            send(createError(0, "The waiting room is full!"), usernameSocketMap.get(command.getUsername()));
        } catch (PhaseNotRightException e) {
            send(createError(0, "You can't use this command in this stage of the game."), usernameSocketMap.get(command.getUsername()));
        }
        sendAllUpdate();
    }

    private void payCharCommand(Command command){
        try {
            int idChar = CharacterUtility.getChar(command.getAttributesMap().get(CommandAttribute.WHO)).getId();
            List<String> studentList = new ArrayList<>(Arrays.asList(command.getAttributesMap().get(CommandAttribute.WHAT).split(",")));
            List<StudentColor> students = new ArrayList<>();
            for (String student : studentList) {
                StudentColor c = StudentColor.getColor(student);
                students.add(c);
            }
            board.playExpertCard(idChar, Integer.parseInt(command.getAttributesMap().get(CommandAttribute.WHERE)), students );
        }catch (NotTheRightGamemodeException | CoinException | StudentException | PhaseNotRightException e){
            e.printStackTrace();
        }
    }

    private void getCharInfo(Command command){
        //The method does not throw exceptions as everyone can use it anytime during the game.
        int idChar = CharacterUtility.getChar(command.getAttributesMap().get(CommandAttribute.WHO)).getId();
        try {
            Packet characterInfo = new Packet(new CharInfo(board.getCharInfo(idChar)));
            send(characterInfo, usernameSocketMap.get(command.getUsername()));
        }catch (NotTheRightGamemodeException e){
            send(createError(0, "You can't use this command in this gamemode."), usernameSocketMap.get(command.getUsername()));
        }
    }

    private void sendWinUpdate(Team t){
        Gson parser = new Gson();
        for (String username: usernameSocketMap.keySet()) {
            PrintWriter out = null;
            Message winner = new WinUpdate(ModelDataBuilder.newBoardData(username, board), t.name());
            Packet packet  = new Packet(winner);
            send(packet, usernameSocketMap.get(username));
            Message gameOver = new EndGame("Game over. Changing state...");
            Packet packet1 = new Packet(gameOver);
            send(packet, usernameSocketMap.get(username));
        }
    }

    private List<StudentColor> getStudentsFromCommand(String string){
        List<String> studentList =  new ArrayList<>(Arrays.asList(string.split(",")));
        List<StudentColor> students = new ArrayList<>();
        StudentColor color = null;
        for (String s : studentList) {
            color = StudentColor.parseColor(s);
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
        StringBuilder r = new StringBuilder("");
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