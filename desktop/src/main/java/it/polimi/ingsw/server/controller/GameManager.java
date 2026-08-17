package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.communication.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Class to keep all the games organized and accessible.
 */
public class GameManager {

    private static final Logger logger = LogManager.getLogger(GameManager.class);
    List<GameInterface> gameList;

    public GameManager() {
        this.gameList = new ArrayList<>();
    }

    public void createGame(GameType type, Set<Client> clients) {
        GameInterface gameInterface = new GameInterface(type, clients);
        gameList.add(gameInterface);
        clients.forEach(client -> client.setGameInterface(gameInterface));
    }

    public int countGames(GameType type) {
        return (int) gameList
                .stream()
                .filter(game -> game.getGameType() == type)
                .count();
    }

    public Map<GameType, Integer> getActiveGames() {
        Map<GameType, Integer> activeGames = new HashMap<>();
        for (GameType gameType : GameType.values()) {
            activeGames
                .put(gameType,
                    gameList
                        .stream()
                        .filter(x -> x.getGameType().equals(gameType) &&
                                     x.isActive())
                        .toList().size());
        }
        return activeGames;
    }
}
