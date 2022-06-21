package it.polimi.ingsw.client.communication;

import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.communication.Receiver;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.subclasses.Error;
import it.polimi.ingsw.communication.message.subclasses.*;

import java.net.Socket;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.startUp.Outputs.CLEAR_SCREEN;

/**
 * Receives all the messages from the server and handles them correctly.
 */
public class ClientReceiver extends Receiver {
    private final UserInterface userInterface;
    private final HeartBeatClient heartBeatClient;

    public ClientReceiver(Socket socket, UserInterface userInterface) {
        super(socket);
        this.userInterface = userInterface;
        heartBeatClient = new HeartBeatClient(userInterface);
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
                if (update instanceof WinUpdate) {
                    System.out.println(((WinUpdate) update).getWinner());
                }
            }
            case LOBBYINFO -> {
                System.out.println(CLEAR_SCREEN);
                LobbyInfo lobbyInfoMessage = (LobbyInfo) message;
                userInterface.printLobby(lobbyInfoMessage);
            }
            case END -> {
                EndGame endGameMessage = (EndGame) message;
                userInterface.endCurrentGame(endGameMessage);
            }
            case ERROR -> {
                Error error = (Error) message;
                userInterface.printError(error.getMessage());
            }
        }
    }
}

class HeartBeatClient implements Runnable {
    private final UserInterface userInterface;
    private final int TIMEOUT = 5000;
    private boolean connected = true;

    public HeartBeatClient(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public synchronized void run() {
        connected = false;
        try {
            wait(TIMEOUT);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        if (!connected) {
            userInterface.disconnected();
        }
        run();
    }

    void validate() {
        connected = true;
    }
}