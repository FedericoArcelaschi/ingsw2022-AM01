package it.polimi.ingsw.client.userInterface.gui;

import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.client.userInterface.gui.controller.GamePaneController;
import it.polimi.ingsw.client.userInterface.gui.controller.LoginPaneController;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.server.model.baseLogic.Board;
import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Gui extends Application implements UserInterface {
    Stage stage;
    Boolean inGame;
    LoginPaneController loginPaneController;
    GamePaneController gamePaneController;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        double screenHeight = stage.getMaxHeight();
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
        /*
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                windowEvent.consume();
                //TODO: prompt to exit safely
            }
        });
        */
        stage.show();
        loginPaneController.initialize(this);
        //FIXME: for testing.
        draw(createBoardData());
    }

    private BoardData createBoardData() {
        //creates a random normal-mode board data for testing.
        Board b =  BoardFactory.getBoard(Arrays.asList("fede", "gio", "lore"), false);
        return b.getData("fede");
    }

    /**
     * draws the board data on update.
     * Loads the UX from the <code>/game.fxml</code> file.
     */
    @Override
    public void draw(BoardData boardData) {
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
        gamePaneController.draw(boardData);
        stage.setScene(new Scene(loginFXML));
        stage.centerOnScreen();
    }

    @Override
    public void printWaitingRoom(LobbyInfo lobbyInfo) {
        //TODO:
    }
}
