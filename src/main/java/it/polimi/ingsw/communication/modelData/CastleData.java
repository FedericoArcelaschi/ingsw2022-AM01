package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;

import java.util.*;

public class CastleData {
    protected final String username;
    protected final List<StudentColor> waitingRoom;
    protected final EnumMap<StudentColor, Integer> diningRoom;
    protected final List<String> deck;
    protected final String lastPlayedCard;
    protected final Team towerColor;
    private final int nTower;
    private final boolean isMyCastle;
    private final Map<StudentColor, Team> teachers;

    public CastleData(String username, List<StudentColor> waitingRoom, EnumMap<StudentColor, Integer> diningRoom, List<String> deck, String lastPlayedCard, Team towerColor, int nTower, Map<StudentColor, Team> teachers, boolean isMyCastle) {
        this.username = username;
        this.waitingRoom = waitingRoom;
        this.diningRoom = new EnumMap<>(diningRoom);
        this.towerColor = towerColor;
        this.deck = deck;
        this.lastPlayedCard = lastPlayedCard;
        this.nTower = nTower;
        this.teachers = teachers;
        this.isMyCastle = isMyCastle;
    }

    public boolean getIsMyCastle(){
        return isMyCastle;
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
        s       .append("\n\t")
                .append("Teachers in the castle: ")
                .append(teachers.entrySet().stream().filter(entry->entry.getValue()==towerColor).map(i-> i.getKey().toStringColored() + " "))
                .append("\n\tStudents in dining room: ");
        for (StudentColor student : diningRoom.keySet()) {
            s       .append(student.toStringColored())
                    .append(": ")
                    .append(diningRoom.get(student))
                    .append(", ");
        }
        removesComma(s);
        s.append("\n\tStudents in waiting room: ");
        for (StudentColor student : waitingRoom) {
            s.append(student.toStringColored());
            s.append(", ");
        }
        removesComma(s);
        if (isMyCastle) {
            s.append("\n\t\tAvailable Cards: ");
            for (String card : deck)
                s.append(card).append(", ");
            removesComma(s);
            if (lastPlayedCard != null)
                s.append("\n\t\tThe last card you played is: ").append(lastPlayedCard);
            else
                s.append("\n\t\tYou didn't played any card yet.");
        } else if (lastPlayedCard != null)
            s.append("\n\t\tThe last played card is: ").append(lastPlayedCard);
        else
            s.append("\n\t\tThe player has not played any card yet.");
        s.append("\n\t\tTeam: ");
        s.append(towerColor);
        return s.toString();
    }

    private void removesComma(StringBuilder s) {
        s.replace(s.length() - 2, s.length(), "");
    }

    public String username() {
        return username;
    }

    public List<StudentColor> waitingRoom() {
        return waitingRoom;
    }

    public EnumMap<StudentColor, Integer> diningRoom() {
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

    public boolean isMyCastle() {
        return isMyCastle;
    }

    public int nTower() {
        return nTower;
    }

    public Map<StudentColor, Team> teachers() {
        return teachers;
    }

    public int coins() {
        return 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, waitingRoom, diningRoom, deck, lastPlayedCard, towerColor, isMyCastle);
    }


}
