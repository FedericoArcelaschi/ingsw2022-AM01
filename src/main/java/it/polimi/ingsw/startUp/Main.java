package it.polimi.ingsw.startUp;

import it.polimi.ingsw.userInterface.cli.Cli;
import it.polimi.ingsw.controller.ServerMain;
import it.polimi.ingsw.userInterface.gui.Gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Outputs.getTitle();
        switch (getUserType(args)) {
            case SERVER ->  new ServerMain(1234).run();
            case CLI ->     new Cli();
            case GUI ->     new Gui();
        }
    }

    /**
     * The app is stuck here until the client insert the right input.
     */
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