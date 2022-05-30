package it.polimi.ingsw.communication;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.communication.packet.message.*;
import it.polimi.ingsw.userInterface.UserInterface;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;

import java.net.Socket;

/**
 * Allow the client to receive packets from the server socket and handle them.
 */
public class ClientReceiver extends Receiver {
    UserInterface userInterface;

    public ClientReceiver(ClientMain cm, Socket socket, UserInterface userInterface) {
        super(cm, socket);
        this.userInterface = userInterface;
    }

    void messageSwitch(MessageType type, Message message) {
        switch (type) {
            case PING -> {
                Ping ping = (Ping) message;
                Packet heartbeatToServer = new Packet(MessageType.PING, ping);
                out.println(parser.toJson(heartbeatToServer));
            }
            case UPDATE -> {
                Update update = (Update) message;
                //print data without saving it anywhere
                userInterface.draw(update.getBoardData());
                cm.setState(ClientState.GAME);
            }
            case LOBBY -> {
                LobbyInfoMessage lobbyInfoMessage = (LobbyInfoMessage) message;
                userInterface.roomOutput(lobbyInfoMessage.getPlayers(), lobbyInfoMessage.getGameType());
            }
            case END -> cm.setState(ClientState.GAME_ENDED);

            case ERROR -> System.out.println("new error received: " + ((ErrorMessage) message).getMessage());
                //print data without saving it anywhere
                //ViewDraw.drawCli(message.data());
        }
    }
}
