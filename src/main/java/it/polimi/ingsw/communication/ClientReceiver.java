package it.polimi.ingsw.communication;

import com.google.gson.Gson;
import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.client.ViewDraw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;

public class ClientReceiver implements Runnable{
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final ClientMain cm;

    public ClientReceiver(ClientMain cm, Socket socket){
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
        Gson parser = new Gson();
        while(!socket.isClosed()){
            Response messageFromServer;
            try {
                  messageFromServer = parser.fromJson(in.readLine(), Response.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            switch (messageFromServer.type()){
                case PING -> {
                    Response heartbeatToServer = new Response(ResponseType.PING);
                    out.println(parser.toJson(heartbeatToServer));
                }
                case UPDATE -> {
                    //print data without saving it anywhere
                    ViewDraw.drawCli(messageFromServer.data());
                }
                case END -> {}
                case ERROR -> {}
            }
        }
    }
}
