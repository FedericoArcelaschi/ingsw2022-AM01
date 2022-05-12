package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.Command;
import it.polimi.ingsw.communication.CommandAttribute;
import it.polimi.ingsw.communication.CommandType;
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

    public Game(GameType gameType, int gameId, List<String> usernameList, List<Socket> gameSocketList) {
        this.gameType = gameType;
        this.gameId = gameId;
        this.usernameSocketMap = new HashMap<>();
        for (int i = 0; i < usernameList.size(); i++) {
            usernameSocketMap.put(usernameList.get(i), gameSocketList.get(i));
        }
        turn = new Turn(usernameList);
        board = BoardFactory.getBoard(usernameList, turn);
    }

    /**
     * execute the command requested
     * @param command description of the command requested
     * @return response to the command
     */
    public void executeCommand(Command command){
        switch(command.getType()) {
            case PLAY_CARD -> {
                playCardCommand(command);
            }
            case MOVE_STUDENT -> {
                moveStudentCommand(command);
            }
            case MOVE_MOTHER_NATURE -> {
                moveMotherNatureCommand(command);
            }
            case CHOOSE_CLOUD -> {
                chooseCloudCommand(command);
            }
        }
        send(createError(0, "Not valid command"), usernameSocketMap.get(command.getUsername()));
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
        try {
            board.playCard(command.getUsername() ,Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
        } catch (NotYourTurnException e) {
            send(createError(0, "It's not your turn yet!"), usernameSocketMap.get(command.getUsername()));
        } catch (IllegalArgumentException e) {
            send(createError(0, "Card specified doesn't exists or is already played"), usernameSocketMap.get(command.getUsername()));
        }
        sendAllUpdate();
    }

    private void moveStudentCommand(@NotNull Command command){
        //Here for now I assume that the list of students in input is
        //given as a single string of Color separated by commas.
        //Needs to be changed accordingly if the convention changes.
        List<String> studentList;
        studentList = Arrays.asList(command.getAttributesMap().get(CommandAttribute.ID).split("\\s*,\\s*"));
        List<Color> students = new ArrayList<>();
        String s = "";
        for (String stud : studentList) {
            Color c = Color.valueOf(stud);
            switch (c) {
                case YELLOW -> students.add(Color.YELLOW);
                case BLUE -> students.add(Color.BLUE);
                case GREEN -> students.add(Color.GREEN);
                case RED -> students.add(Color.RED);
                case PINK -> students.add(Color.PINK);
            }
        }
        switch (command.getAttributesMap().get(CommandAttribute.WHERE)){
            case "dining room" -> {
                try {
                    board.moveStudentToDiningRoom(command.getUsername(), students);
                } catch (NoSuchStudentException e) {
                    send(createError(0, "There aren't enough students!"), usernameSocketMap.get(command.getUsername()));
                } catch (NotYourTurnException e) {
                    send(createError(0, "It's not your turn yet!"), usernameSocketMap.get(command.getUsername()));
                } catch (TooManyStudentsException e) {
                    send(createError(0, "The dining room is full!"), usernameSocketMap.get(command.getUsername()));
                }
                sendAllUpdate();
            }
            case "island" -> {
                //the current player moves the list of students in the third parameter
                //to the island of which the id was chosen.
                try {
                    board.moveStudentToIsland(command.getUsername(), Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)), students);
                } catch (NoSuchStudentException e) {
                    send(createError(0, "There aren't enough students!"), usernameSocketMap.get(command.getUsername()));
                } catch (NotYourTurnException e) {
                    send(createError(0, "It's not your turn yet!"), usernameSocketMap.get(command.getUsername()));
                }
                sendAllUpdate();
            }
        }
        send(createError(0, "Not valid destination for students"), usernameSocketMap.get(command.getUsername()));
    }

    private Packet moveMotherNatureCommand(Command command){
        board.moveMotherNature(Integer.parseInt(command.getAttributesMap().get(CommandAttribute.DISTANCE)));
        return  createUpdate(DataBuilder.newBoardData(command.getUsername(), board));
    }

    private void chooseCloudCommand(Command command){
        try {
            board.chooseCloud(command.getUsername(), Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
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
}