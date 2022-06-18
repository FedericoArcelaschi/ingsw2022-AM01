package it.polimi.ingsw.client.userInterface.gui.controller;

import it.polimi.ingsw.client.userInterface.gui.LoginPreferences;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.server.controller.GameType;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class LoginPaneController {
    @FXML public TableView lobbyTable;
    @FXML Pane networkPane;
    @FXML TextField usernameTextField, ipTextField, portTextField;
    @FXML RadioButton player2RadioButton, player3RadioButton, player4RadioButton;
    @FXML CheckBox expertModeButton;
    @FXML Button submitButton;
    ToggleGroup nPlayer;
    Consumer<LoginPreferences> connect;

    public void initialize(Consumer<LoginPreferences> connect) {
        nPlayer = new ToggleGroup();
        player2RadioButton.setToggleGroup(nPlayer);
        player3RadioButton.setToggleGroup(nPlayer);
        player4RadioButton.setToggleGroup(nPlayer);
        player2RadioButton.setSelected(true);
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

    public void drawLobbyInfo(LobbyInfo lobbyInfo) {
        lobbyTable.setVisible(true);
        networkPane.setVisible(false);
        submitButton.setDisable(false);
        TableColumn<LobbyInfo.Lobby, GameType> gameTypeColumn = new TableColumn<>("Game Type");
        gameTypeColumn.setCellValueFactory(new PropertyValueFactory<>("gameType"));
        TableColumn<LobbyInfo.Lobby, Set<String>> playerColumn = new TableColumn<>("Connected Players");
        playerColumn.setCellValueFactory(new PropertyValueFactory<>("connectedPlayers"));
        lobbyTable.getColumns().add(gameTypeColumn);
        lobbyTable.getColumns().add(playerColumn);

        for(LobbyInfo.Lobby lobby: lobbyInfo.getPlayerInLobbyMap()) {
            lobbyTable.getItems().add(lobby);
        }
    }
}
