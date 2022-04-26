package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private final List<String> sittingOrder;
    private List<String> actionOrder;
    private String currentPlayerTurn;

    /**
     * For the first round the PlanificationTurn is the Sitting Order
     * @param sittingOrder players in the ordered they joined the server
     */
    public Turn(List<String> sittingOrder){
        this.sittingOrder = new ArrayList<>(sittingOrder);
        actionOrder = new ArrayList<>(sittingOrder);
        currentPlayerTurn = sittingOrder.get(0);
    }

    /**
     * @return the player who is playing
     */
    public String getCurrentPlayer(){
        return currentPlayerTurn;
    }

    /**Changes the turn along the new action turn order
     */
    public void setTurnAction(List<String> newerTurns) throws IllegalArgumentException{
        if(newerTurns.containsAll(actionOrder) && actionOrder.containsAll(newerTurns))
            this.actionOrder = newerTurns;
        else
            throw new IllegalArgumentException();
        currentPlayerTurn = actionOrder.get(0);
    }

    /** Sets the current turn to the player besides him.
     * @return playerTurn
     */
    public String nextTurnPlanning(){
        return currentPlayerTurn = next(sittingOrder, currentPlayerTurn);
    }

    /** Sets the current turn to the next player chose in the planification turn.
     * Returns also the first player for the Planification turn
     * Doesn't need tp be called for the first player. It's already on currentPlayerTurn
     * @return playerTurn
     */
    public String nextTurnAction(){
        int nextPlayerPosition = actionOrder.indexOf(currentPlayerTurn) + 1;
        if(nextPlayerPosition == sittingOrder.size())
            return null; //"It's planning Time!!"
        return currentPlayerTurn = actionOrder.get(nextPlayerPosition);
    }

    private String next(List<String> list, String element){
        int dim = list.size();
        int index = list.indexOf(element);
        if(index == dim -1)
            return list.get(0);
        else return list.get(index +1);
    }

    //TODO: DETERMINE ORDER OF ACTIONS
}
