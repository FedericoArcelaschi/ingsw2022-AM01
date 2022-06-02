package it.polimi.ingsw.client.userInterface.cli;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.server.controller.GameType;
import it.polimi.ingsw.communication.modelData.BoardData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Cli implements UserInterface {
    String username;
    int nPlayer;
    boolean mode;

    public Cli() {
        //TODO: ask user preferences.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter username:");
            username = br.readLine();
            System.out.println("Enter nPlayer:");
            nPlayer = Integer.parseInt(br.readLine());
            System.out.println("Expert mode?");
            mode = Boolean.parseBoolean(br.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClientMain clientMain = new ClientMain(username,
                nPlayer,
                mode,
                "127.0.0.1",
                1234);
        clientMain.connect(this);
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
    public void draw(BoardData boardData) {
        System.out.println(boardData.toString());
    }

    @Override
    public void roomOutput(List<String> connectedUser, GameType gameType) {
        StringBuilder sb = new StringBuilder();
        sb.append("Player in queue: \n");
        for (String user : connectedUser) {
            sb.append("\t").append(user).append("\n");
        }
        sb.append("Game type: ").append(gameType).append("\n");
    }
}
