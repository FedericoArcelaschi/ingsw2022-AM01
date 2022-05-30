package it.polimi.ingsw.communication.packet.message;

import it.polimi.ingsw.controller.GameType;

import java.util.ArrayList;
import java.util.List;

public class LobbyInfoMessage implements Message{
    private final List<String> players;
    private final GameType g;

    public LobbyInfoMessage(List<String> players, GameType g){
        this.players = new ArrayList<>(players);
        this.g = g;
    }

    public List<String> getPlayers(){
        return players;
    }

    public GameType getGameType(){
        return g;
    }
}
