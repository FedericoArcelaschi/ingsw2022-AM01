package it.polimi.ingsw.gui.preferencesPane;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.controller.ServerMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * VBox with elements to insert username and preferences for the game.
 */
public class PreferencePane extends VBox implements EventHandler<ActionEvent> {

    RadioButtonsPane playerPreference;
    RadioButtonsPane modePreference;
    InputPane username = new InputPane("Username");
    ClientMain cm;
    ServerMain s = new ServerMain(1234);

    public PreferencePane(double v) {
        super(v);
        playerPreference = new RadioButtonsPane("Number of Players",Arrays.asList("2", "3", "4"));
        modePreference = new RadioButtonsPane("Mode", Arrays.asList("base", "expert"));
        submit.setOnAction(this);
        getChildren().addAll(username, playerPreference, modePreference, submit);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(submit)){
            // System.out.println(username.getText() +  ", " + playerPreference.getSelected() + ", " + modePreference.getSelected());
            boolean gamemode = !modePreference.getSelected().equals("base");
            ExecutorService executorService = Executors.newCachedThreadPool();
            cm = new ClientMain(username.getText(), Integer.parseInt(playerPreference.getSelected()), gamemode, "127.0.0.1", 1234);
            executorService.submit(s);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cm.connect();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(s.getConnectedPlayers());
        }
    }

}
