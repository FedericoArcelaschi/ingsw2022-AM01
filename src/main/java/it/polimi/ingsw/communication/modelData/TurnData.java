package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.TurnPhase;

import java.util.List;

public class TurnData {
    private final List<String> sittingOrder;
    private final List<String> actionOrder;
    private final TurnPhase currentPhase;
    private final String currentPlayer;

    public TurnData(List<String> sittingOrder, List<String> actionOrder, TurnPhase currentPhase, String currentPlayer) {
        this.sittingOrder = sittingOrder;
        this.actionOrder = actionOrder;
        this.currentPhase = currentPhase;
        this.currentPlayer = currentPlayer;
    }

    public TurnData(Turn t){
        this(t.getSittingOrder(), t.getActionOrder(), t.getCurrentPhase(), t.getCurrentPlayer());
    }

    public List<String> getSittingOrder() {
        return sittingOrder;
    }

    public List<String> getActionOrder() {
        return actionOrder;
    }

    public TurnPhase getCurrentPhase() {
        return currentPhase;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\n\tGame Phase: ").append(currentPhase);
        if(currentPhase == TurnPhase.PLANNING){
            s.append("\n\tTurn Order: ");
            for(String player : sittingOrder){
                s.append(player).append(", ");
            }
        }
        else{
            s.append("\n\tTurn Order: ");
            for(String player : actionOrder){
                s.append(player).append(", ");
            }
        }
        s.append("\n\tCurrent Player: ").append(currentPlayer);
        return s.toString();
    }
}
