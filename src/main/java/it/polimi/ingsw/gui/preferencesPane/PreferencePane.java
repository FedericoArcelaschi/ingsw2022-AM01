package it.polimi.ingsw.gui.preferencesPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class PreferencePane extends VBox implements EventHandler<ActionEvent> {

    RadioButtonsPane playerPreference;
    RadioButtonsPane modePreference;

    public PreferencePane(double v) {
        super(v);
        InputPane username = new InputPane("Username");
        playerPreference = new RadioButtonsPane("Number of Players",Arrays.asList("2", "3", "4"));
        modePreference = new RadioButtonsPane("Mode", Arrays.asList("base", "expert"));
        Button submit = new Button("submit");
        submit.setOnAction(this);
        getChildren().addAll(username, playerPreference, modePreference, submit);
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }

}
