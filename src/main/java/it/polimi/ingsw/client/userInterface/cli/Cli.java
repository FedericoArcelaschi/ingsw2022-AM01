package it.polimi.ingsw.client.userInterface.cli;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.server.controller.GameType;
import it.polimi.ingsw.communication.modelData.BoardData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Cli implements UserInterface {
    String username;
    int nPlayer;
    Boolean expertMode;
    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Cli() {
        ClientMain clientMain = new ClientMain(
                "127.0.0.1",
                12345,
                getValidPreferences());
        try {
            clientMain.connect(this);
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
            new Cli();
        }
        // the Cli in the input/output to the terminal
        while(true) {
            try {
                String input = br.readLine();
                clientMain.runCommand(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void draw (BoardData boardData) { //TODO: il problema è che forse non può fare questo se sta leggendo da input.
        System.out.println(boardData.toString());
    }

    /**
     * Method to handle the LobbyInfoMessages.
     */
    @Override
    public void printWaitingRoom(List<String> connectedUser, GameType gameType) {
        //TODO: print a nicier view of the lobby
        StringBuilder sb = new StringBuilder();
        sb.append("Player in queue: \n");
        sb.append("Game type: ").append(gameType).append("\n");
        for (String user : connectedUser) {
            sb.append("\t").append(user).append("\n");
        }
        System.out.println(sb);
    }
    /**
     * Before opening the connection with the server the client requires to insert the preferences.
     */
    private Preferences getValidPreferences() {
        int nPlayer;
        Boolean expertMode;
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String query
                    = "Enter username:";
            do{
                System.out.println(query);
                username = br.readLine();
                query = "Enter a valid username:";
            } while (username == null);

            query = "Enter the number of players:";
            do {
                System.out.println(query);
                try {
                    nPlayer = Integer.parseInt(br.readLine());
                } catch (NumberFormatException ignored) {nPlayer = 0;}
                query = "Enter a valid number of players: (between 2 and 4)";
            } while (nPlayer < 2 || nPlayer > 4);

            query = "Expert mode? (Y/n)";
            do {
                System.out.println(query);
                expertMode = getBoolean(br.readLine());
            } while (expertMode == null);

        } catch (IOException e) {
            System.err.println("input error:\n" + e.getMessage());
            return getValidPreferences();
        }
        try {
            return new Preferences(username, nPlayer, expertMode);
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
            return getValidPreferences();
        }
    }

    private Boolean getBoolean(String s) {
        if(s.equalsIgnoreCase("y"))
            return true;
        if(s.equalsIgnoreCase("n"))
            return false;
        return null;
    }
}
