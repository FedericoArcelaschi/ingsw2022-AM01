package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.Command;
import it.polimi.ingsw.communication.CommandAttribute;
import it.polimi.ingsw.communication.CommandType;
import it.polimi.ingsw.communication.ServerReceiver;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.DataBuilder;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class Game{
    private final GameType gameType;
    private final int gameId;
    private final Board board;
    private final Turn turn;
    private final Map<String, Socket> usernameSocketMap;
    private final Map<String, ServerReceiver> usernameServerReceiverMap;
    //TODO: there could be a better way of doing this. The parameter is used to ensure that the player can move students
    //TODO: more times per turn.
    private int movedStudents;

    public Game(GameType gameType, int gameId, List<String> usernameList, List<ServerReceiver> gameSocketList) {
        this.gameType = gameType;
        this.gameId = gameId;
        this.usernameServerReceiverMap = new HashMap<>();
        this.usernameSocketMap = new HashMap<>();
        this.movedStudents = 0;
        for (int i = 0; i < usernameList.size(); i++) {
            usernameServerReceiverMap.put(usernameList.get(i), gameSocketList.get(i));
            usernameSocketMap.put(usernameList.get(i), gameSocketList.get(i).getSocket());
        }
        turn = new Turn(usernameList);
        board = BoardFactory.getBoard(usernameList, turn);
        sendAllUpdate();
    }

    /**
     * execute the command requested
     * @param command description of the command requested
     * @return response to the command
     */
    public void executeCommand(Command command){
        System.out.println("Executing command of type: " + command.getType().name());
        switch(command.getType()) {
            case PLAY_CARD -> playCardCommand(command);
            case MOVE_STUDENT_TO_DININGROOM -> moveStudentToDiningRoomCommand(command);
            case MOVE_STUDENT_TO_ISLAND -> moveStudentToIslandCommand(command);
            case MOVE_MOTHER_NATURE -> moveMotherNatureCommand(command);
            case CHOOSE_CLOUD -> chooseCloudCommand(command);
        }
        //send(createError(0, "Not valid command"), usernameSocketMap.get(command.getUsername()));
    }

    public void playerDisconnected(Socket s){
        String user = null;
        for (String username: usernameSocketMap.keySet()) {
            if(usernameSocketMap.get(username).equals(s)) user = username;
        }
        if(user == null) throw new IllegalArgumentException("the player isn't part of this game");
        Message message = new EndGameMessage(user + " disconnected");
        Packet packet = new Packet(MessageType.END, message);
        for (String username: usernameSocketMap.keySet()) {
            PrintWriter out = null;
            send(packet, usernameSocketMap.get(username));
        }
    }

    public void playerWin(String winner){
        for (String username: usernameSocketMap.keySet()) {
            PrintWriter out = null;
            Message message = new WinUpdate(DataBuilder.newBoardData(username, board), winner);
            Packet packet  = new Packet(MessageType.UPDATE, message);
            send(packet, usernameSocketMap.get(username));
        }
    }

    private void sendAllUpdate(){
        Gson parser = new Gson();
        for (String username: usernameSocketMap.keySet()) {
            PrintWriter out = null;
            Message message = new Update(DataBuilder.newBoardData(username, board));
            Packet packet  = new Packet(MessageType.UPDATE, message);
            send(packet, usernameSocketMap.get(username));
        }
    }

    private void send(Packet packet, Socket socket){
        Gson parser = new Gson();
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.println(parser.toJson(packet));
    }

    private Packet createUpdate(BoardData boardData){
        Message message = new Update(boardData);
        return new Packet(MessageType.UPDATE, message);
    }

    private Packet createError(int errorCode, String errorMessage){
        Message message = new ErrorMessage(errorCode, errorMessage);
        return new Packet(MessageType.ERROR, message);
    }

    private void playCardCommand(Command command){
        System.out.println("Playcard command is being executed...");
        try {
            board.playCard(command.getUsername() ,Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
            turn.changePhase();
        } catch (NotYourTurnException e) {
            send(createError(0, "It's not your turn yet!"), usernameSocketMap.get(command.getUsername()));
        } catch (IllegalArgumentException e) {
            send(createError(0, "Card specified doesn't exists or is already played"), usernameSocketMap.get(command.getUsername()));
        }
        sendAllUpdate();
    }

    private void moveStudentToDiningRoomCommand(@NotNull Command command){
        //Here for now I assume that the list of students in input is
        //given as a single string of Color separated by commas.
        //Needs to be changed accordingly if the convention changes.

        //List of students in the command
        List<String> studentList =  new ArrayList<>(Arrays.asList(command.getAttributesMap().get(CommandAttribute.WHAT).split(",")));
        //List of students that will get the respective Color value
        List<StudentColor> students = new ArrayList<>();
        for (String s : studentList) {
            StudentColor c = StudentColor.getColor(s);
            students.add(c);
        }
        movedStudents += students.size();

        try {
            board.moveStudentToDiningRoom(command.getUsername(), students);
            if(movedStudents == 3) {
                movedStudents = 0;
                turn.changePhase();
            }
        } catch (NoSuchStudentException e) {
            send(createError(0, "There aren't enough students!"), usernameSocketMap.get(command.getUsername()));
        } catch (NotYourTurnException e) {
            send(createError(0, "It's not your turn yet!"), usernameSocketMap.get(command.getUsername()));
        } catch (TooManyStudentsException e) {
            send(createError(0, "The dining room is full!"), usernameSocketMap.get(command.getUsername()));
        }
        sendAllUpdate();
    }

    private void moveStudentToIslandCommand(@NotNull Command command){
        List<String> studentList = new ArrayList<>(Arrays.asList(command.getAttributesMap().get(CommandAttribute.WHAT).split(",")));
        List<StudentColor> students = new ArrayList<>();
        for (String stud : studentList) {
            StudentColor c = StudentColor.getColor(stud);
            students.add(c);
        }
        movedStudents += students.size();

        try {
            //The -1 is needed because islands are indexed starting from 0; if the player inputs 1 the chosen island
            //becomes the first one, which is island number 0.
            board.moveStudentToIsland(command.getUsername(), Integer.parseInt(command.getAttributesMap().get(CommandAttribute.WHERE))-1, students);
            if(movedStudents == 3) {
                movedStudents = 0;
                turn.changePhase();
            }
        } catch (NoSuchStudentException e) {
            send(createError(0, "There aren't enough students!"), usernameSocketMap.get(command.getUsername()));
        } catch (NotYourTurnException e) {
            send(createError(0, "It's not your turn yet!"), usernameSocketMap.get(command.getUsername()));
        }
        sendAllUpdate();
    }

    private void moveMotherNatureCommand(Command command){
        System.out.println("Moving mother nature...");
        board.moveMotherNature(Integer.parseInt(command.getAttributesMap().get(CommandAttribute.DISTANCE)));
        turn.changePhase();
        sendAllUpdate();
        //return  createUpdate(DataBuilder.newBoardData(command.getUsername(), board));
    }

    private void chooseCloudCommand(Command command){
        try {
            board.chooseCloud(command.getUsername(), Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
            turn.changePhase();
        } catch (NotYourTurnException e) {
            send(createError(0, "It's not your turn yet!"), usernameSocketMap.get(command.getUsername()));
        } catch (TooManyStudentsException e) {
            send(createError(0, "The waiting room is full!"), usernameSocketMap.get(command.getUsername()));
        }
        sendAllUpdate();
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