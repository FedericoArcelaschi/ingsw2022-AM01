package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;

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
                switch (command.getAttributesMap().get(CommandAttribute.WHAT)) {
                    case "deck" -> {
                        return gson.toJson(getDeck(command.getPlayerID()));
                    }
                    case "professors" -> {
                        return gson.toJson(getProfessorMap());
                    }
                }
            }
            case PLAY_CARD -> {
                String player = command.getPlayerID();
                board.getCastleMap().get(player).playCard(Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)));
                return "Card has been played successfully!";
            }
            case MOVE_STUDENT -> { //Here for now I assume that the list of students in input is
                                   //given as a single string of Color separated by commas.
                                   //Needs to be changed accordingly if the convention changes.
                List<String> studentList;
                studentList = Arrays.asList(command.getAttributesMap().get(CommandAttribute.ID).split("\\s*,\\s*"));
                List<Color> students = new ArrayList<>();
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
                switch (CommandAttribute.WHERE.toString()){
                    case "Dining room" -> {
                        try {
                            board.moveStudentToDR(command.getPlayerID(), students);
                        } catch (NoSuchStudentException e) {
                            e.printStackTrace();
                        } catch (NotYourTurnException e) {
                            e.printStackTrace();
                        } catch (TooManyStudentsException e) {
                            e.printStackTrace();
                        }
                    }
                    case "Island" -> {
                        //the current player moves the list of students in the third parameter
                        //to the island of which the id was chosen.
                        try {
                            board.moveStudentToIsland(command.getPlayerID(), Integer.parseInt(command.getAttributesMap().get(CommandAttribute.ID)), students);
                        } catch (NoSuchStudentException e) {
                            e.printStackTrace();
                        } catch (NotYourTurnException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            case MOVE_MOTHER_NATURE -> {
                board.moveMotherNature(Integer.parseInt(command.getAttributesMap().get(CommandAttribute.DISTANCE)));
                return "Mother nature has been moved successfully!";
            }
            case CHOOSE_CLOUD -> {
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
}