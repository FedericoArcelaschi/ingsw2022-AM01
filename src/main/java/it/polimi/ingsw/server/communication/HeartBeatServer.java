package it.polimi.ingsw.server.communication;

import it.polimi.ingsw.client.userInterface.cli.Cli;
import it.polimi.ingsw.communication.message.subclasses.EndGame;
import it.polimi.ingsw.communication.message.subclasses.Ping;
import org.apache.logging.log4j.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executors;

/**
 * Class that implements the heart-beat protocol.
 * if a client doesn't ping back before TIMEOUT //TODO it gets removed from his game (the game end) or queue.
 */
public class HeartBeatServer implements Runnable {

    private final static Logger logger = LogManager.getLogger(HeartBeatServer.class);

    private final int TIMEOUT = 5000; // [ms]
    private final Set<Client> clients = new HashSet<>();
    private final Set<Socket> heartBeats = new HashSet<>();

    /**
     * Adds a client to the connected clients list and starts the heartbeat on him as well
     */
    public void addClient(Client newClient) {
        clients.add(newClient);
        logger.info("Added client");
    }

    /**Validate the connection with the client for another 5 seconds.
     * @param socket the key in the Heartbeat server
     */
    public synchronized void validateResponse(Socket socket) {
        // logger.info("client @ port " + socket.getPort() + "sent a ping validation.");
        if (heartBeats.contains(socket)) {
            heartBeats.remove(socket);
            logger.info("Ping received from port: " + socket.getPort() + ". Pinging back...");
        }
        else
            logger.info("client in port: " + socket.getLocalPort() + " shouldn't be connected");
    }

    /**
     * Heart-beat server main thread keeps executing the following methods to keep track of the connected players.
     */
    public void run() {
        long previousTime = new Date().getTime();
        sendsPing();
        waits(previousTime);
        synchronized (heartBeats) {
            heartBeats.forEach(this::removeClient);
        }
        run();
    }

    private void sendsPing() {
        for (Socket client : clients.stream().map(Client::clientsSocket).toList()) {
            PrintWriter out;
            try {
                out = new PrintWriter(client.getOutputStream(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String pingMessage = new Ping().toJson();
            out.println(pingMessage);
            heartBeats.add(client);
            //FIXME -> here the idea is to put the PING UUID associated to each client's ping. Not needed. Not implemented.
        }
    }

    private void waits(long previousTime) {
        try {
            Thread.sleep(TIMEOUT - previousTime + new Date().getTime());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeClient(Socket client) {
        Client c = clients.stream().filter(x -> client.equals(x.clientsSocket())).findFirst().orElse(null);
        sendEndGameMsg(c);
        clients.remove(c);
        heartBeats.remove(client);
        logger.info("User on port " + client.getPort() + " disconnected.");
    }

    private void sendEndGameMsg(Client client) {
        List<Socket> playersInGame = new ArrayList<>(client.getGameInterface().getClients().getClients().stream().map(Client::clientsSocket).toList());
        for (Socket s : playersInGame) {
            PrintWriter out;
            try {
                out = new PrintWriter(s.getOutputStream(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String endGameMessage = new EndGame("Player " + client.username() + " disconnected. The game is now over.").toJson();
            out.println(endGameMessage);
        }
    }
}
