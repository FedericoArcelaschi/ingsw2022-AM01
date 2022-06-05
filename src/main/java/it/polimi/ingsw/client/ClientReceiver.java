package it.polimi.ingsw.client;

import it.polimi.ingsw.client.communication.ClientState;
import it.polimi.ingsw.communication.Receiver;
import it.polimi.ingsw.communication.message.*;
import it.polimi.ingsw.communication.message.subclasses.Error;
import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.message.subclasses.Ping;
import it.polimi.ingsw.communication.message.subclasses.Update;

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

    protected void messageSwitch(Message message) {
        switch (message.getType()) {
            case PING -> {
                //TODO: are we interested in keeping the Ping UUID?
                out.println(new Ping().toJson());
                //TODO: add a timer on a new thread that makes the heart beat two way.
            }
            case UPDATE -> {
                Update update = (Update) message;
                //print data without saving it anywhere
                userInterface.draw(update.getBoardData());
                cm.setState(ClientState.GAME);
            }
            case LOBBYINFO -> {
                LobbyInfo lobbyInfoMessage = (LobbyInfo) message;
                userInterface.printWaitingRoom(lobbyInfoMessage.getPlayers(), lobbyInfoMessage.getGameType());
            }
            case END -> cm.setState(ClientState.GAME_ENDED);

            case ERROR -> System.out.println("new error received: " + ((Error) message).getMessage());
                //print data without saving it anywhere
                //ViewDraw.drawCli(message.data());
        }
    }
}
