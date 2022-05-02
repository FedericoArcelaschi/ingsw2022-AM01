package it.polimi.ingsw.communication;

import it.polimi.ingsw.communication.packet.Message;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Ping;

import java.net.Socket;

public class ServerReceiver extends Receiver{

    HeartBeatServer hbs;

    public ServerReceiver(Socket socket, HeartBeatServer hbs) {
        super(null, socket);
        this.hbs = hbs;
    }

    void messageSwitch(MessageType type, Message message){
        switch (type){
            case PING -> {
                Ping ping = (Ping) message;
                System.out.println("Server: ping received");
                hbs.validateResponse(ping);
            }
            case UPDATE -> {
                /*
                TODO: The client is asking to run a command, so we need to check and execute it if is possible.
                If the move is illegal we send an ERROR message.
                Else we broadcast the updated BoardData to all clients.
                */
            }
            case END -> {
                //TODO: the client received the end message from the server and sent back an acknowledgment.
            }
            case ERROR -> {
                //TODO: the client received the error message from the server and sent back an acknowledgment.
            }
        }
    }
}
