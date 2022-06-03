package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;

import java.util.List;
import java.util.Map;

public class ExpertCastleData extends CastleData {
    private final int coins;

    public ExpertCastleData(String username,
                            List<StudentColor> waitingRoom,
                            List<StudentColor> diningRoom,
                            List<String> deck,
                            String lastPlayedCard,
                            Team towerColor,
                            boolean isMyCastle, int coins) {
        super(username, waitingRoom, diningRoom, deck, lastPlayedCard, towerColor, isMyCastle);
        this.coins = coins;
    }
    //To add: Coins.


}
