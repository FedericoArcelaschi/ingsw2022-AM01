package it.polimi.ingsw.communication.serverSide;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.LobbyInfoMessage;
import it.polimi.ingsw.controller.Game;
import it.polimi.ingsw.controller.GameType;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LobbyManager {
    private final Map<GameType, List<ServerReceiver>> gameTypePlayersMap = new HashMap<>();
    private final Map<GameType, List<String>> players = new HashMap<>();
    private int gameId; //should use this to "catalog" game
    private final Map<Integer, Game> games;

    public LobbyManager(Map<Integer, Game> games) {
        for(GameType g : GameType.values()) {
            gameTypePlayersMap.computeIfAbsent(g, k -> new ArrayList<>());
            players.computeIfAbsent(g, k -> new ArrayList<>());
        }
        this.games = games;
        gameId = 0;
    }

    public Map<GameType, List<String>> getPlayers() {
        return players;
    }


    /**
     * Adds a player to the requested lobby. Informs players of the same lobby that the state of the lobby changed.
     * @param gameType the provided gametype.
     * @param serverReceiver the serverReceiver of the new player.
     * @param nickname the player name. it's a key.
     */
    public void addPlayer(GameType gameType, ServerReceiver serverReceiver, String nickname) {
        gameTypePlayersMap.get(gameType).add(serverReceiver);
        if(players.get(gameType).contains(nickname)) {
            int i = 0;
            nickname.concat(String.valueOf(i));
            while (players.get(gameType).contains(nickname))
                nickname.replace(String.valueOf(i), String.valueOf(i++));
        }
        players.get(gameType).add(nickname);
        System.out.println(players);
        Game game = submitGame(gameType);
        if(game != null){
            for (ServerReceiver sr: gameTypePlayersMap.get(gameType)) {
                sr.setGame(game);
            }
        }
    }

    /**
     * Computes the game type according to the player's preferences, if there are enough players.
     * Returns null otherwise.
     * @return the correct game.
     */
    private Game submitGame(GameType gameType){
        List<String> playersInGameQueue  = new ArrayList<>(players.get(gameType));
        if(playersInGameQueue.size() >= gameType.nPlayer) {
            List<ServerReceiver> serverReceiverList = new ArrayList<>(gameTypePlayersMap.get(gameType));
            players.get(gameType).clear();
            gameTypePlayersMap.get(gameType).clear();
            return new Game(gameType, playersInGameQueue, serverReceiverList);
        }
        informPlayers(gameType);
        return null;
    }

    /**
     * Method used to send to the other players in the lobby an update on who's in.
     */
    private void informPlayers(GameType gameType) {
        int numberOfPlayers = gameType.nPlayer;
        if(players.get(gameType).size() < numberOfPlayers) {
            LobbyInfoMessage lobbyMessage = new LobbyInfoMessage(players.get(gameType), gameType);
            Packet packet = new Packet(MessageType.LOBBY, lobbyMessage);
            List<ServerReceiver> playerServerReceivers = new ArrayList<>(gameTypePlayersMap.get(gameType));
            for(ServerReceiver sr : playerServerReceivers) {
                PrintWriter out;
                try {
                    out = new PrintWriter(sr.getSocket().getOutputStream(), true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                out.println(new Gson().toJson(packet));
            }
        }
    }
}
