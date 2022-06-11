package it.polimi.ingsw.communication.message.subclasses;

import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.MessageType;
import it.polimi.ingsw.server.controller.GameType;

import java.util.Map;
import java.util.Set;

/**
 * NEWER version of lobby info contains all the lobby.
 * All the players connected for each game type and the number of active games for each gametipe
 */
public class LobbyInfo extends Message {
    private final Map<GameType, Integer> activeGames;
    private final Map<GameType, Set<String>> clientsInLobbyMap;
    public LobbyInfo(Map<GameType, Set<String>> clientsInLobbyMap, Map<GameType, Integer> activeGames) {
        super(MessageType.LOBBYINFO);
        this.clientsInLobbyMap = clientsInLobbyMap;
        this.activeGames = activeGames;
    }

    public Map<GameType, Integer> getActiveGames() {return activeGames;}

    public Map<GameType, Set<String>> getClientsInLobbyMap() {return clientsInLobbyMap;}

    @Override
    public String toString() {
        return "I'm a Lobby Info message"; //FIXME
    }
}
