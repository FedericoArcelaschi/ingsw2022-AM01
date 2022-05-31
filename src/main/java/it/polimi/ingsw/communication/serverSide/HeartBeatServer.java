package it.polimi.ingsw.communication.serverSide;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.exception.ClientNotRespondingException;
import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.Ping;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Class that implements the heart-beat protocol.
 * it uses a Map to save clients pinged and remove them from it when they ping back.
 * if a client doesn't ping back before timeout it gets removed from his game (the game end) or queue.
 */
public class HeartBeatServer implements Callable {

    private final int timeout = 5000;
    private final Set<Socket> clients;
    private final Set<Socket> heartBeats = new HashSet<>();

    public HeartBeatServer(Set<Socket> clients) {
        this.clients = clients;
    }
    public void validateResponse(Socket clientSocket){
        heartBeats.remove(clientSocket);
    }

    /**
     * Sends a ping to each client and requires that in 5 seconds it responds or it will be removed from the list of players in the ServerMain
     */
    public void run() {
        Gson parser = new Gson();
        while(true) {
            long previous = System.currentTimeMillis();
            for (Socket client: clients) {
                Packet packet = new Packet(MessageType.PING, new Ping());
                String jsonMessage = parser.toJson(packet, Packet.class);
                try {
                    new PrintWriter(client.getOutputStream(), true).println(jsonMessage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                heartBeats.add(client);
            }
            try {
                Thread.sleep(timeout - System.currentTimeMillis() + previous);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (Socket clientSocket : heartBeats)
                clients.remove(clientSocket);
        }
    }

    @Override
    public Object call() {
        run();
        /*try {
            run();
        }
        catch(ClientNotRespondingException e){
            e.printStackTrace();
            System.exit(57); //NETWORK_NOT_RESPONDING
        }*/
        return null;
    }
}
