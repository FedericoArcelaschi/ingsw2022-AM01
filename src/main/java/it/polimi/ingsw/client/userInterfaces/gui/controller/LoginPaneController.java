package it.polimi.ingsw.client.userInterfaces.gui.controller;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.client.userInterfaces.UserInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class LoginPaneController {
    @FXML TextField usernameTextField, ipTextField, portTextField;
    @FXML RadioButton player2RadioButton, player3RadioButton, player4RadioButton;
    @FXML CheckBox expertModeButton;
    ToggleGroup nPlayer;
    UserInterface userInterface;

    public void initialize(UserInterface userInterface){
        nPlayer = new ToggleGroup();
        player2RadioButton.setToggleGroup(nPlayer);
        player3RadioButton.setToggleGroup(nPlayer);
        player4RadioButton.setToggleGroup(nPlayer);
        player2RadioButton.setSelected(true);
        expertModeButton.setSelected(false);
        this.userInterface = userInterface;
    }

    public void submitPreferences(ActionEvent actionEvent) {
        RadioButton selectedNPlayer = (RadioButton) nPlayer.getSelectedToggle();
        boolean expertMode = expertModeButton.selectedProperty().get();

        ClientMain clientMain = new ClientMain(
                usernameTextField.getText(),
                Integer.parseInt(selectedNPlayer.getText().substring(0, 1)),
                expertMode,
                ipTextField.getText().equals("") ? ipTextField.getPromptText() : ipTextField.getText(),
                Integer.parseInt(portTextField.getText().equals("") ? portTextField.getPromptText() : portTextField.getText())
        );
        clientMain.connect(userInterface);
    }

    public void test(){
        System.out.println("test");
    }
}
