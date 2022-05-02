package it.polimi.ingsw.communication;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.communication.packet.Message;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.Ping;

import java.net.Socket;

public class ClientReceiver extends Receiver{

    public ClientReceiver(ClientMain cm, Socket socket) {
        super(cm, socket);
    }

    void messageSwitch(MessageType type, Message message){
        switch (type){
            case PING -> {
                Ping ping = (Ping) message;
                Packet heartbeatToServer = new Packet(MessageType.PING, ping);
                System.out.println(cm.getUsername()+": ping received");
                out.println(parser.toJson(heartbeatToServer));
            }
            case UPDATE -> {
                //print data without saving it anywhere
                //ViewDraw.drawCli(message.data());
            }
            case END -> {}
            case ERROR -> {}
        }
    }
}
