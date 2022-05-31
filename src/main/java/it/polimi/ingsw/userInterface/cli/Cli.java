package it.polimi.ingsw.userInterface.cli;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.controller.GameType;
import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.userInterface.UserInterface;
import it.polimi.ingsw.communication.modelData.BoardData;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Cli extends Application implements UserInterface {
    String username;
    int nPlayer;
    Boolean expertMode;
    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Cli() {
        getValidInput();
        ClientMain clientMain = new ClientMain(username,
                nPlayer,
                expertMode,
                "127.0.0.1",
                1234);
        Executors.newCachedThreadPool().submit(
                () -> clientMain.connect(this));
        //
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
    public void start(Stage stage) throws IllegalAccessException {
       throw new IllegalAccessException("Method for GUI");
    }

    @Override
    public void draw(BoardData boardData) {
        System.out.println(boardData.toString());
    }

    /**
     * Method to handle the LobbyInfoMessages.
     */
    @Override
    public void printWaitingRoom(List<String> connectedUser, GameType gameType) {
        StringBuilder sb = new StringBuilder();
        sb.append("Player in queue: \n");
        sb.append("Game type: ").append(gameType).append("\n");
        for (String user : connectedUser) {
            sb.append("\t").append(user).append("\n");
        }
        System.out.println(sb);
    }

    /**
     * Before opening the connection with the server the "local" Cli requires to insert the preferences.
     */
    void getValidInput() {
        String query;
        try {
            query = "Enter username:";
            do{
                System.out.println(query);
                username = br.readLine();
                query = "Enter a valid username:";
            } while (username == null);

            query = "Enter the number of players:";
            do {
                System.out.println(query);
                try{
                    nPlayer = Integer.parseInt(br.readLine());
                } catch (NumberFormatException ignored){}
                query = "Enter a valid number of players: (between 2 and 4)";
            } while(nPlayer < 2 || nPlayer > 4);

            query = "Expert mode? (Y/n)";
            do {
                System.out.println(query);
                expertMode = getBoolean(br.readLine());
            } while (expertMode == null);

        } catch (IOException e) {
            throw new RuntimeException(e);
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
