package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CastleData {
    private final String username;
    private final List<Color> waitingRoom;
    private final Map<Color, Integer> diningRoom;
    private final List<String> deck;
    private final String lastPlayedCard;
    private final Team towerColor;
    private final boolean isMyCastle;

    public CastleData(
            String username,
            List<Color> waitingRoom,
            Map<Color, Integer> diningRoom,
            List<String> deck,
            String lastPlayedCard,
            Team towerColor,
            boolean isMyCastle) {
        this.username = username;
        this.waitingRoom = waitingRoom;
        this.diningRoom = diningRoom;
        this.deck = deck;
        this.lastPlayedCard = lastPlayedCard;
        this.towerColor = towerColor;
        this.isMyCastle = isMyCastle;
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

    public List<String> deck() {
        return deck;
    }

    public String lastPlayedCard() {
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
        StringBuilder s = new StringBuilder();
        s.append("\n\t\tStudents in waitingroom: ");
        for (Color c : waitingRoom) {
            s.append(c.str).append(", ");
        }
        s.append("\n\t\tStudents in diningroom: ");
        for (Color c : diningRoom.keySet()) {
            s.append(c.str).append(": ").append(diningRoom.get(c).toString()).append(", ");
        }
        if (isMyCastle){
            s.append("\n\t\tAvailable Cards: ");
            for (String c : deck) {
                s.append(c.toString()).append(", ");
            }
        }
        if(lastPlayedCard != null) {
            s.append("\n\t\tThe last played card is: ").append(lastPlayedCard.toString());
        }else{
            s.append("\n\t\tThe player has not played any cards yet.");
        }
        s.append("\n\t\tTeam: ").append(towerColor.toString());
        return s.toString();
    }

}
