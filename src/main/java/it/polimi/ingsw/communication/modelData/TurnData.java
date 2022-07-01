package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.server.model.baseLogic.TurnPhase;

import java.util.List;
import java.util.Objects;

public class TurnData {
    private final List<String> playerOrder;
    private final TurnPhase currentPhase;
    private final String currentPlayer;

    public TurnData(List<String> playerOrder, TurnPhase currentPhase, String currentPlayer) {
        this.playerOrder = playerOrder;
        this.currentPhase = currentPhase;
        this.currentPlayer = currentPlayer;
    }

    public List<String> actionOrder() {
        return playerOrder;
    }

    public TurnPhase currentPhase() {
        return currentPhase;
    }

    public String currentPlayer() {
        return currentPlayer;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\n\tGame Phase: ").append(currentPhase);
        s.append("\n\tTurn Order: ");
        for (String player : playerOrder) {
            s.append(player).append(", ");
        }
        removesComma(s);
        s.append("\n\tCurrent Player: ").append(currentPlayer);
        return s.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TurnData) obj;
        return Objects.equals(this.playerOrder, that.playerOrder) &&
                Objects.equals(this.currentPhase, that.currentPhase) &&
                Objects.equals(this.currentPlayer, that.currentPlayer);
    }

    private void removesComma(StringBuilder s) {
        s.replace(s.length() - 2, s.length(), "");
    }
}
