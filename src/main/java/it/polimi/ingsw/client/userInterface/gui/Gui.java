package it.polimi.ingsw.client.userInterface.gui;

import it.polimi.ingsw.client.communication.ClientMain;
import it.polimi.ingsw.client.communication.ClientState;
import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.subclasses.EndGame;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.client.userInterface.gui.controller.GamePaneController;
import it.polimi.ingsw.client.userInterface.gui.controller.LoginPaneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;

public class Gui extends Application implements UserInterface {
    Stage stage;
    Scene login, game;
    LoginPaneController loginPaneController;
    GamePaneController gamePaneController;
    ClientMain clientMain;
    boolean inGame = false;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        FXMLLoader loginLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/login.fxml")));
        Parent loginFXML;
        try {
            loginFXML = loginLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loginPaneController = loginLoader.getController();
        stage.setTitle("Eriantys");
        login = new Scene(loginFXML);
        stage.setScene(login);
        stage.show();
        stage.setOnCloseRequest(event -> System.exit(0));
        loginPaneController.initialize(this::connect, this::sendPreferences);
    }

    /**
     * draws the board data on update.
     * Loads the UX from the <code>/game.fxml</code> file.
     */
    @Override
    public void draw(BoardData boardData){
        Platform.runLater(()->refresh(boardData));
    }


    public void refresh(BoardData boardData) {
        System.out.println(boardData);
        //draws the game panel for testing.
        if(!inGame) {
            FXMLLoader gameLoader
                    = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/game.fxml")));
            inGame = true;
            Parent gameFXML;
            try {
                gameFXML = gameLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            gamePaneController = gameLoader.getController();
            gamePaneController.initialize(this::send);
            gamePaneController.draw(boardData);
            stage.setTitle(boardData.username());
            game = new Scene(gameFXML);
            stage.setScene(game);
            stage.centerOnScreen();
        }
        else {
            gamePaneController.clean();
            gamePaneController.draw(boardData);
        }
    }

    @Override
    public void printLobby(LobbyInfo lobbyInfo) {
        Platform.runLater(()->loginPaneController.drawLobbyInfo(lobbyInfo));
    }

    @Override
    public void printError(String error) {
        if(gamePaneController != null) Platform.runLater(()->gamePaneController.printMessage(Alert.AlertType.WARNING, error));
    }

    @Override
    public void endCurrentGame(EndGame endGameMessage) {
        clientMain.setState(ClientState.GAME_ENDED);
        inGame = false;
        if(gamePaneController != null) Platform.runLater(()-> {
            gamePaneController.printMessage(Alert.AlertType.ERROR, endGameMessage.getCause());
            newGameMessage();
        });
    }

    private void newGameMessage() {
        if(gamePaneController.newGameMessage()) {
            clientMain.setState(ClientState.OUTSIDE_LOBBY);
            startNewGame();
        }
    }

    private void startNewGame() {
        stage.setScene(login);
        stage.centerOnScreen();
        loginPaneController.initialize(this::connect, this::sendPreferences);
    }

    @Override
    public void disconnected() {
        clientMain.setState(ClientState.NOT_CONNECTED);
        if(gamePaneController != null) Platform.runLater(()->gamePaneController.printMessage(Alert.AlertType.ERROR, "Connection lost, you left the game"));
        //TODO:
    }

    public void connect(InetSocketAddress address) {
        clientMain = new ClientMain(this);
        clientMain.connect(address);
        //TODO: Handle connected conditions
    }

    public void sendPreferences(Preferences preferences) {
        clientMain.sendPreferences(preferences);
    }

    public void send(Command command) {
        clientMain.runCommand(command);
    }
}
