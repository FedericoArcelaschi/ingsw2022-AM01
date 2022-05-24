package it.polimi.ingsw.cli;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.client.Drawable;
import it.polimi.ingsw.communication.modelData.BoardData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cli implements Drawable {
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
}
