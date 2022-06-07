package it.polimi.ingsw.server.communication;

import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.server.controller.Game;
import it.polimi.ingsw.server.controller.GameType;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WaitingRooms {
    private final Map<GameType, List<ServerReceiver>> gameSocketMap = new HashMap<>();

    public WaitingRooms() {
        for(GameType g : GameType.values()) {
            gameSocketMap.computeIfAbsent(g, k -> new ArrayList<>());
        }
    }

    public Map<GameType, List<ServerReceiver>> getGameSocketMap() {
        return gameSocketMap;
    }

    /**
     * Adds a player to the requested lobby. Informs players of the same lobby that the state of the lobby changed.
     * @param serverReceiver the serverReceiver of the new player.
     */
    public void addPlayer(GameType gameType, ServerReceiver serverReceiver) {
        while (gameSocketMap.get(gameType).stream().map(ServerReceiver::getUsername).toList().contains(serverReceiver.getUsername())) {
            serverReceiver.setUsername(serverReceiver.getUsername() + "*");
        }
        gameSocketMap.get(gameType).add(serverReceiver);
        informPlayers(gameType, gameType.nPlayer);
    }

    /**
     * Method used to send to the rest of the players who else joined the lobby.
     * @param gameType the provided gametype.
     * @param numberOfPlayers the amount of players expected by the game type.
     */
    private void informPlayers(GameType gameType, int numberOfPlayers) {
        if(gameSocketMap.get(gameType).size() < numberOfPlayers){
            List<String> playersIn = new ArrayList<>(gameSocketMap.get(gameType).stream().map(ServerReceiver::getUsername).toList());
            Message lobbyInfo = new LobbyInfo(playersIn, gameType);
            List<ServerReceiver> playersInLobby = new ArrayList<>(gameSocketMap.get(gameType));
            for(ServerReceiver sr : playersInLobby){
                PrintWriter out;
                try {
                    out = new PrintWriter(sr.getSocket().getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                out.println(lobbyInfo.toJson());
            }
        }
    }

    /**
     * Computes the game type according to the player's preferences, if there are enough players.
     * Returns null otherwise.
     *
     * @param gameId a sequential game identificator
     * @return the correct game.
     */
    public Game submitGame(int gameId, GameType type) {
        if(gameSocketMap.get(type).size() == type.nPlayer) {
            Collections.reverse(gameSocketMap.get(type));
            Game g = new Game(type, gameSocketMap.get(type));
            gameSocketMap.get(type).clear();
            return g;
        }
        return null;
    }

    //Do I need to add a removeFromQueue?
}
