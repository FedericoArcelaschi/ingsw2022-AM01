package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.BoardFactory;
import it.polimi.ingsw.model.Turn;

import java.util.List;

public class Game{
    private final int gameId;
    private Board board;
    private Turn turn;

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
        if(command.getType() == CommandType.GET){
            switch (command.getAttributesMap().get(CommandAttribute.WHAT)){
                case "deck" -> {
                    return getDeck(command.getPlayerID());
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
    private String getDeck(String playerID){
        Boolean[] deck = board.getAvailableCards(playerID);
        StringBuilder s = new StringBuilder(playerID+"'s deck:\n");
        for (int i = 0; i < deck.length; i++) {
            String isPlayed = deck[i] ? "not available" : "available";
            s.append(i+1).append(": ").append(isPlayed).append("\n");
        }
        return s.toString();
    }
}
