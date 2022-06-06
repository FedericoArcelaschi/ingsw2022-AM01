package it.polimi.ingsw.client.userInterface.gui;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.ModelDataBuilder;
import it.polimi.ingsw.server.controller.GameType;
import it.polimi.ingsw.server.model.baseLogic.Board;
import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.client.userInterface.gui.controller.GamePaneController;
import it.polimi.ingsw.client.userInterface.gui.controller.LoginPaneController;
import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import javafx.application.Application;
import javafx.application.Platform;
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
        loginPaneController.initialize(this);
        //FIXME: for testing.
        draw(createBoardData());
    }

    private BoardData createBoardData(){
        //FIXME: for testing.
        Board b =  BoardFactory.getBoard(Arrays.asList("fede", "gio"), true);
        try{
            b.playCard("fede", 1);
            b.changePhase();
            b.playCard("gio", 10);
            b.changePhase();
            b.moveStudentsToDiningRoom("fede", b.getCastle("fede").getWaitingRoom().subList(0,2));
            b.moveStudentToIsland("fede", 1, b.getCastle("fede").getWaitingRoom().subList(0,1));
            b.changePhase();
            //b.moveMotherNature(1);
        } catch (PhaseNotRightException | NotYourTurnException | NoSuchStudentException | TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        return ModelDataBuilder.newBoardData("fede", b);
    }

    /**
     * draws the board data on update.
     * Loads the UX from the <code>/game.fxml</code> file.
     * @param boardData
     */
    @Override
    public void draw(BoardData boardData){
        Platform.runLater(()->refresh(boardData));
    }


    public void refresh(BoardData boardData) {
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
    public void printWaitingRoom(List<String> connectedUser, GameType gameType) {
        //TODO:
    }
}
