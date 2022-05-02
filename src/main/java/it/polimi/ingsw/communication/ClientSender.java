package it.polimi.ingsw.communication;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.Preferences;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSender {
    private final Socket socket;
    private final PrintWriter out;
    Gson parser = new Gson();

    public ClientSender(Socket socket) {
        this.socket = socket;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCommand(Command command){
        out.println(parser.toJson(command, Command.class));
    }

    public void sendPreferences(Preferences preferences){
        Packet packet = new Packet(MessageType.PREFERENCES, preferences);
        out.println(parser.toJson(packet));
    }
}
