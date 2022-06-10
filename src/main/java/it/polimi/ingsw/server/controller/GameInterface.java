package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.subclasses.Error;
import it.polimi.ingsw.server.communication.Client;
import it.polimi.ingsw.server.communication.ClientList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;

/**
 * class to adapt the communication layer to the controller.
 */
public class GameInterface {

    private static Logger logger = LogManager.getLogger(GameInterface.class);

    private final Game game;
    private final ClientList clients;
    private final GameType gameType;

    public GameInterface(GameType gameType, ClientList clients) {
        logger.info(this + ": creating a new instance with players: " + clients.getClients().stream().map(Client::username).toList());
        logger.info("sockets are: " + clients.getClients().stream().map(Client::clientsSocket).toList());
        this.clients = new ClientList(clients);
        this.game = new Game(gameType, clients.getClients().stream().map(Client::username).toList());
        send(game.updateAll());
        this.gameType = gameType;
    }

    public void executeCommand(Command command, Socket socket) {
        logger.info(this + " is executing command: "+ command);
        if (clients.getClients().stream().anyMatch(i -> (i.clientsSocket().equals(socket)) && (i.username().equals(command.getUsername())))) {
            send(game.executeCommand(command));
        } else
            Client.send(new Error("you are in the wrong game. command-socket don't match. Quit."), socket);
    }

    private void send(MessageUsernameSet messageUsernameSet) {
        logger.info(this + " is sending an update to the clients.");
        messageUsernameSet.values()
                .forEach(messageUsername ->
                clients.getClients().stream()
                .filter(i -> i.username().equals(messageUsername.addressee()))
                .forEach(client -> client.send(messageUsername.message())));
    }

    public GameType getGameType() {
        return gameType;
    }

}