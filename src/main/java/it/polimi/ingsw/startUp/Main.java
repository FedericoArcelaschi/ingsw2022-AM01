package it.polimi.ingsw.startUp;

import it.polimi.ingsw.userInterface.cli.Cli;
import it.polimi.ingsw.controller.ServerMain;
import it.polimi.ingsw.userInterface.gui.Gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        System.out.println(Outputs.START.out);
        switch (getUserType(args)) {
            case SERVER -> {
                ServerMain serverMain = new ServerMain(1234);
                serverMain.run();
            }
            case CLI -> {
                Cli cli = new Cli();
            }
            case GUI -> {
                Gui gui = new Gui();
                gui.view();
            }
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
        System.out.println(Outputs.USER_TYPE_INVALID.out);
        return getUserType();
    }

    private static UserType getUserType() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(Outputs.USER_TYPE_REQUEST.out);
        UserType userType;
        try {
            userType = UserType.getUserType(br.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(userType == null) {
            System.out.println(Outputs.USER_TYPE_INVALID.out);
            userType = getUserType();
        }
        return userType;
    }
}
