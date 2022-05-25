package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.*;
import it.polimi.ingsw.communication.packet.message.CommandMessage;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.Preferences;
import it.polimi.ingsw.userInterface.UserInterface;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain {
    private final String username;
    private ClientState state;
    private final int preferenceNPlayer;
    private final boolean preferenceExpertMode;
    private final String IP;
    private final int port;
    public Socket socket = null;
    private ClientSender cs;
    private ClientReceiver cr;

    public ClientMain(String username, int preferenceNPlayer, boolean preferenceExpertMode, String IP, int port) {
        this.username=username;
        this.preferenceNPlayer=preferenceNPlayer;
        this.preferenceExpertMode=preferenceExpertMode;
        this.IP = IP;
        this.port = port;
        this.state = ClientState.NOT_CONNECTED;
    }

    public void connect(UserInterface userInterface){
        System.out.println(username + ":  attempting connection");
        try {
            ExecutorService executor = Executors.newCachedThreadPool();
            socket = new Socket(IP, port);
            System.out.println(username + ":  connected");

            cs = new ClientSender(socket);
            cr = new ClientReceiver(this, socket, userInterface);
            //send player preferences to the server;
            Preferences preferences = new Preferences(username, preferenceNPlayer, preferenceExpertMode);
            Packet packet = new Packet(MessageType.PREFERENCES, preferences);
            cs.sendPacket(packet);
            //run the ClientReceiver
            executor.submit(cr);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + IP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    IP);
            System.exit(1);
        }
        this.state = ClientState.WAITING_ROOM;
    }

    public boolean runCommand(String stringCommand){
        if (socket == null || socket.isClosed()) {
            return false;
        }
        //compose command and send
        CommandMessage commandMessage = new CommandMessage(username, stringCommand);
        Packet packet = new Packet(MessageType.COMMAND, commandMessage);
        cs.sendPacket(packet);
        System.out.println("command sent");
        return true;
    }

    public String getUsername() {
        return username;
    }

    public ClientState getState() {
        return state;
    }

    public void setState(ClientState state){
        this.state = state;
    }
}
