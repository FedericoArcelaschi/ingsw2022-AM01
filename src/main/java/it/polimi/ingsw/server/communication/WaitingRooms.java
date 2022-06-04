package it.polimi.ingsw.server.communication;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.packet.message.LobbyInfo;
import it.polimi.ingsw.server.controller.Game;
import it.polimi.ingsw.server.controller.GameType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param gameType the provided gametype.
     * @param serverReceiver the serverReceiver of the new player.
     * @throws IOException
     */
    public void addPlayer(GameType gameType, ServerReceiver serverReceiver) {
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
            Packet packet = new Packet(lobbyInfo);
            List<ServerReceiver> playersInLobby = new ArrayList<>(gameSocketMap.get(gameType).subList((gameSocketMap.get(gameType).size()-gameSocketMap.get(gameType).size()%numberOfPlayers),gameSocketMap.get(gameType).size()));
            for(ServerReceiver sr : playersInLobby){
                PrintWriter out;
                try {
                    out = new PrintWriter(sr.getSocket().getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                out.println(packet.toJson());
            }
        }
    }

    /**
     * Computes the game type according to the player's preferences, if there are enough players.
     * Returns null otherwise.
     * @param gameId
     * @return the correct game.
     */
    public Game submitGame(int gameId, GameType type) {
        if(gameSocketMap.get(type).size() == type.nPlayer) {
            Game g = new Game(type, gameSocketMap.get(type));
            gameSocketMap.get(type).clear();
            return g;
        }
        return null;
    }

    //Do I need to add a removeFromQueue?
}
