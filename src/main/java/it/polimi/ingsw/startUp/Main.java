package it.polimi.ingsw.startUp;

import it.polimi.ingsw.client.userInterface.cli.Cli;
import it.polimi.ingsw.server.communication.ServerMain;
import it.polimi.ingsw.client.userInterface.gui.Gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        System.out.println(Outputs.START);
        //TODO: if(screenSize > 200 characters)
        //          Outputs.getTitle();
        //      else
        //          Outputs.getSmallTitle();
        switch (getUserType(args)) {
            case SERVER -> new ServerMain(12345).run();
            case CLI -> new Cli();
            case GUI -> new Gui();
        }

    }

    private static UserType getUserType(String[] args) {
        if (args.length == 0 || args[0] == null)
            return getUserType();
        for (String argIn : args) {
            if(UserType.getUserType(argIn) != null) {
                return UserType.getUserType(argIn);
            }
        }
        System.out.println(Outputs.USER_TYPE_INVALID);
        return getUserType();
    }

    private static UserType getUserType() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(Outputs.USER_TYPE_REQUEST);
        UserType userType;
        try {
            userType = UserType.getUserType(br.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(userType == null) {
            System.out.println(Outputs.USER_TYPE_INVALID);
            userType = getUserType();
        }
        return userType;
    }
}
