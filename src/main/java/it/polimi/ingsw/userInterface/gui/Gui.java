package it.polimi.ingsw.userInterface.gui;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.ModelDataBuilder;
import it.polimi.ingsw.controller.GameType;
import it.polimi.ingsw.model.baseLogic.Board;
import it.polimi.ingsw.model.baseLogic.BoardFactory;
import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.model.baseLogic.Turn;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.userInterface.UserInterface;
import it.polimi.ingsw.userInterface.gui.controller.GamePaneController;
import it.polimi.ingsw.userInterface.gui.controller.LoginPaneController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Arrays;
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
        draw(createBoardData());
    }

    private BoardData createBoardData() {
        Board b =  BoardFactory.getBoard(Arrays.asList("fede", "gio"), new Turn(Arrays.asList("fede", "gio")));
        return ModelDataBuilder.newBoardData("fede", b);
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

        gamePaneController.draw(boardData);
        stage.setScene(new Scene(loginFXML));
        stage.centerOnScreen();
    }

    @Override
    public void roomOutput(List<String> connectedUser, GameType gameType) {

    }
}
