package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.packet.Packet;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSender {

    private final PrintWriter out;

    public ClientSender(@NotNull Socket socket) {
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPacket(Packet packet){
        out.println(packet.toJson());
        System.out.println(packet.toJson());
    }
}
