package it.polimi.ingsw.communication.message.subclasses;

import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.MessageType;
import it.polimi.ingsw.server.controller.GameType;

import java.util.ArrayList;
import java.util.List;

public class LobbyInfo extends Message {

    private final List<String> players;
    private final GameType gameType;

    public LobbyInfo(List<String> players, GameType g){
        super(MessageType.LOBBYINFO);
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
