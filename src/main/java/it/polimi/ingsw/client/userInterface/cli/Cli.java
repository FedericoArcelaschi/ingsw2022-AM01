package it.polimi.ingsw.client.userInterface.cli;

import it.polimi.ingsw.client.communication.ClientMain;
import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.server.controller.GameType;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.startUp.Outputs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cli implements UserInterface {

    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Cli() {
        ClientMain clientMain = new ClientMain(
                "localhost",
                12345);
        try {
            clientMain.connect(this);
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
            new Cli();
        }
        clientMain.sendPreferences(getValidPreferences());

        // the Cli in the input/output to the terminal
        while(true) {
            try {
                String input = br.readLine().strip();
                if(!input.isBlank())
                    clientMain.runCommand(input);
            } catch (IOException | NoClassDefFoundError e) {
                System.err.println(e.getMessage());
                //throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void draw (BoardData boardData) {
        System.out.println("\r");
        System.out.println(boardData.toString());
    }

    /**
     * Method to handle the LobbyInfoMessages.
     */
    @Override
    public void printLobby(LobbyInfo lobbyInfo) {
        System.out.println("\r");
        System.out.println(lobbyInfo);
    }

    @Override
    public void printError(String error) {
        System.err.println(error);
    }

    /**
     * Before opening the connection with the server the client requires to insert the preferences.
     */
    private Preferences getValidPreferences() {
        int nPlayer;
        Boolean expertMode;
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String query = "Enter username:";
        String username;
        try {
            do{
                System.out.println(query);
                username = br.readLine().strip();
                query = "Enter a valid username:";
            } while (username == null || username.isBlank());

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
            System.out.println(Outputs.CLEAR_SCREEN);
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
