package it.polimi.ingsw.client.userInterface.gui.controller;

import it.polimi.ingsw.client.userInterface.gui.LoginPreferences;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.util.function.Consumer;

public class LoginPaneController {
    @FXML TextField usernameTextField, ipTextField, portTextField;
    @FXML RadioButton player2RadioButton, player3RadioButton, player4RadioButton;
    @FXML CheckBox expertModeButton;
    ToggleGroup nPlayer;
    Consumer<LoginPreferences> connect;

    public void initialize(Consumer<LoginPreferences> connect) {
        nPlayer = new ToggleGroup();
        player2RadioButton.setToggleGroup(nPlayer);
        player2RadioButton.setSelected(true);
        player3RadioButton.setToggleGroup(nPlayer);
        player4RadioButton.setToggleGroup(nPlayer);
        expertModeButton.setSelected(false);
        this.connect = connect;
    }

    public void submitPreferences(ActionEvent actionEvent) {
        connect.accept(getPreferences());
    }

    private LoginPreferences getPreferences() {
        String username = usernameTextField.getText();
        RadioButton selectedNPlayer = (RadioButton) nPlayer.getSelectedToggle();
        int nPlayers = Integer.parseInt(selectedNPlayer.getText().substring(0, 1));
        boolean expertMode = expertModeButton.selectedProperty().get();
        String ip = ipTextField.getText().equals("") ? ipTextField.getPromptText() : ipTextField.getText();
        int port = Integer.parseInt(portTextField.getText().equals("") ? portTextField.getPromptText() : portTextField.getText());
        try {
            return new LoginPreferences(ip, port, new Preferences(username, nPlayers, expertMode));
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
            getPreferences();
        }
        return null;
    }
}
