package it.polimi.ingsw.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * abstract class that is able to receive packets from a socket.
 */
public abstract class Receiver implements Runnable{
    protected final Socket socket;
    protected final BufferedReader in;
    protected final PrintWriter out;
    protected final ClientMain cm;
    protected Gson parser;

    public Receiver(ClientMain cm, Socket socket) {
        this.socket = socket;
        this.cm = cm;
        this.parser = new GsonBuilder().create();
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while(true){
            Packet packet;
            Message message;
            try {
                String read = in.readLine();
                packet = parser.fromJson(read, Packet.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //System.out.println(packet.getMessageJson());
            message = parser.fromJson(packet.getMessageJson(), packet.getTypeClass());
            messageSwitch(packet.getType(), message);
        }
    }

    void messageSwitch(MessageType type, Message message){}
}
