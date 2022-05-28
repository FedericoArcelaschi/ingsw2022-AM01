package it.polimi.ingsw.userInterface.gui;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.controller.GameType;
import it.polimi.ingsw.userInterface.UserInterface;
import it.polimi.ingsw.userInterface.gui.controller.GamePaneController;
import it.polimi.ingsw.userInterface.gui.controller.LoginPaneController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Gui extends Application implements UserInterface {
    Stage stage;
    Boolean inGame;
    LoginPaneController loginPaneController;
    GamePaneController gamePaneController;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        inGame = false;
        FXMLLoader loginLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/login.fxml")));
        Parent loginFXML = null;
        try {
            loginFXML = loginLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loginPaneController = loginLoader.getController();
        stage.setTitle("Eriantys");
        stage.setScene(new Scene(loginFXML));
        /*
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                windowEvent.consume();
            }
        });*/
        stage.show();
        loginPaneController.initialize(this);
    }

    @Override
    public void draw(BoardData boardData) {
        FXMLLoader gameLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/game.fxml")));
        inGame = true;
        Parent loginFXML = null;
        try {
            loginFXML = gameLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gamePaneController = gameLoader.getController();
        stage.setScene(new Scene(loginFXML));
    }

    @Override
    public void roomOutput(List<String> connectedUser, GameType gameType) {

    }
}
