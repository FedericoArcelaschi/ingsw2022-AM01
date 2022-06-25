package it.polimi.ingsw.server.communication;

import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.server.controller.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that handles game lobbies. Its purpose is to create games upon reaching the right amount of players,
 * and notify players of the lobbies' status.
 */
public class LobbyManager {

    private static final Logger logger = LogManager.getLogger(LobbyManager.class); //.getName?

    /**
     * Game handler. Keeps all the live game information.
     */
    private final GameManager gameManager = new GameManager();
    /**
     * Map that contains the players waiting in the queue to play
     */
    private final Map<GameType, Set<Client>> gameClientsMap = new EnumMap<>(GameType.class);
    /**
     * List of players that didn't specify their preferences. Are updated with the lobby-infos.
     */
    private final Set<Client> clientsToInform = new HashSet<>();

    public LobbyManager() {
        logger.info("waiting room started");
        for(GameType g : GameType.values()) {
            gameClientsMap.put(g, new HashSet<>());
        }
    }

    public void addPlayerNoPreferences(Client client) {
        logger.info("new client added with no preferences. connected clients in lobby: " + getSumClientsInLobby());
        clientsToInform.add(client);
        sendLobbyInfo(client);
    }

    /**
     * Adds a player to the requested waiting room. Informs all the players  of the same lobby that the state of the lobby changed.
     */
    public void addPlayer(Client client, Preferences preferences) {
        String username = validUsername(preferences.username());
        client.setUsername(username);
        logger.info("client on port" + client.clientsSocket().getPort());
        logger.info("\t" + preferences.username() + " << joined the lobby. connected clients in lobby: " + getSumClientsInLobby());
        clientsToInform.remove(client);
        Set<Client> oldClientList = gameClientsMap.get(preferences.getGameType());
        oldClientList.add(client);
        gameClientsMap.replace(
                preferences.getGameType(),
                oldClientList);
        submitGame(preferences.getGameType());
        informPlayers();
    }

    private String validUsername(@NotNull String username) {
        @NotNull String finalUsername = username;
        boolean isPresentName
                = gameClientsMap.values().stream()
                .flatMap(Collection::stream)
                .map(Client::username)
                .anyMatch(name -> Objects.equals(name, finalUsername));
        if(isPresentName)
            return validUsername(username + "*");
        return username;
    }

    public int countGames(GameType type) {
        return this.gameManager.countGames(type);
    }

    /**
     * Sends to all the players in the lobby an updated
     */
    private void informPlayers() {
       gameClientsMap.values().stream()
                .flatMap(Collection::stream)
                .forEach(this::sendLobbyInfo);
       clientsToInform.forEach(this::sendLobbyInfo);
    }

    /**
     * Computes the game type according to the player's preferences, if there are enough players.
     * Returns null otherwise.
     */
    private void submitGame(@NotNull GameType type) {
        if(gameClientsMap.get(type).size() == type.nPlayer) {
            logger.info("Server: created " + (type.expertMode ? "expert" : "normal") + " game " + gameManager.countGames(type) + " with players: " + gameClientsMap.get(type).stream().map(Client::username).toList());
            gameManager.createGame(type, gameClientsMap.get(type));
            gameClientsMap.get(type).clear();
        }
    }

    private void sendLobbyInfo(Client client) {
        client.send(getLobbyInfo());
        logger.info("sent lobby info to client " + client.username() + " on port. " + client.clientsSocket().getPort());
    }

    private LobbyInfo getLobbyInfo() {
        Set<String> clients; //FIXME: implement con uno stream? sarebbe bello
        Map<GameType, Set<String>> gameTypeUsernameMap = new EnumMap<>(GameType.class);
        for (GameType g : GameType.values()) {
            clients = gameClientsMap.get(g).stream()
                    .map(Client::username)
                    .collect(Collectors.toSet());
            gameTypeUsernameMap.put(g, clients);
        }
        return new LobbyInfo(gameTypeUsernameMap, gameManager.getActiveGames());
    }

    private int getSumClientsInLobby() {
        return clientsToInform.size() +
                (int)   gameClientsMap.values().stream()
                        .flatMap(Collection::stream)
                        .filter(Objects::nonNull)
                        .count();
    }

    public void remove(Client client) {
        if(!clientsToInform.remove(client))
            gameClientsMap.forEach((key, value) -> value.remove(client));
        clientsToInform.forEach(this::sendLobbyInfo);
        gameClientsMap.forEach(
                (key, value) ->
                value.forEach(this::sendLobbyInfo));
    }
}
