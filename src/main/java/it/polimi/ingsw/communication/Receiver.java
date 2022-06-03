package it.polimi.ingsw.communication;

import it.polimi.ingsw.client.communication.ClientMain;
import it.polimi.ingsw.communication.packet.PacketParser;
import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * abstract class that is able to receive packets from a socket.
 */
public abstract class Receiver implements Runnable {
    protected final Socket socket;
    protected final BufferedReader in;
    protected final PrintWriter out;
    protected final ClientMain cm;

    protected Receiver(ClientMain cm, Socket socket) {
        this.socket = socket;
        this.cm = cm;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        //keeps reading the input
        String read;
        try {
            read = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var packet = PacketParser.gson.fromJson(read, Packet.class);
        messageSwitch(packet.getMessage());
        run();
    }

    protected abstract void messageSwitch(Message message);

}
