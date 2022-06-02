package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.exception.ClientNotRespondingException;
import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.Ping;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Class that implements the heart-beat protocol.
 * it uses a Map to save clients pinged and remove them from it when they ping back.
 * if a client doesn't ping back before timeout it gets removed from his game (the game end) or queue.
 */
public class HeartBeatServer implements Callable {

    private final int timeout = 5000;
    private final List<Socket> clients;
    private final Map<Socket, Message> heartBeats;

    public HeartBeatServer(){
        clients = new ArrayList<>();
        heartBeats = new HashMap<>();
    }

    public void addClient(Socket newClient){
        clients.add(newClient);
    }

    public void removeClient(Socket client){
        clients.remove(client);
    }

    public void validateResponse(Message response){
        for(Socket key : heartBeats.keySet()){
            if (heartBeats.get(key).equals(response)){
                heartBeats.remove(key);
                return;
            }
        }
    }

    public void run() throws ClientNotRespondingException {
        Gson parser = new Gson();
            for (Socket client : clients) {
                PrintWriter out;
                try {
                    out = new PrintWriter(client.getOutputStream(), true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Message message = new Ping();
                Packet packet = new Packet(message);
                heartBeats.put(client, message);
                String jsonMessage = parser.toJson(packet, Packet.class);
                out.println(jsonMessage);
            }
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(!heartBeats.isEmpty())
                System.out.println("client didn't ping back in time");
            run(); //personalmente mi sembra più elegante
        //TODO: removeClient(s);
        //TODO: waitingRooms.removePlayer(s);
        //TODO: game.end(s);
        //errorMessage.append(s).append(", ");
        //throw new ClientNotRespondingException(errorMessage.toString());
    }

    @Override
    public Object call() throws ClientNotRespondingException {
        try {
            run();
        }
        catch(ClientNotRespondingException e){
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
}
