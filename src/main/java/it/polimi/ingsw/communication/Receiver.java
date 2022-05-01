package it.polimi.ingsw.communication;

import com.google.gson.Gson;
import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.client.ViewDraw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Receiver implements Runnable{
    protected final Socket socket;
    protected final BufferedReader in;
    protected final PrintWriter out;
    protected final ClientMain cm;
    protected Gson parser;

    public Receiver(ClientMain cm, Socket socket){
        this.socket = socket;
        this.cm = cm;
        this.parser = new Gson();
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while(!socket.isClosed()){
            Message message;
            try {
                message = parser.fromJson(in.readLine(), Message.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            messageSwitch(message);
        }
    }

    void messageSwitch(Message message){}
}
