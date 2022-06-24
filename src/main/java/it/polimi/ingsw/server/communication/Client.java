package it.polimi.ingsw.server.communication;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.server.controller.GameInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public final class Client {

    private final static Logger logger = LogManager.getLogger(Client.class);
    private final Socket clientsSocket;
    private final LobbyManager lobbyManager;
    private GameInterface gameInterface;
    private String username;

    public Client(Socket clientsSocket, HeartBeatServer heartBeatServer, LobbyManager lobbyManager, ExecutorService executor) {
        heartBeatServer.addClient(this);
        this.clientsSocket = clientsSocket;
        this.lobbyManager = lobbyManager;
        executor.submit(new ServerReceiver(this, heartBeatServer, lobbyManager));
        logger.debug("New Client created @port: " +clientsSocket.getPort());
    }

    public void setup() {

    }

    public void send(Message message) {
        logger.info("client " + username + " sending data. (" + message.getType() + ").");
        OutputStream outputStream;
        try {
            outputStream = clientsSocket.getOutputStream();
        } catch (IOException e) {
            logger.info("client " + this + " failed opening the output stream", e);
            return;
        }
        PrintWriter out = new PrintWriter(outputStream, true);
        out.println(message.toJson());
    }

    public void executeCommand(Command command, Client client) {
        gameInterface.executeCommand(command, client);
    }


    public void endConnection() {
        if (gameInterface == null) {
            this.lobbyManager.remove(this);
        } else {
            gameInterface.endGame(this);
        }
    }

    /**
     * Transfer a player from the game to outside the lobby.
     */
    public void putInLobby() {
        gameInterface = null;
        lobbyManager.addPlayerNoPreferences(this);
    }

    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket clientsSocket() {
        return clientsSocket;
    }

    public String username() {
        return username;
    }
}
