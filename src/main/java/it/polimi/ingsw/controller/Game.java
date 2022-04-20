package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Turn;

public class Game{
    private final int gameId;
    private Board board;
    private Turn turn;

    public Game(int gameId) {
        this.gameId = gameId;
    }

    public void executeCommand(String command){
        System.out.println("game" + gameId + " is printing: "+ command);
    }
}
