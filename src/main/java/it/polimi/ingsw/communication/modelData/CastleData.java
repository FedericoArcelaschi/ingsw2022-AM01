package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CastleData {
    private final String username;
    private final List<Color> waitingRoom;
    private final Map<Color, Integer> diningRoom;
    private final List<Card> deck;
    private final Card lastPlayedCard;
    private final Team towerColor;

    public CastleData(
            String username,
            List<Color> waitingRoom,
            Map<Color, Integer> diningRoom,
            List<Card> deck,
            Card lastPlayedCard,
            Team towerColor
    ) {
        this.username = username;
        this.waitingRoom = waitingRoom;
        this.diningRoom = diningRoom;
        this.deck = deck;
        this.lastPlayedCard = lastPlayedCard;
        this.towerColor = towerColor;
    }

    public CastleData(String username, Castle castle) {
        this(
                username,
                castle.getWaitingRoom(),
                castle.getDiningRoom(),
                castle.getDeck(),
                castle.getLastCardPlayed(),
                castle.getTeam()
        );
    }

    public String username() {
        return username;
    }

    public List<Color> waitingRoom() {
        return waitingRoom;
    }

    public Map<Color, Integer> diningRoom() {
        return diningRoom;
    }

    public List<Card> deck() {
        return deck;
    }

    public Card lastPlayedCard() {
        return lastPlayedCard;
    }

    public Team towerColor() {
        return towerColor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CastleData) obj;
        return Objects.equals(this.username, that.username) &&
                Objects.equals(this.waitingRoom, that.waitingRoom) &&
                Objects.equals(this.diningRoom, that.diningRoom) &&
                Objects.equals(this.deck, that.deck) &&
                Objects.equals(this.lastPlayedCard, that.lastPlayedCard) &&
                Objects.equals(this.towerColor, that.towerColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, waitingRoom, diningRoom, deck, lastPlayedCard, towerColor);
    }

    @Override
    public String toString() {
        return "CastleData[" +
                "username=" + username + ", " +
                "waitingRoom=" + waitingRoom + ", " +
                "diningRoom=" + diningRoom + ", " +
                "deck=" + deck + ", " +
                "lastPlayedCard=" + lastPlayedCard + ", " +
                "towerColor=" + towerColor + ']';
    }

}
