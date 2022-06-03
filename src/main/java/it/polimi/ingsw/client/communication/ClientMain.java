package it.polimi.ingsw.client.communication;

import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.Preferences;
import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.communication.packet.message.command.CommandMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;

public class ClientMain {
    private final String username;
    private ClientState state = ClientState.NOT_CONNECTED;
    private final int preferenceNPlayer;
    private final boolean preferenceExpertMode;
    private final String IP;
    private final int port;

    public Socket socket = null;
    private ClientSender clientSender;
    private ClientReceiver clientReceiver;

    public ClientMain(String IP, int port, Preferences preferences) {
        this.IP = IP;
        this.port = port;
        this.username = preferences.username();
        this.preferenceNPlayer = preferences.nPlayer();
        this.preferenceExpertMode = preferences.expertMode();
    }

    public void connect(UserInterface userInterface) throws IllegalAccessException {
        System.out.println(username + ":  attempting connection");
        try {
            this.socket = new Socket(IP, port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            //TODO: implement better exception handling
        }
        System.out.println(username + ":  connected");

        clientSender = new ClientSender(socket);
        System.out.println("socket client: " + socket.getChannel()); // => null
        System.out.println(socket.getPort());
        System.out.println(socket.getRemoteSocketAddress());

        //sends player preferences to the server;
        Preferences preferences = null;
        preferences = new Preferences(username, preferenceNPlayer, preferenceExpertMode);
        Packet packet = new Packet(preferences);
        clientSender.sendPacket(packet);

        //runs the ClientReceiver
        clientReceiver = new ClientReceiver(this, socket, userInterface);
        Runnable runnable = () -> Executors.newCachedThreadPool().submit(clientReceiver);
        runnable.run();

        state = ClientState.WAITING_ROOM;
    }

    public void runCommand(String stringCommand){
        if (socket == null || socket.isClosed()) {
            return;
        }
        //compose command and send, only if the player is in a game.
        if (state == ClientState.GAME) {
            var commandMessage = new CommandMessage(username, stringCommand);
            var packet = new Packet(commandMessage);
            clientSender.sendPacket(packet);
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
