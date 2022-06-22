package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.communication.ClientList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Class to keep all the games organized and accessible.
 */
public class GameManager {

    private static Logger logger = LogManager.getLogger(GameManager.class);
    List<GameInterface> gameList;

    public GameManager() {
        this.gameList = new ArrayList<>();
    }

    public void createGame(GameType type, ClientList clients) {
        GameInterface gameInterface = new GameInterface(type, clients);
        gameList.add(gameInterface);
        clients.getClients().forEach(client->client.setGameInterface(gameInterface));
    }

    public int countGames(GameType type) {
        return (int) gameList.stream().filter(i -> i.getGameType()==type).count(); //TODO: maybe implement a map with games/player and add the option to only view  a game
    }

    private final Map<GameType, Integer> activeGames = new EnumMap<>(GameType.class);


    public Map<GameType, Integer> getActiveGames() {
        Map<GameType, Integer> activeGames = new HashMap<>();
        for (GameType g : GameType.values()) {
            activeGames.put(g, gameList.stream().filter(x->x.getGameType().equals(g) && x.isActive()).toList().size());
        }
        //return gameList.stream().flatMap(gameInterface -> activeGames.computeIfPresent(gameInterface.getGameType(), (g,i)->i++). ); FIXME
        return activeGames;
    }
}
