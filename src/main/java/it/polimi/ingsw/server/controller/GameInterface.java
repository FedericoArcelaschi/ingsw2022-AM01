package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.subclasses.EndGame;
import it.polimi.ingsw.communication.message.subclasses.Error;
import it.polimi.ingsw.server.communication.Client;
import it.polimi.ingsw.server.model.baseLogic.Team;
import javafx.scene.control.Alert.AlertType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * class to adapt the communication layer to the controller.
 */
public class GameInterface {

    private static final Logger logger = LogManager.getLogger(GameInterface.class);
    private final Game game;
    private final Set<Client> clients;
    private final GameType gameType;
    private boolean active;

    public GameInterface(GameType gameType, Set<Client> clients) {
        logger.info(this + ": creating a new instance with players: " + clients.stream().map(Client::username).toList());
        logger.info("sockets are: " + clients.stream().map(Client::clientsSocket).toList());
        this.game       = new Game(gameType, clients.stream().map(Client::username).toList());
        this.clients    = new HashSet<>(clients);
        this.gameType   = gameType;
        this.active     = true;
        send(game.updateAll());
    }

    public void executeCommand(Command command, Client client) {
        logger.info(this + " is executing command: "+ command);
        try {
            clients.forEach(
                    client1 -> {
                        if(client1.equals(client))
                                command.setUsername(client.username());
                    });
            send(game.executeCommand(command));
        } catch (Exception e) {
            logger.info("exception during a Game move:\n\t", e); //should be a WARNING
        }
    }

    private void send(@NotNull Map<String, Message> usernameMessageMap) {
        logger.info(this + " is sending an update to the client(s).");
        logger.info("clients: " + clients.stream().map(Client::username).collect(Collectors.toSet()));
        usernameMessageMap.forEach(
                (key, value) -> clients.stream()
                .filter(i -> i.username().equals(key))
                .findFirst()
                .ifPresentOrElse(client -> client.send(value),
                        () -> logger.info("client not in game!")));
    }

    public void endGame(Client client) {
        clients.remove(client);
        clients.forEach(Client::putInLobby);
        sendEndGameMsg(client);
        active = false;
    }

    private void sendEndGameMsg(Client client) {
            clients.forEach(client1 -> client1.send(
                    new EndGame("Player " + client.username() + " disconnected. The game is over. \nPress any key to continue.",
                            AlertType.ERROR,
                            "",
                            Team.BLACK)));
    }

    public GameType getGameType() {
        return gameType;
    }

    public boolean isActive() {
        return active;
    }
}