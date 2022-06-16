package it.polimi.ingsw.client.communication;

import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.communication.Receiver;
import it.polimi.ingsw.communication.message.subclasses.*;
import it.polimi.ingsw.communication.message.subclasses.Error;
import it.polimi.ingsw.communication.message.Message;

import java.net.Socket;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.startUp.Outputs.CLEAR_SCREEN;

/**
 * Receives all the messages from the server and handles them correctly.
 */
public class ClientReceiver extends Receiver {
    private UserInterface userInterface;
    private HeartBeatClient heartBeatClient;

    public ClientReceiver(ClientMain cm, Socket socket, UserInterface userInterface) {
        super(cm, socket);
        this.userInterface = userInterface;
        heartBeatClient = new HeartBeatClient(cm);
        Executors.newSingleThreadExecutor().submit(heartBeatClient);
    }

    protected synchronized void messageSwitch(Message message) {
        switch (message.getType()) {
            case PING -> {
                out.println(new Ping().toJson());
                heartBeatClient.validate();
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
                userInterface.printLobby(lobbyInfoMessage);
            }
            case END -> {
                Error error = (Error) message;
                System.err.println(error.getMessage());
                cm.setState(ClientState.GAME_ENDED);
            }

            case ERROR -> {
                Error
                        error = (Error) message;
                userInterface.printError(error.getMessage());
                //TODO: cm.setState(error.getState());
                //TODO: UserInterface.handleError(error.getMessage());
            }
        }
    }
}

class HeartBeatClient implements Runnable {
    private final ClientMain clientMain;
    private boolean connected = true;

    public HeartBeatClient(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    @Override
    public synchronized void run() {
        connected = false;
        try {
            wait(10000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        if (!connected) {
            clientMain.setState(ClientState.NOT_CONNECTED);
            //clientMain.runCommand(new Error("QUIT"));
        }
        run();
    }

    void validate() {
        connected = true;
    }
}