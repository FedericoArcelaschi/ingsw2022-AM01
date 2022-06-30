package it.polimi.ingsw.communication.message.subclasses;

import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.MessageType;
import it.polimi.ingsw.server.controller.GameType;
import it.polimi.ingsw.startUp.Outputs;

import java.util.*;

/**
 * NEWER version of lobby info contains all the lobby.
 * All the players connected for each game type and the number of active games for each gametipe
 */
public class LobbyInfo extends Message {
    private final Map<GameType, Integer> activeGames;
    private final List<Lobby> lobbies;

    public LobbyInfo(Map<GameType, Set<String>> clientsInLobbyMap, Map<GameType, Integer> activeGames) {
        super(MessageType.LOBBYINFO);
        this.activeGames = activeGames;
        lobbies = new ArrayList<>();
        for (GameType gt: clientsInLobbyMap.keySet()) {
            lobbies.add(new Lobby(gt, clientsInLobbyMap.get(gt)));
        }
    }

    public Map<GameType, Integer> getActiveGames() {
        return activeGames;
    }

    public List<Lobby> getLobbies() {
        return new ArrayList<>(lobbies);
    }

    @Override
    public String toString() {
        final int SIZE = 87;
        StringBuilder builder = new StringBuilder();

        builder .append(Outputs.CLEAR_SCREEN)
                .append("-".repeat(SIZE - 2))
                .append("||")
                .append("ACTIVE GAMES:||\n");
        for (int i = 0; i< GameType.values().length; i++) {
            GameType gameType = GameType.values()[i];
            int numberOfDrawnPlayers = 0;
            builder.append("|               |  ")
                    .append(gameType.expertMode ? "expert" : "normal")
                    .append(" mode  ||");
            playerInLobbyMap.computeIfAbsent(gameType, k -> new HashSet<>());
            for (String player : (playerInLobbyMap.get(gameType).stream().toList())) {
                numberOfDrawnPlayers++;
                if (player.length() > 10) {
                    builder.append(" ")
                            .append(player, 0, 7)
                            .append("...")
                            .append(' ')
                            .append('|');
                } else {
                    builder.append(" ")
                            .append(player)
                            .append(" ".repeat(10 - player.length()))
                            .append(' ')
                            .append('|');
                }
            }
            for (; numberOfDrawnPlayers < gameType.nPlayer; numberOfDrawnPlayers++) { //padding for empty lobbys
                builder.append(' ')
                        .append(" ".repeat(10))
                        .append(' ')
                        .append('|');
            }
            for (; numberOfDrawnPlayers < 4; numberOfDrawnPlayers++) {
                builder.append(' ')
                        .append("x".repeat(10))
                        .append(' ')
                        .append("|");
            }
            builder.append("|")
            //ACtive games:
                    .append("  --> ");
            if(activeGames.containsKey(gameType))
                builder.append(activeGames.get(gameType))
                        .append(" <--  ||\n");
            else
                builder .append("NONE   ||\n");
            if (!gameType.expertMode)
                builder .append("|   ")
                        .append(gameType.nPlayer)
                        .append(" PLAYERS")
                        .append("   |---------------||---------------------------------------------------");
            else
                builder .append("-".repeat(SIZE - 2));
            builder.append("||             ||\n");
        }
        return builder.toString();
    }

    public static class Lobby {
        private final GameType gameType;
        private final Set<String> connectedPlayers;
        private final String formattedPlayers;

        public Lobby(GameType gameType, Set<String> connectedPlayers) {
            this.gameType = gameType;
            this.connectedPlayers = connectedPlayers;
            this.formattedPlayers = connectedPlayers.toString().replace("[", "").replace("]", "");
        }

        public GameType getGameType() {
            return gameType;
        }

        public Set<String> getConnectedPlayers() {
            return connectedPlayers;
        }

        public String getFormattedPlayers() {
            return formattedPlayers;
        }
    }
}

