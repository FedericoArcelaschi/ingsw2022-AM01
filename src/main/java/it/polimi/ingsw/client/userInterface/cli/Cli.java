package it.polimi.ingsw.client.userInterface.cli;

import it.polimi.ingsw.client.communication.ClientMain;
import it.polimi.ingsw.client.communication.ClientState;
import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.communication.message.subclasses.EndGame;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.startUp.Outputs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.*;

import static it.polimi.ingsw.startUp.Outputs.CLEAR_SCREEN;

public class Cli implements UserInterface, DefaultConversations {

    private final ClientMain clientMain;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final long READ_TIME = 50;
    private String input = "";
    private boolean firstUpdate = true;
    private final SocketAddress DEFAULT_ADDRESS = new InetSocketAddress("localhost", 12345);

    public Cli() {
        clientMain = new ClientMain(this);
        connect(DEFAULT_ADDRESS);
    }

    /**
     * Recursive method that connects the client.
     * Keeps asking for the Network Preferences until the client connects to the server.
     *
     * @param address may be null if there is a problem in the input parsing/reading
     */
    private void connect(@Nullable SocketAddress address) {
        if (address != null)
            clientMain.connect(address);
        if (!clientMain.isConnected()) connect(getNetworkPreferences(reader));
        System.out.println("connected!");
    }

    /**
     * @implNote Decided to wait to "start" the command parsing because
     * of problems with concurrent preferences & command parsing.
     */
    @Override
    public void draw(BoardData update) {
        System.out.println(CLEAR_SCREEN);
        clientMain.setState(ClientState.IN_GAME);
        clientMain.setBoardData(update);
        System.out.println(update.toString());
        if (firstUpdate)
            executor.submit(this::readBuffer);
        firstUpdate = false;
    }

    /**
     * Input managing and Command generation.
     * Is a recursive function: will end only in case of a system shutdown.
     */
    public void readBuffer() {
        while (clientMain.getState() == ClientState.IN_GAME) {
            input = "";
            try {
                input = reader.readLine().strip();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!input.isBlank())
                if (!clientMain.runCommand(input)) {
                    break;
                }
        }
    }

    @Override
    public void printLobby(LobbyInfo lobbyInfo) {
        executor.submit(() -> {
            System.out.println(CLEAR_SCREEN);
            System.out.println(lobbyInfo);
            if (clientMain.getState() == ClientState.OUTSIDE_LOBBY) {
                synchronized (System.out) {
                    clientMain.sendPreferences(this.getValidPreferences(reader));
                }
                clientMain.setState(ClientState.INSIDE_LOBBY);
            }
        });
    }

    @Override
    public void printError(String error) {
        System.err.println(error);
    }

    /**
     * Ends the game and prompts the client if he wants to play another one.
     * If he wants to play another game he will be redirected to the Lobby
     *
     * @param endGameMessage contains relevant information about the end of the game. A winner if present.
     */
    @Override
    public void endCurrentGame(EndGame endGameMessage) {
        clientMain.setState(ClientState.GAME_ENDED);
        System.out.println(endGameMessage.getCause());
        executor.submit(
                () -> {
                    if (requestNewGame(reader)) {
                        clientMain.setState(ClientState.OUTSIDE_LOBBY);
                        clientMain.sendPreferences(this.getValidPreferences(reader));
                        clientMain.setState(ClientState.INSIDE_LOBBY);
                    } else {
                        System.out.println("Goodbye!");
                        System.exit(0);
                    }
                }
        );
    }

    /**
     * Re-connects the disconnected client to the server.
     * The newly connected client will be in the lobby.
     */
    @Override
    public void disconnected() {
        clientMain.setState(ClientState.NOT_CONNECTED);
        System.err.println("connection lost: you left the game.\n");
        System.err.flush();
        if(requestNewGame(reader))
            connect(getNetworkPreferences(reader));
        else
            System.exit(0);
    }
}
