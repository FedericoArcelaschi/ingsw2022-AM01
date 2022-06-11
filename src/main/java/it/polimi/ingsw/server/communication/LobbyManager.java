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

public class LobbyManager {

    private static Logger logger = LogManager.getLogger(LobbyManager.class); //.getName?

    /**
     * Game handler. Keeps all the live game information.
     */
    private final GameManager gameManager = new GameManager();
    /**
     * Map that contains the players waiting in the queue to play
     */
    private final Map<GameType, ClientList> gameClientsMap = new EnumMap<>(GameType.class);
    /**
     * List of players that didn't specify their preferences. Are updated with the lobby-infos.
     */
    private final Set<Socket> clientsToInform = new HashSet<>();

    public LobbyManager() {
        logger.info("waiting room started");
        for(GameType g : GameType.values()) {
            gameClientsMap.put(g, new ClientList());
        }
    }

    public void addPlayerNoPreferences(Socket socket) {
        logger.info("new client added with no preferences. connected clients in lobby: " + getSumClientsInLobby());
        clientsToInform.add(socket); //FIXME
        sendLobbyInfo(socket);
    }

    /**
     * Adds a player to the requested waiting room. Informs all the players  of the same lobby that the state of the lobby changed.
     */
    public void addPlayer(Client client, Preferences preferences) {
        Socket socket = client.clientsSocket();
        client.setUsername(preferences.username());
        logger.info("client on port" + socket.getPort());
        logger.info("\t" + preferences.username() + " << joined the lobby. connected clients in lobby: " + getSumClientsInLobby());
        clientsToInform.remove(socket);
        ClientList oldClientList = gameClientsMap.get(preferences.getGameType());
        gameClientsMap.replace(
                preferences.getGameType(),
                oldClientList.add(client));
        submitGame(preferences.getGameType());
        informPlayers();
    }

    public int countGames(GameType type) {
        return this.gameManager.countGames(type);
    }

    /**
     * Sends to all the players in the lobby an updated
     */
    private void informPlayers() {
       gameClientsMap.values().stream()
                .flatMap(clientList -> clientList.getClients().stream())
                .map(Client::clientsSocket)
                .forEach(this::sendLobbyInfo);
       clientsToInform.forEach(this::sendLobbyInfo);
    }

    /**
     * Computes the game type according to the player's preferences, if there are enough players.
     * Returns null otherwise.
     */
    private void submitGame(@NotNull GameType type) {
        if(gameClientsMap.get(type).getClients().size() == type.nPlayer) {
            logger.info("Server: created " + (type.expertMode ? "expert" : "normal") + " game " + gameManager.countGames(type) + " with players: " + gameClientsMap.get(type).getClients().stream().map(Client::username).toList());
            gameManager.createGame(type, gameClientsMap.get(type));
            gameClientsMap.get(type).clear();
        }
    }

    private void sendLobbyInfo(Socket socket) {
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        out.println(getLobbyInfo().toJson());
    }

    private LobbyInfo getLobbyInfo() {
        Set<String> clients; //FIXME: implement con uno stream? sarebbe bello
        Map<GameType, Set<String>> gameTypeUsernameMap = new EnumMap<>(GameType.class);
        for (GameType g : GameType.values()) {
            clients = gameClientsMap.get(g).getClients().stream()
                    .map(Client::username)
                    .collect(Collectors.toSet());
            gameTypeUsernameMap.put(g, clients);
        }
        return new LobbyInfo(gameTypeUsernameMap, gameManager.getActiveGames());
    }

    private int getSumClientsInLobby() {
        return clientsToInform.size() +
                (int) gameClientsMap.values().stream()
                        .map(ClientList::getClients)
                        .flatMap(Collection::stream)
                        .filter(Objects::nonNull).count();
    }
}
