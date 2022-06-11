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

    private String username;
    private final Socket clientsSocket;
    private ServerReceiver serverReceiver;
    private GameInterface gameInterface;

    public Client(Socket clientsSocket) {
        logger.debug("New Client created @port: " +clientsSocket.getPort());
        this.clientsSocket = clientsSocket;
    }

    public void setup(HeartBeatServer heartBeatServer, LobbyManager lobbyManager, ExecutorService executor) {
        this.serverReceiver = new ServerReceiver(this, heartBeatServer, lobbyManager);
        heartBeatServer.addClient(this);
        lobbyManager.addPlayerNoPreferences(this.clientsSocket);
        executor.submit(serverReceiver);
    }

    public void executeCommand(Command command, Socket socket) {
        gameInterface.executeCommand(command, socket);
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

    @Override
    public String toString() {
        return "Client[" +
                "username=" + username + ", " +
                "clientsSocket=" + clientsSocket + ", " +
                "serverReceiver=" + serverReceiver + ']';
    }

    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }

    public Socket clientsSocket() {
        return clientsSocket;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String username() {
        return username;
    }

}
