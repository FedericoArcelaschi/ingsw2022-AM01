package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;

import java.util.List;
import java.util.Map;

public record CastleData(
        String username,
        List<Color> waitingRoom,
        Map<Color,Integer> diningRoom,
        List<Card> deck,
        Card lastPlayedCard,
        Team towerColor
) {
    public CastleData(String username, Castle castle){
        this(
                username,
                castle.getWaitingRoom(),
                castle.getDiningRoom(),
                castle.getDeck(),
                castle.getLastCardPlayed(),
                castle.getTeam()
                );
    }
}
