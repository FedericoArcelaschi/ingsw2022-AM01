package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.clientSide.ClientReceiver;
import it.polimi.ingsw.communication.clientSide.ClientSender;
import it.polimi.ingsw.communication.packet.message.CommandMessage;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.Preferences;
import it.polimi.ingsw.startUp.Outputs;
import it.polimi.ingsw.userInterface.UserInterface;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;

public class ClientMain {
    private final String username;
    private final int preferenceNPlayer;
    private final boolean preferenceExpertMode;
    private final String IP;
    private final int port;
    private ClientState state = ClientState.NOT_CONNECTED;
    public Socket socket = null;
    private ClientSender cs;
    private ClientReceiver cr;

    public ClientMain(String username, int preferenceNPlayer, boolean preferenceExpertMode, String IP, int port) {
        this.username = username;
        this.preferenceNPlayer = preferenceNPlayer;
        this.preferenceExpertMode = preferenceExpertMode;
        this.IP = IP;
        this.port = port;

    }

    /**
     * On client startUp. Opens the Socket for communication
     * Connects the communication layer with the view:
     * the userInterface can either be a textual or graphical.
     * Sends the preferences to the server.
     * puts the client receiver on a new thread
     */
    public void connect(UserInterface userInterface) {
        System.out.println(this.username + " : attempting connection");
        try {
            this.socket = new Socket(IP, port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1022); //ERROR_NO_NETWORK
        }

        //run the ClientReceiver on another thread
        this.cr = new ClientReceiver(this, this.socket, userInterface);
        Executors.newCachedThreadPool().submit(cr);

        //send player preferences to the server;
        this.cs = new ClientSender(this.socket);
        Preferences preferences = new Preferences(username, preferenceNPlayer, preferenceExpertMode);
        Packet packet = new Packet(preferences, MessageType.PREFERENCES);
        this.cs.sendPacket(packet);
        System.out.println(this.username + " :  connected");
    }

    public void runCommand(String stringCommand) {
        if(stringCommand.equalsIgnoreCase("help"))
            System.out.println(Outputs.HELP.out);
        if (socket == null || socket.isClosed()) {
            return;
        }
        /**
         * The client only composes a message and sends it to the server if is connected.
         */
        if (state == ClientState.GAME) {
            CommandMessage commandMessage = new CommandMessage(username, stringCommand);
            Packet packet = new Packet(commandMessage, MessageType.COMMAND);
            cs.sendPacket(packet);
            System.out.println("command sent");
        }
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
