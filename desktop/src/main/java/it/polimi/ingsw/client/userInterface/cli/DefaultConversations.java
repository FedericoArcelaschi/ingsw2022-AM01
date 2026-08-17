package it.polimi.ingsw.client.userInterface.cli;

import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.startUp.Outputs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public interface DefaultConversations {

    //SUPPORT METHODS:
    default boolean requestNewGame(BufferedReader reader) {
        String query = "Do you want to play a new game? (y/n)";
        Boolean answer;
        do {
            System.out.println(query);
            try {
                answer = getBoolean(reader.readLine().strip());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            query = "Please answer with yes or no.";
        } while (answer == null);
        return answer;
    }

    /**
     * Before opening the connection with the server the client requires to insert the preferences.
     */
    default @NotNull Preferences getValidPreferences(BufferedReader reader) {
        int nPlayer;
        Boolean expertMode;
        String query = "Enter username:";
        String username;
        try {
            do {
                System.out.println(query);
                username = reader.readLine().strip();
                query = "Enter a valid username:";
            } while (username == null || username.isBlank());

            query = "Enter the number of players:";
            do {
                System.out.println(query);
                try {
                    nPlayer = Integer.parseInt(reader.readLine());
                } catch (NumberFormatException ignored) {
                    nPlayer = 0;
                }
                query = "Enter a valid number of players: (between 2 and 4)";
            } while (nPlayer < 2 || nPlayer > 4);

            query = "Expert mode? (Y/n)";
            do {
                System.out.println(query);
                expertMode = getBoolean(reader.readLine().strip());
            } while (expertMode == null);

        } catch (IOException e) {
            System.err.println("input error:\n\t" + e.getMessage());
            return getValidPreferences(reader);
        }
        try {
            System.out.println(Outputs.CLEAR_SCREEN);
            return new Preferences(username, nPlayer, expertMode);
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
            return getValidPreferences(reader);
        }
    }

    @NotNull
    default SocketAddress getNetworkPreferences(BufferedReader reader) {
        String hostName;
        int port = 0;
        InetAddress address = null;

        synchronized (System.out) {
            while (address == null) {
                try {
                    System.out.println("\ninsert the host IP:");
                    hostName = reader.readLine().strip();
                    address = Inet4Address.getAllByName(hostName)[0];
                } catch (IOException e) {
                    System.out.println("\u001b[31m" + e.getMessage() + "\u001b[0m");
                }
            }
            while (port == 0) {
                try {
                    System.out.println("\ninsert the port:");
                    port = Integer.parseInt(reader.readLine().strip());
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
            return getNetworkPreferences(reader);
        }
    }

    default @Nullable Boolean getBoolean(String s) {
        if (s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes"))
            return true;
        if (s.equalsIgnoreCase("n") || s.equalsIgnoreCase("no"))
            return false;
        return null;
    }
}
