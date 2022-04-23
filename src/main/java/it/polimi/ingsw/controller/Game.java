package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Game{
    private final int gameId;
    private Board board;
    private Turn turn;
    private static Gson gson = new Gson();

    public Game(int gameId, List<String> nicknameList) {
        this.gameId = gameId;
        turn = new Turn(nicknameList);
        board = new BoardFactory().getBoard(nicknameList, turn);
    }

    /**
     * execute the command requested
     * @param command description of the command requested
     * @return response to the command
     */
    public String executeCommand(Command command){
        switch(command.getType()) {
            case GET -> {
                return getCommand(command);
            }
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
        return command.toString();
    }

    /**
     * return the availability for each card of the deck
     * @param playerID the player that called the command
     * @return String that shows availability for each card of the deck
     */
    private Boolean[] getDeck(String playerID){
        return board.getAvailableCards(playerID);
    }

    private Map<Color, Team> getProfessorMap(){
        return board.getProfessorsMap();
    }

    /**
     * Private method that handles a command of "get" type.
     * @param command, get command.
     * @return the command, so that the server can send the information back.
     */
    private String getCommand(Command command){
        switch (command.getAttributesMap().get(CommandAttribute.WHAT)) {
            case "deck" -> {
                return gson.toJson(getDeck(command.getPlayerID()));
            }
            case "professors" -> {
                return gson.toJson(getProfessorMap());
            }
        }
        return "Command was not successful. Please, try again.";
    }

    private String playCardCommand(Command command){
        String player = command.getPlayerID();
        try {
            board.playCard(command.getPlayerID() ,Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
        } catch (NotYourTurnException e) {
            e.printStackTrace();
            return "It's not your turn yet!";
        }
        return "Card has been played successfully!";
    }

    private String moveStudentCommand(@NotNull Command command){
        //Here for now I assume that the list of students in input is
        //given as a single string of Color separated by commas.
        //Needs to be changed accordingly if the convention changes.
        List<String> studentList;
        studentList = Arrays.asList(command.getAttributesMap().get(CommandAttribute.ID).split("\\s*,\\s*"));
        List<Color> students = new ArrayList<>();
        String s = new String();
        for (String stud : studentList) {
            switch (stud){
                case "Yellow" -> {
                    students.add(Color.YELLOW);
                }case "Blue" -> {
                    students.add(Color.BLUE);
                }case "Green" -> {
                    students.add(Color.GREEN);
                }case "Red" -> {
                    students.add(Color.RED);
                }case "Pink" -> {
                    students.add(Color.PINK);
                }
            }
        }
        switch (command.getAttributesMap().get(CommandAttribute.WHERE)){
            case "Dining room" -> {
                try {
                    board.moveStudentToDR(command.getPlayerID(), students);
                    s = "The students have been moved to the dining room.";
                    return s;
                } catch (NoSuchStudentException e) {
                    e.printStackTrace();
                } catch (NotYourTurnException e) {
                    e.printStackTrace();
                    s = "It's not your turn yet!";
                    return s;
                } catch (TooManyStudentsException e) {
                    e.printStackTrace();
                    s =  "The dining room is full!";
                    return s;
                }
            }
            case "Island" -> {
                //the current player moves the list of students in the third parameter
                //to the island of which the id was chosen.
                try {
                    board.moveStudentToIsland(command.getPlayerID(), Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)), students);
                    s = "The students have been moved to the chosen island.";
                    return s;
                } catch (NoSuchStudentException e) {
                    e.printStackTrace();
                } catch (NotYourTurnException e) {
                    e.printStackTrace();
                    s = "It's not your turn yet!";
                    return s;
                }
            }
        }
        return s;
    }

    private String moveMotherNatureCommand(Command command){
        board.moveMotherNature(Integer.parseInt(command.getAttributesMap().get(CommandAttribute.DISTANCE)));
        return "Mother nature has been moved successfully!";
    }

    private String chooseCloudCommand(Command command){
        try {
            board.chooseCloud(command.getPlayerID(), Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
            return "The cloud has been chosen successfully!";
        } catch (NotYourTurnException e) {
            e.printStackTrace();
            return "It is not your turn.";
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
            return "You can't choose this cloud yet.";
        }
    }

    private String notifyStatusUpdate(){
        return "The game state has been modified.";
    }
}