package it.polimi.ingsw.communication.packet.message;

import it.polimi.ingsw.controller.GameType;

import java.util.ArrayList;
import java.util.List;

public class LobbyInfoMessage implements Message{
    private final List<String> players;
    private final GameType gameType;

    public LobbyInfoMessage(List<String> players, GameType g){
        this.players = new ArrayList<>(players);
        this.gameType = g;
    }

    public List<String> getPlayers(){
        return players;
    }

    public GameType getGameType(){
        return gameType;
    }

    @Override
    public String toString() {
        return "players in queue: " + players +
                "game type: " + gameType;
    }
}
