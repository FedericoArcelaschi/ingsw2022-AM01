package it.polimi.ingsw.client;

import it.polimi.ingsw.client.communication.ClientState;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.communication.message.subclasses.CommandMessage;
import it.polimi.ingsw.startUp.Outputs;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;

public class ClientMain {
    private final String username;
    private final int preferenceNPlayer;
    private final boolean preferenceExpertMode;
    private final String IP;
    private final int port;

    private Socket socket = null;
    private ClientSender clientSender;
    private ClientReceiver clientReceiver;
    private ClientState state = ClientState.NOT_CONNECTED;

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

        //sends player preferences to the server;
        clientSender.send(new Preferences(username, preferenceNPlayer, preferenceExpertMode));

        //runs the ClientReceiver
        clientReceiver = new ClientReceiver(this, socket, userInterface);
        Runnable runnable = () -> Executors.newCachedThreadPool().submit(clientReceiver);
        runnable.run();

        state = ClientState.WAITING_ROOM;
    }

    public void runCommand(String stringCommand) {
        if(stringCommand.strip().equalsIgnoreCase("help"))  {
            System.out.println(Outputs.HELP.out);
            return;
        }
        if (socket == null || socket.isClosed()) {
            return;
        }
        //compose command and send, only if the player is in a game.
        if (state == ClientState.GAME) {
            CommandMessage commandMessage;
            try {
                commandMessage = new CommandMessage(username, stringCommand);
            }catch (IllegalArgumentException e){
                System.err.println(e.getMessage());
                return;
            }
            clientSender.send(commandMessage);
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
