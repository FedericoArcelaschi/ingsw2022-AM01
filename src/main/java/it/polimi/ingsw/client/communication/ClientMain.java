package it.polimi.ingsw.client.communication;

import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.subclasses.CommandMessage;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.expertMode.CharacterData;
import it.polimi.ingsw.startUp.Outputs;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.IllegalBlockingModeException;
import java.text.ParseException;
import java.util.concurrent.Executors;

public class ClientMain {

    private final UserInterface userInterface;
    private Socket socket;
    private String username;
    private ClientSender clientSender;
    private ClientState state = ClientState.NOT_CONNECTED;
    private BoardData boardData;

    public ClientMain(@NotNull UserInterface UI) {
        this.userInterface = UI;
    }

    /**
     * Tries to connect the client.
     * Depending on the socket address and port and from the server availability may not connect the client
     * @param socketAddress a InetAddress: address - port
     */
    public boolean connect(@NotNull SocketAddress socketAddress) {
        System.out.println("User on " + socketAddress + ":  attempting connection");
        try {
            this.socket = new Socket();
            this.socket.connect(socketAddress);
        } catch (IOException | IllegalArgumentException | IllegalBlockingModeException e) {
            System.out.println("\u001b[31m" + e.getMessage() + ": failed connection\u001b[0m");
            return false;
        }
        System.out.println("connected!");
        clientSender = new ClientSender(socket);
        Executors.newSingleThreadExecutor().submit(new ClientReceiver(socket, userInterface));
        state = ClientState.OUTSIDE_LOBBY;
        return true;
    }

    /**
     * From Lobby information pulls the username and then sends them to the server
     */
    public void sendPreferences(Preferences preferences) {
        this.username = preferences.username();
        clientSender.send(preferences);
    }

    /**
     * Only for Input - Game commands
     * @param stringCommand the unformatted user input
     */
    public void runCommand(String stringCommand) {
        if (socket.isClosed())
            userInterface.disconnected();
        switch (state) {
            case NOT_CONNECTED -> {
            }
            case OUTSIDE_LOBBY -> {

            }
            case INSIDE_LOBBY -> {

            }
            case GAME -> {
                if (stringCommand.strip().equalsIgnoreCase("help"))
                    System.out.println(Outputs.HELP);
                else if(stringCommand.strip().equalsIgnoreCase("charinfo"))
                    System.out.println(boardData.characters().stream().map(CharacterData::getDescription)); //fixme
                else {
                    try {
                        CommandMessage commandMessage = new CommandMessage(username, stringCommand);
                        clientSender.send(commandMessage);
                    } catch (ParseException e) {
                        userInterface.printError(e.getMessage());
                    }
                }
            }
            case GAME_ENDED -> {

            }
        }
    }

    public void runCommand(Command command) {
        clientSender.send(new CommandMessage(command));
    }

    public void setState(ClientState state) {
        this.state = state;
    }

    public ClientState getState() {
        return state;
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public void setBoardData(BoardData boardData) {
        this.boardData = boardData;
    }


}
