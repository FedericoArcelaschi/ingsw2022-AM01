package it.polimi.ingsw.communication;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.exception.ClientNotRespondingException;
import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.MessageType;
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

public class HeartBeatServer implements Callable {
    private final List<Socket> sockets;
    private final Map<Socket, Message> heartBeats;

    public HeartBeatServer(){
        sockets = new ArrayList<>();
        heartBeats = new HashMap<>();
    }

    public void addClient(Socket newClient){
        sockets.add(newClient);
    }

    public boolean removeClient(Socket client){
        return sockets.remove(client);
    }

    public boolean validateResponse(Message response){
        for(Socket key : heartBeats.keySet()){
            if (heartBeats.get(key).equals(response)){
                heartBeats.remove(key);
                return true;
            }
        }
        return false;
    }

    public void run() throws ClientNotRespondingException {
        Gson parser = new Gson();
        while(heartBeats.isEmpty()){
            for (Socket client: sockets) {
                PrintWriter out;
                try {
                    out = new PrintWriter(client.getOutputStream(), true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Message message = new Ping();
                Packet packet = new Packet(MessageType.PING, message);
                heartBeats.put(client, message);
                String jsonMessage = parser.toJson(packet, Packet.class);
                out.println(jsonMessage);
            }
            try {
                int sleep;
                if(sockets.isEmpty()) sleep = 100;
                else sleep = 2000;
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //String plural = heartBeats.size() == 1? "" : "s";
        //StringBuilder errorMessage = new StringBuilder("Client"+ plural +" disconnected: ");
        for (Socket s: heartBeats.keySet()) {
            removeClient(s);
            //TODO: waitingRooms.removePlayer(s);
            //TODO: game.end(s);
            //errorMessage.append(s).append(", ");
        }
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
