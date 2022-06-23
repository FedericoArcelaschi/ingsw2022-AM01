package it.polimi.ingsw.server.communication;

import it.polimi.ingsw.communication.message.subclasses.Ping;
import org.apache.logging.log4j.*;

import java.util.*;

/**
 * Class that implements the heart-beat protocol.
 * if a client doesn't ping back before TIMEOUT //TODO it gets removed from his game (the game end) or queue.
 */
public class HeartBeatServer implements Runnable {

    private final static Logger logger = LogManager.getLogger(HeartBeatServer.class);

    private final int TIMEOUT = 5000; // [ms]
    private final Set<Client> clients = new HashSet<>();
    private final Set<Client> heartBeats = new HashSet<>();

    /**
     * Adds a client to the connected clients list and starts the heartbeat on him as well
     */
    public void addClient(Client newClient) {
        clients.add(newClient);
        logger.info("Added client");
    }

    /**Validate the connection with the client for another 5 seconds.
     * @param client the key in the Heartbeat server
     */
    public synchronized void validateResponse(Client client) {
        if (heartBeats.contains(client)) {
            heartBeats.remove(client);
            logger.info("Ping received from port: " + client.username() + ". Pinging back...");
        }
        else
            logger.info("client in port: " + client.username() + " shouldn't be connected");
    }

    /**
     * Heart-beat server main thread keeps executing the following methods to keep track of the connected players.
     */
    public void run() {
        long previousTime = new Date().getTime();
        sendsPingToAll();
        waits(previousTime);
        synchronized (heartBeats) {
            heartBeats.forEach(this::removeClient);
        }
        run();
    }

    private void sendsPingToAll() {
        clients.forEach(client -> {
            client.send(new Ping());
            heartBeats.add(client);
        });
    }

    private void waits(long previousTime) {
        try {
            Thread.sleep(TIMEOUT - new Date().getTime() + previousTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeClient(Client client) {
        client.endGame();
        clients.remove(client);
        heartBeats.remove(client);
        logger.info("User on port " + client.username() + " disconnected.");
    }

}
