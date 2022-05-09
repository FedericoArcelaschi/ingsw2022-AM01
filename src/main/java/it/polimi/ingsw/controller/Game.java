package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.Command;
import it.polimi.ingsw.communication.CommandAttribute;
import it.polimi.ingsw.communication.CommandType;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.ErrorMessage;
import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.message.Update;
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
    public Packet executeCommand(Command command){
        switch(command.getType()) {
            case PLAY_CARD -> {
                return playCardCommand(command);
            }
            case MOVE_STUDENT -> {
                return moveStudentCommand(command);
            }
            case MOVE_MOTHER_NATURE -> {
                return moveMotherNatureCommand(command);
            }
            case CHOOSE_CLOUD -> {
                return chooseCloudCommand(command);
            }
        }
        return createError(0, "Command not valid");
    }

    public void sendUpdate(Packet packet){
        System.out.println("--sending updates--");
        if(packet.getType() == MessageType.UPDATE){
            Gson parser = new Gson();
            for (Socket s: usernameSocketMap.values()) {
                PrintWriter out = null;
                try {
                    out = new PrintWriter(s.getOutputStream(), true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                out.println(parser.toJson(packet));
            }
        }
    }

    public void sendError(String username, Packet packet){
        Socket s = usernameSocketMap.get(username);
        Gson parser = new Gson();
        PrintWriter out = null;
        try {
            out = new PrintWriter(s.getOutputStream(), true);
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

    private Packet playCardCommand(Command command){
        try {
            board.playCard(command.getUsername() ,Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
        } catch (NotYourTurnException e) {
            return createError(0, "NotYourTurn");
        } catch (IllegalArgumentException e) {
            return createError(0, "commandError");
        }
        return  createUpdate(new BoardData(command.getUsername(), board));
    }

    private Packet moveStudentCommand(@NotNull Command command){
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
                    return createError(0, "There aren't enough students");
                } catch (NotYourTurnException e) {
                    return createError(0, "It's not your turn yet!");
                } catch (TooManyStudentsException e) {
                    return createError(0, "The dining room is full!");
                }
                return  createUpdate(new BoardData(command.getUsername(), board));
            }
            case "island" -> {
                //the current player moves the list of students in the third parameter
                //to the island of which the id was chosen.
                try {
                    board.moveStudentToIsland(command.getUsername(), Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)), students);
                } catch (NoSuchStudentException e) {
                    return createError(0, "There aren't enough students");
                } catch (NotYourTurnException e) {
                    return createError(0, "It's not your turn yet!");
                }
                return  createUpdate(new BoardData(command.getUsername(), board));
            }
        }
        return createError(0, "Not valid moveStudent command");
    }

    private Packet moveMotherNatureCommand(Command command){
        board.moveMotherNature(Integer.parseInt(command.getAttributesMap().get(CommandAttribute.DISTANCE)));
        return  createUpdate(new BoardData(command.getUsername(), board));
    }

    private Packet chooseCloudCommand(Command command){
        try {
            board.chooseCloud(command.getUsername(), Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
        } catch (NotYourTurnException e) {
            return createError(0, "It's not your turn yet!");
        } catch (TooManyStudentsException e) {
            return createError(0, "The waiting room is full!");
        }
        return  createUpdate(new BoardData(command.getUsername(), board));
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