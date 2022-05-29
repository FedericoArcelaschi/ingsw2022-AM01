package it.polimi.ingsw.userInterface.gui.controller;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.userInterface.UserInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class LoginPaneController {
    @FXML TextField usernameTextField, ipTextField, portTextField;
    @FXML RadioButton player2RadioButton, player3RadioButton, player4RadioButton, baseModeRadioButton, expertModeRadioButton;
    ToggleGroup nPlayer, mode;
    UserInterface ui;

    public void initialize(UserInterface ui){
        nPlayer = new ToggleGroup();
        mode = new ToggleGroup();
        player2RadioButton.setToggleGroup(nPlayer);
        player3RadioButton.setToggleGroup(nPlayer);
        player4RadioButton.setToggleGroup(nPlayer);
        baseModeRadioButton.setToggleGroup(mode);
        expertModeRadioButton.setToggleGroup(mode);
        player2RadioButton.setSelected(true);
        baseModeRadioButton.setSelected(true);

        this.ui = ui;
    }

    public void submitPreferences(ActionEvent actionEvent) {
        RadioButton selectedNPlayer = (RadioButton) nPlayer.getSelectedToggle();
        RadioButton selectedMode = (RadioButton) mode.getSelectedToggle();

        ClientMain clientMain = new ClientMain(
                usernameTextField.getText(),
                Integer.parseInt(selectedNPlayer.getText().substring(0,1)),
                selectedMode.getText().equals("Expert Mode"),
                ipTextField.getText().equals("") ? ipTextField.getPromptText() : ipTextField.getText(),
                Integer.parseInt(portTextField.getText().equals("") ? portTextField.getPromptText() : portTextField.getText())
        );
        clientMain.connect(ui);
    }

    public void test(){
        System.out.println("test");
    }
}
