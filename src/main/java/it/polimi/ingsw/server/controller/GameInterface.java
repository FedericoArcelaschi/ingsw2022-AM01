package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.MessageType;
import it.polimi.ingsw.communication.message.subclasses.EndGame;
import it.polimi.ingsw.communication.message.subclasses.Error;
import it.polimi.ingsw.communication.message.subclasses.WinUpdate;
import it.polimi.ingsw.server.communication.Client;
import it.polimi.ingsw.server.communication.ClientList;
import it.polimi.ingsw.server.model.baseLogic.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class to adapt the communication layer to the controller.
 */
public class GameInterface {

    private static final Logger logger = LogManager.getLogger(GameInterface.class);

    private final Game game;
    private final ClientList clients;
    private final GameType gameType;

    public GameInterface(GameType gameType, ClientList clients) {
        logger.info(this + ": creating a new instance with players: " + clients.getClients().stream().map(Client::username).toList());
        logger.info("sockets are: " + clients.getClients().stream().map(Client::clientsSocket).toList());

        this.game       = new Game(gameType, clients.getClients().stream().map(Client::username).toList());
        this.clients    = new ClientList(clients);
        this.gameType   = gameType;
        send(game.updateAll());
    }

    public void executeCommand(Command command, Socket socket) {
        logger.info(this + " is executing command: "+ command);
        try {
            if (clients.getClients().stream().anyMatch(i -> (i.clientsSocket().equals(socket)) && (i.username().equals(command.getUsername())))) {
                send(game.executeCommand(command));
            } else
                new Client(socket).send(new Error("you are in the wrong game. userID-socket don't match. Quit."));
        } catch (Exception e) {
            logger.info("exception during a Game move:\n\t", e); //should be a WARNING
        }
    }

    private void send(MessageUsernameSet messageUsernameSet) {
        logger.info(this + " is sending an update to the client(s).");
        logger.info("clients: " + clients.getClients().stream().map(Client::clientsSocket).collect(Collectors.toSet()));
        messageUsernameSet.values()
                .forEach(
                    messageUsername ->
                        {clients.getClients().stream()
                         .filter(i -> i.username().equals(messageUsername.addressee()))
                         .forEach(client -> client.send(messageUsername.message()));}
                );
    }

    public GameType getGameType() {
        return gameType;
    }

    public ClientList getClients() {
        return clients;
    }

    public void endGame(Client client) {
        sendEndGameMsg(client);
    }

    private void sendEndGameMsg(Client client) {
        boolean draw = game.getBoard().placedTowers().values().size() != clients.size();
        Team winner = game.getBoard().placedTowers().entrySet().stream()
                .max((team1, team2) -> team1.getValue() > team2.getValue() ? 1 : 0)
                .get()
                .getKey();
        for(Client c : clients.getClients()) {
            c.send(new EndGame("Player " + client.username() + " disconnected. The game is over."));
            c.send(new EndGame(
                    draw ? "The game ended in a draw." : "The winner is: " + winner.toString()));
        }
    }
}