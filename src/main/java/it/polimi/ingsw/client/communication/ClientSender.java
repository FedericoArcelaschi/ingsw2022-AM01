package it.polimi.ingsw.client.communication;

import it.polimi.ingsw.communication.message.Message;
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

    public void send(Message message){
        out.println(message.toJson());
    }
}
