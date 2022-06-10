package it.polimi.ingsw.communication;

import com.google.gson.Gson;
import it.polimi.ingsw.client.communication.ClientMain;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.server.controller.GameInterface;
import it.polimi.ingsw.server.controller.GameManager;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Receives messages from a socket.
 */
public abstract class Receiver implements Runnable{
    protected final Socket socket;
    protected final BufferedReader in;
    protected final PrintWriter out;
    protected final ClientMain cm;

    public Receiver(ClientMain cm, Socket socket) { //FIXME è brutto che sia a null.
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
        while (true)
            readInput();
    }

    private void readInput() {
        String read;
        try {
            read = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        messageSwitch(new Gson().fromJson(read, Message.class));
    }

    protected abstract void messageSwitch(Message message);

}
