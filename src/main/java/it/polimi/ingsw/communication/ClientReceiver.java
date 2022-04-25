package it.polimi.ingsw.communication;

import com.google.gson.Gson;
import it.polimi.ingsw.client.ClientMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceiver implements Runnable{
    private final Socket socket;
    private final BufferedReader in;
    private final ClientMain cm;

    public ClientReceiver(ClientMain cm, Socket socket){
        this.socket = socket;
        this.cm = cm;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        Gson parser = new Gson();
        while(!socket.isClosed()){
            Response r;
            try {
                  r = parser.fromJson(in.readLine(), Response.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(r.message());
            cm.setBoard(r.board());
        }
    }
}
