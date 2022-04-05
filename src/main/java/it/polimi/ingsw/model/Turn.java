package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private final List<String> playerSittingOrder;
    private List<String> playerPlanificationOrder;
    private String playerTurn;

    /**
     * For the first round the PlanificationTurn is the Sitting Order
     * @param playerSittingOrder
     */
    public Turn(List<String> playerSittingOrder) {
        this.playerSittingOrder = new ArrayList<>(playerSittingOrder);
        playerPlanificationOrder = new ArrayList<>(playerSittingOrder);
        playerTurn = playerSittingOrder.get(0);
    }

    /**
     * @return the player who is playing
     */
    public String getTurn(){
        return playerTurn;
    }

    /**Changes the turn along the action turn order
     */
    public void setTurnAction(List<String> newerTurns){
        this.playerPlanificationOrder= newerTurns;
    }

    /** Sets the current turn to the player besides him.
     * @return playerTurn
     */
    public String nextTurnPlanification(){
        int nextPlayerPosition = playerSittingOrder.indexOf(playerTurn) + 1;
        if(nextPlayerPosition == playerSittingOrder.size())
            return playerTurn = playerSittingOrder.get(0);
        else return playerTurn = playerSittingOrder.get(nextPlayerPosition);
    }

    /** Sets the current turn to the next player chose in the planification turn.
     * Returns also the first player for the Planification turn
     * @return playerTurn
     */
    public String nextTurnAction(){
        int nextPlayerPosition = playerPlanificationOrder.indexOf(playerTurn) + 1;
        return playerTurn = playerPlanificationOrder.get(nextPlayerPosition);
    }
}
