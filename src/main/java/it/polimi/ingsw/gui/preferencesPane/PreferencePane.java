package it.polimi.ingsw.gui.preferencesPane;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.communication.ClientSender;
import it.polimi.ingsw.communication.HeartBeatServer;
import it.polimi.ingsw.communication.ServerReceiver;
import it.polimi.ingsw.communication.packet.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.Preferences;
import it.polimi.ingsw.controller.ServerMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.Socket;
import java.util.Arrays;

/**
 * VBox with elements to insert username and preferences for the game.
 */
public class PreferencePane extends VBox implements EventHandler<ActionEvent> {

    RadioButtonsPane playerPreference;
    RadioButtonsPane modePreference;
    Button submit = new Button("submit");
    InputPane username = new InputPane("Username");
    ClientMain cm;

    public PreferencePane(double v) {
        super(v);
        playerPreference = new RadioButtonsPane("Number of Players",Arrays.asList("2", "3", "4"));
        modePreference = new RadioButtonsPane("Mode", Arrays.asList("base", "expert"));
        submit.setOnAction(this);
        getChildren().addAll(username, playerPreference, modePreference, submit);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource() == submit){
            // System.out.println(username.getText() +  ", " + playerPreference.getSelected() + ", " + modePreference.getSelected());
            boolean gamemode = !modePreference.getSelected().equals("base");
            cm = new ClientMain(username.getText(), Integer.parseInt(playerPreference.getSelected()), gamemode, "127.0.0.1", 1234);
            cm.connect();
        }
    }

}
