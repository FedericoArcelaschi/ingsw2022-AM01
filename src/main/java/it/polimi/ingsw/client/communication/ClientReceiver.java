package it.polimi.ingsw.client.communication;

import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.communication.Receiver;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.subclasses.Error;
import it.polimi.ingsw.communication.message.subclasses.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.startUp.Outputs.CLEAR_SCREEN;

/**
 * Receives all the messages from the server and handles them correctly.
 */
public class ClientReceiver extends Receiver {
    private final UserInterface userInterface;
    private final HeartBeatClient heartBeatClient;
    private PrintWriter out;

    public ClientReceiver(Socket socket, UserInterface userInterface) {
        super(socket);
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {userInterface.printError(e.getMessage());}
        this.userInterface = userInterface;
        this.heartBeatClient = new HeartBeatClient(userInterface);
        Executors.newSingleThreadExecutor().submit(heartBeatClient);
    }

    protected synchronized void messageSwitch(Message message) {
        switch (message.getType()) {
            case PING -> {
                heartBeatClient.validate();
                out.println(new Ping().toJson());
            }
            case UPDATE -> {
                Update update = (Update) message;
                userInterface.draw(update.getBoardData());
            }
            case LOBBYINFO -> {
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
    private final int TIMEOUT = 7000;
    private boolean connected = true;

    public HeartBeatClient(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public synchronized void run() {
        while (true) {
            connected = false;
            try {
                wait(TIMEOUT);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            if (!connected) {
                userInterface.disconnected();
                break;
            }
        }
    }

    void validate() {
        connected = true;
    }
}