package it.polimi.ingsw.communication;

import com.google.gson.Gson;
import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.client.ViewDraw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientReceiver extends Receiver{

    public ClientReceiver(ClientMain cm, Socket socket) {
        super(cm, socket);
    }

    void messageSwitch(Message message){
        switch (message.type()){
            case PING -> {
                Message heartbeatToServer = new Message(message.id(), MessageType.PING);
                System.out.println(cm.getUsername()+": ping received");
                out.println(parser.toJson(heartbeatToServer));
                System.out.println(cm.getUsername()+": pinged back");
            }
            case UPDATE -> {
                //print data without saving it anywhere
                ViewDraw.drawCli(message.data());
            }
            case END -> {}
            case ERROR -> {}
        }
    }
}
