package it.polimi.ingsw.client.communication;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.communication.Receiver;
import it.polimi.ingsw.communication.message.*;
import it.polimi.ingsw.communication.message.subclasses.*;
import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.communication.message.subclasses.Error;

import java.net.Socket;

import static it.polimi.ingsw.startUp.Outputs.CLEAR_SCREEN;

/**
 * Receives all the messages from the server and handles them correctly.
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
                out.println(new Ping().toJson());
                //TODO: are we interested in keeping the Ping UUID?
                //TODO: add a timer on a new thread that makes the heart beat two way.
            }
            case UPDATE -> {
                System.out.println(CLEAR_SCREEN);
                Update update = (Update) message;
                userInterface.draw(update.getBoardData());
                cm.setState(ClientState.GAME);
            }
            case LOBBYINFO -> {
                System.out.println(CLEAR_SCREEN);
                LobbyInfo lobbyInfoMessage = (LobbyInfo) message;
                System.out.println("\r");
                userInterface.printWaitingRoom(lobbyInfoMessage.getPlayers(), lobbyInfoMessage.getGameType());
            }
            case END -> cm.setState(ClientState.GAME_ENDED);

            case ERROR -> {
                Error error = (Error) message;
                System.err.println("new error received: " + error.getMessage());
                //IDEA: cm.setState(error.getState());
                //IDEA: UserInterface.handleError(error.getMessage());
            }
            case CHARINFO -> {
                CharInfo charInfo = (CharInfo) message;
                System.out.println(charInfo.getInfo());
            }
        }
    }
}
