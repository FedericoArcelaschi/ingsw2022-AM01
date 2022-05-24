package it.polimi.ingsw.startUp;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.controller.ServerMain;
import it.polimi.ingsw.gui.Gui;

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
                ClientMain clientMain = new ClientMain("PIPPO",
                        2,
                        false,
                        "127.0.0.1",
                        1234);
                clientMain.connect();
                while(true) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        String input = br.readLine();
                        clientMain.runCommand(input);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            case GUI -> {
                System.out.println("sono una gui");
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
