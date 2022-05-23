package it.polimi.ingsw.gui.preferencesPane;

import it.polimi.ingsw.client.ClientMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.Socket;
import java.util.Arrays;

/**
 * VBox with elements to insert username and preferences for the game.
 */
public class PreferencePane extends VBox {

    RadioButtonsPane playerPreference;
    RadioButtonsPane modePreference;
    InputPane username = new InputPane("Username");
    ClientMain cm;

    public PreferencePane(double v) {
        super(v);
        playerPreference = new RadioButtonsPane("Number of Players",Arrays.asList("2", "3", "4"));
        modePreference = new RadioButtonsPane("Mode", Arrays.asList("base", "expert"));
        getChildren().addAll(username, playerPreference, modePreference);
    }

}
