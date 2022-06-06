package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.server.model.baseLogic.TurnPhase;

import java.util.List;
import java.util.Objects;

public final class TurnData {
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\n\tGame Phase: ").append(currentPhase);
        if (currentPhase == TurnPhase.PLANNING) {
            s.append("\n\tTurn Order: ");
            for (String player : sittingOrder) {
                s.append(player).append(", ");
            }
        } else {
            s.append("\n\tTurn Order: ");
            for (String player : actionOrder) {
                s.append(player).append(", ");
            }
        }
        s.append("\n\tCurrent Player: ").append(currentPlayer);
        return s.toString();
    }

    public List<String> sittingOrder() {
        return sittingOrder;
    }

    public List<String> actionOrder() {
        return actionOrder;
    }

    public TurnPhase currentPhase() {
        return currentPhase;
    }

    public String currentPlayer() {
        return currentPlayer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TurnData) obj;
        return Objects.equals(this.sittingOrder, that.sittingOrder) &&
                Objects.equals(this.actionOrder, that.actionOrder) &&
                Objects.equals(this.currentPhase, that.currentPhase) &&
                Objects.equals(this.currentPlayer, that.currentPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sittingOrder, actionOrder, currentPhase, currentPlayer);
    }

}
