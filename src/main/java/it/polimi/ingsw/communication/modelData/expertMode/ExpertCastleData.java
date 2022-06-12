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
                            boolean isMyCastle,
                            Integer coins) {
        super(username, waitingRoom, diningRoom, deck, lastPlayedCard, towerColor, nTower, teachers, isMyCastle);
        this.coins = coins;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(super.toString());
        int indexToInsert = s.indexOf("Team:");
        s.insert(indexToInsert, "Coins: " + coins + "\n\t\t");
        return s.toString();
    }

}
