package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private final List<String> sittingOrder;
    private List<String> actionOrder;
    private String currentPlayerTurn;

    /**
     * For the first round the PlanificationTurn is the Sitting Order
     * @param sittingOrder
     */
    public Turn(List<String> sittingOrder) throws IllegalArgumentException{
        if(sittingOrder.size() < 2 && sittingOrder.size() > 4)
            throw new IllegalArgumentException();
        this.sittingOrder = new ArrayList<>(sittingOrder);
        actionOrder = new ArrayList<>(sittingOrder);
        currentPlayerTurn = sittingOrder.get(0);
    }

    /**
     * @return the player who is playing
     */
    public String getTurn(){
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
    public String nextTurnPlanification(){
        return currentPlayerTurn = next(sittingOrder,currentPlayerTurn);
    }

    /** Sets the current turn to the next player chose in the planification turn.
     * Returns also the first player for the Planification turn
     * Doesn't need tp be called for the first player. It's already on currentPlayerTurn
     * @return playerTurn
     */
    public String nextTurnAction(){
        //actionOrder.get(0)
        //actionOrder.remove(0)
        int nextPlayerPosition = actionOrder.indexOf(currentPlayerTurn) + 1;
        if(nextPlayerPosition == sittingOrder.size())
            return null; //"It's Planification Time!!"
        return currentPlayerTurn = actionOrder.get(nextPlayerPosition);
    }

    private String next(List<String> list, String element){
        int dim = list.size();
        int index = list.indexOf(element);
        if(index == dim -1)
            return list.get(0);
        else return list.get(index +1);
    }
}
