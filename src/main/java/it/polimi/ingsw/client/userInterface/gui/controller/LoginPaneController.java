package it.polimi.ingsw.client.userInterface.gui.controller;

import it.polimi.ingsw.client.userInterface.gui.LoginPreferences;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.server.controller.GameType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.util.Set;
import java.util.function.Consumer;

public class LoginPaneController {
    @FXML TableView lobbyTable;
    @FXML Pane networkPane, preferencesPane, waitingForGamePane;
    @FXML TextField usernameTextField, ipTextField, portTextField;
    @FXML RadioButton player2RadioButton, player3RadioButton, player4RadioButton;
    @FXML CheckBox expertModeButton;
    @FXML Button submitButton;
    ToggleGroup nPlayer;
    Consumer<LoginPreferences> connect, sendPreferences;

    public void initialize(Consumer<LoginPreferences> connect, Consumer<LoginPreferences> sendPreferences) {
        nPlayer = new ToggleGroup();
        player2RadioButton.setToggleGroup(nPlayer);
        player3RadioButton.setToggleGroup(nPlayer);
        player4RadioButton.setToggleGroup(nPlayer);
        player2RadioButton.setSelected(true);
        expertModeButton.setSelected(false);
        this.connect = connect;
        this.sendPreferences = sendPreferences;
        drawTable();
    }

    public void connect(ActionEvent actionEvent) {
        connect.accept(getPreferences());
        submitButton.setDisable(false);
    }

    public void submitPreferences(ActionEvent actionEvent) {
        sendPreferences.accept(getPreferences());
        submitButton.setDisable(true);
        preferencesPane.setVisible(false);
        waitingForGamePane.setVisible(true);
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
        lobbyTable.getItems().clear();
        for(LobbyInfo.Lobby lobby: lobbyInfo.getLobbies()) {
            lobbyTable.getItems().add(lobby);
        }
    }

    private void drawTable() {
        TableColumn<LobbyInfo.Lobby, GameType> gameTypeColumn = new TableColumn<>("Game Type");
        gameTypeColumn.setCellValueFactory(new PropertyValueFactory<>("gameType"));
        TableColumn<LobbyInfo.Lobby, String> playerColumn = new TableColumn<>("Connected Players");
        playerColumn.setCellValueFactory(new PropertyValueFactory<>("formattedPlayers"));
        lobbyTable.getColumns().add(gameTypeColumn);
        lobbyTable.getColumns().add(playerColumn);
    }
}
