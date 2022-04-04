package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private final List<String> playerSittingOrder;
    private final List<String> playerPianificationOrder;
    private String playerTurn;

    public Turn(String playerTurn){
        playerSittingOrder = new ArrayList<>();
        playerPianificationOrder = new ArrayList<>();
        this.playerTurn = playerTurn;
    }
    /** Returns the turn of the current player.
     *
     * @return playerTurn
     */
    public String getTurn(){
        return playerTurn;
    }

    /** Changes the turn.
     *
     */
    public void setTurn(List<String> newerTurns){
        this.playerPianificationOrder.clear();
        this.playerPianificationOrder.addAll(newerTurns);
    }

    /** Sets the current turn to the next player.
     *
     * @return playerTurn
     */
    public String nextTurn(){
         this.playerTurn = playerTurn.replaceAll(playerTurn, playerPianificationOrder.get(playerPianificationOrder.indexOf(playerTurn)+1));
         return playerTurn;
    }
}
