package it.polimi.ingsw.client.userInterface.gui;

import it.polimi.ingsw.client.communication.ClientMain;
import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.subclasses.EndGame;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.expertMode.CharacterData;
import it.polimi.ingsw.server.model.baseLogic.Board;
import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.client.userInterface.gui.controller.GamePaneController;
import it.polimi.ingsw.client.userInterface.gui.controller.LoginPaneController;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Gui extends Application implements UserInterface {
    Stage stage;
    Boolean inGame;
    LoginPaneController loginPaneController;
    GamePaneController gamePaneController;
    ClientMain clientMain;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        inGame = false;
        FXMLLoader loginLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/login.fxml")));
        Parent loginFXML;
        try {
            loginFXML = loginLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loginPaneController = loginLoader.getController();
        stage.setTitle("Eriantys");
        stage.setScene(new Scene(loginFXML));
        stage.show();
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
        FXMLLoader gameLoader
                = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/game.fxml")));
        inGame = true;
        Parent loginFXML;
        try {
            loginFXML = gameLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gamePaneController = gameLoader.getController();
        gamePaneController.initialize(this::send);
        gamePaneController.draw(boardData);
        stage.setTitle(boardData.username());
        stage.setScene(new Scene(loginFXML));
        stage.centerOnScreen();
    }

    @Override
    public void printLobby(LobbyInfo lobbyInfo) {
        Platform.runLater(()->loginPaneController.drawLobbyInfo(lobbyInfo));
    }

    @Override
    public void printError(String error) {
        if(gamePaneController != null) Platform.runLater(()->gamePaneController.printError(error));
    }

    @Override
    public void endCurrentGame(EndGame endGameMessage) {
        //TODO
    }

    @Override
    public void disconnected() {
        //TODO:
    }

    public void connect(InetSocketAddress address) {
        clientMain = new ClientMain(this);
        Boolean connected = clientMain.connect(address);
        //TODO: Handle connected conditions
    }

    public void sendPreferences(Preferences preferences) {
        clientMain.sendPreferences(preferences);
    }

    public void send(Command command) {
        clientMain.runCommand(command);
    }
}
