package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.model.baseLogic.Team;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CastleData {
    private final String username;
    private final List<StudentColor> waitingRoom;
    private final Map<StudentColor, Integer> diningRoom;
    private final List<String> deck;
    private final String lastPlayedCard;
    private final Team towerColor;
    private final boolean isMyCastle;

    public CastleData(String username, List<StudentColor> waitingRoom, Map<StudentColor, Integer> diningRoom, List<String> deck, String lastPlayedCard, Team towerColor, boolean isMyCastle) {
        this.username = username;
        this.waitingRoom = waitingRoom;
        this.diningRoom = diningRoom;
        this.deck = deck;
        this.lastPlayedCard = lastPlayedCard;
        this.towerColor = towerColor;
        this.isMyCastle = isMyCastle;
    }

    public String getUsername() {
        return username;
    }

    public List<StudentColor> getWaitingRoom() {
        return waitingRoom;
    }

    public Map<StudentColor, Integer> getDiningRoom() {
        return diningRoom;
    }

    public List<String> getDeck() {
        return deck;
    }

    public String getLastPlayedCard() {
        return lastPlayedCard;
    }

    public Team getTowerColor() {
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
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\n\t\tStudents in waiting room: ");
        for (StudentColor c : waitingRoom) {
            s.append(c).append(", ");
        }
        s.append("\n\t\tStudents in dining room: ");
        for (StudentColor c : diningRoom.keySet())
            s.append(c).append(": ").append(diningRoom.get(c).toString()).append(", ");
        if (isMyCastle) {
            s.append("\n\t\tAvailable Cards: ");
            for (String c : deck)
                s.append(c).append(", ");
        }
        if(lastPlayedCard != null)
            s.append("\n\t\tThe last played card is: ").append(lastPlayedCard);
        else
            s.append("\n\t\tThe player has not played any cards yet.");
        s.append("\n\t\tTeam: ").append(towerColor.toString());
        return s.toString();
    }

}
