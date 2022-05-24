package it.polimi.ingsw.communication;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Drawable;
import it.polimi.ingsw.communication.packet.message.ErrorMessage;
import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.Ping;
import it.polimi.ingsw.communication.packet.message.Update;

import java.net.Socket;

/**
 * Allow the client to receive packets from the server socket and handle them.
 */
public class ClientReceiver extends Receiver{
    Drawable drawable;

    public ClientReceiver(ClientMain cm, Socket socket, Drawable drawable) {
        super(cm, socket);
        this.drawable = drawable;
    }

    void messageSwitch(MessageType type, Message message){
        switch (type){
            case PING -> {
                Ping ping = (Ping) message;
                System.out.println("ping received");
                Packet heartbeatToServer = new Packet(MessageType.PING, ping);
                out.println(parser.toJson(heartbeatToServer));
                System.out.println(parser.toJson(heartbeatToServer));
                System.out.println("ping sent back: ");
            }
            case UPDATE -> {
                System.out.println("board received");
                Update update = (Update) message;
                //print data without saving it anywhere
                drawable.draw(update.getBoardData());
                cm.setState(ClientState.GAME);
            }
            case END -> {
                cm.setState(ClientState.GAME_ENDED);
            }
            case ERROR -> {
                ErrorMessage errorMessage = (ErrorMessage) message;
                System.out.println("new error received: " + errorMessage.getMessage());
                //print data without saving it anywhere
                //ViewDraw.drawCli(message.data());
            }
        }
    }
}
