package it.polimi.ingsw.server.communication;

import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.server.controller.GameInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public final class Client {

    private String username;
    private final Socket clientsSocket;
    private ServerReceiver serverReceiver;
    private GameInterface gameInterface;

    public Client(Socket clientsSocket) {
        this.clientsSocket = clientsSocket;
    }

    public void setup(HeartBeatServer heartBeatServer, LobbyManager lobbyManager, ExecutorService executor) {
        ServerReceiver serverReceiver = new ServerReceiver(this, heartBeatServer, lobbyManager);
        heartBeatServer.addClient(this);
        lobbyManager.addPlayerNoPreferences(this.clientsSocket);
        executor.submit(serverReceiver);
    }

    public void executeCommand(Command command, Socket socket) {
        gameInterface.executeCommand(command, socket); //FIXME
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

    public String getUsername() {
        return username;
    }

    public void send(Message message) {
        System.out.println("client sending data. " + message);
        PrintWriter out;
        try {
            out = new PrintWriter(clientsSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        out.println(message.toJson());
    }

    public static void send(Message message, Socket socket) {
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        out.println(message.toJson());
    }

    @Override
    public String toString() {
        return "Client[" +
                "username=" + username + ", " +
                "clientsSocket=" + clientsSocket + ", " +
                "serverReceiver=" + serverReceiver + ']';
    }

}
