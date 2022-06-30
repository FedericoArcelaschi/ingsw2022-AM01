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

public class Cli implements UserInterface {

    private final ClientMain clientMain;
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final long READ_TIME = 50;
    private String input = "";
    private String newGame;
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
        if (!clientMain.isConnected()) connect(getNetworkPreferences());
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
                input = br.readLine().strip();
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
                    clientMain.sendPreferences(this.getValidPreferences());
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
                    if (requestNewGame()) {
                        clientMain.setState(ClientState.OUTSIDE_LOBBY);
                        clientMain.sendPreferences(this.getValidPreferences());
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
        System.err.println("connection lost: you left the game.");
        System.err.flush();
        this.connect(DEFAULT_ADDRESS);
    }


    //SUPPORT METHODS:
    private boolean requestNewGame() {
        String query = "Do you want to play a new game? (y/n)";
        Boolean answer;
        do {
            System.out.println(query);
            query = "Please answer with yes or no.";
            try {
                answer = getBoolean(br.readLine().strip());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (answer == null);
        return answer;
    }

    /**
     * Before opening the connection with the server the client requires to insert the preferences.
     */
    private @NotNull Preferences getValidPreferences() {
        int nPlayer;
        Boolean expertMode;
        String query = "Enter username:";
        String username;
        try {
            do {
                System.out.println(query);
                username = br.readLine().strip();
                query = "Enter a valid username:";
            } while (username == null || username.isBlank());

            query = "Enter the number of players:";
            do {
                System.out.println(query);
                try {
                    nPlayer = Integer.parseInt(br.readLine());
                } catch (NumberFormatException ignored) {
                    nPlayer = 0;
                }
                query = "Enter a valid number of players: (between 2 and 4)";
            } while (nPlayer < 2 || nPlayer > 4);

            query = "Expert mode? (Y/n)";
            do {
                System.out.println(query);
                expertMode = getBoolean(br.readLine().strip());
            } while (expertMode == null);

        } catch (IOException e) {
            System.err.println("input error:\n\t" + e.getMessage());
            return getValidPreferences();
        }
        try {
            System.out.println(Outputs.CLEAR_SCREEN);
            return new Preferences(username, nPlayer, expertMode);
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
            return getValidPreferences();
        }
    }

    private @Nullable Boolean getBoolean(String s) {
        if (s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes"))
            return true;
        if (s.equalsIgnoreCase("n") || s.equalsIgnoreCase("no"))
            return false;
        return null;
    }

    private @NotNull SocketAddress getNetworkPreferences() {
        String hostName;
        int port = 0;
        InetAddress address = null;

        synchronized (System.out) {
            while (address == null) {
                try {
                    System.out.println("\ninsert the host IP:");
                    hostName = br.readLine().strip();
                    address = Inet4Address.getAllByName(hostName)[0];
                } catch (IOException e) {
                    System.out.println("\u001b[31m" + e.getMessage() + "\u001b[0m");
                }
            }
            while (port == 0) {
                try {
                    System.out.println("\ninsert the port:");
                    port = Integer.parseInt(br.readLine().strip());
                } catch (NumberFormatException e) {
                    System.out.println("\u001b[31mNot a valid number.\u001b[0m");
                    System.err.flush();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    System.err.flush();
                }
            }
        }
        try {
            return new InetSocketAddress(address, port);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.err.flush();
            return getNetworkPreferences();
        }
        if(requestNewGame(reader))
            connect(getNetworkPreferences(reader));
        else
            System.exit(0);
    }
}
