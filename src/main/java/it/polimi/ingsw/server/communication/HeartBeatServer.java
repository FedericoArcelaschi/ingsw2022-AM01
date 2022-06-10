package it.polimi.ingsw.server.communication;

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

    private final static Logger logger = LogManager.getLogger(HeartBeatServer.class); //.getName?

    private final int TIMEOUT = 5000; // [ms]
    private final Set<Socket> clients = new HashSet<>();
    private final Set<Socket> heartBeats = new HashSet<>();

    /**
     * Adds a client to the connected clients list and starts the heartbeat on him as well
     */
    public void addClient(Client newClient) {
        Socket newClientSocket = newClient.clientsSocket();
        logger.info("Added client");
        clients.add(newClientSocket);
    }

    /**Validate the connection with the client for another 5 seconds.
     * @param socket the key in the Heartbeat server
     */
    public synchronized void validateResponse(Socket socket) {
       // logger.info("client @ port " + socket.getPort() + "sent a ping validation.");
        if (heartBeats.contains(socket)) {
            heartBeats.remove(socket);
            logger.info("Ping received. Pinging back...");
        }
        else
            logger.warn("client in port: " + socket.getLocalPort() + " shouldn't be connected");
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
        for (Socket client : clients) {
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
        clients.remove(client); //FIXME: clients not in the list should be removed from the server
        heartBeats.remove(client);
        //Executors.newSingleThreadExecutor().submit(this::endClientConnection);
        logger.info("User on port " + client.getPort() + " disconnected.");
    }

}
