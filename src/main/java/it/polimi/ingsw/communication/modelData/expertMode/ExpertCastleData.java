package it.polimi.ingsw.communication.modelData.expertMode;

import it.polimi.ingsw.communication.modelData.CastleData;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ExpertCastleData extends CastleData {
    private final int coins;

    public ExpertCastleData(String username,
                            List<StudentColor> waitingRoom,
                            EnumMap<StudentColor, Integer> diningRoom,
                            List<String> deck,
                            String lastPlayedCard,
                            Team towerColor,
                            int nTower,
                            Map<StudentColor, Team> teachers,
                            boolean isMyCastle, int coins) {
        super(username, waitingRoom, diningRoom, deck, lastPlayedCard, towerColor, nTower, teachers, isMyCastle);
        this.coins = coins;
    }

    public int coins() {
        return coins;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\n\t\tStudents in waiting room: ");
        for (StudentColor student : waitingRoom) {
            s.append(student.toStringColored());
            s.append(", ");
        }
        removesComma(s);
        s.append("\n\t\tStudents in dining room: ");
        for (StudentColor student : diningRoom.keySet()) {
            s.append(student.toStringColored());
            s.append(": ");
            s.append(diningRoom.get(student));
            s.append(", ");
        }
        removesComma(s);
        s.append("\n\t\tCoins: ").append(coins);
        if (isMyCastle()) {
            s.append("\n\t\tAvailable Cards: ");
            for (String card : deck())
                s.append(card).append(", ");
            removesComma(s);
            if (lastPlayedCard != null)
                s.append("\n\t\tThe last card you played is: ").append(lastPlayedCard);
            else
                s.append("\n\t\tYou didn't played any card yet.");
        } else if (lastPlayedCard() != null)
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

}
