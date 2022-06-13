package it.polimi.ingsw.client.userInterface.gui;

import it.polimi.ingsw.client.communication.ClientMain;
import it.polimi.ingsw.communication.command.Command;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.communication.message.subclasses.Preferences;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.expertMode.CharacterData;
import it.polimi.ingsw.server.model.baseLogic.Board;
import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.client.userInterface.UserInterface;
import it.polimi.ingsw.client.userInterface.gui.controller.GamePaneController;
import it.polimi.ingsw.client.userInterface.gui.controller.LoginPaneController;
import it.polimi.ingsw.server.model.baseLogic.Turn;
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
        loginPaneController.initialize(this::connect);
        //FIXME: for testing.
        draw(createBoardData());
    }

    private BoardData createBoardData(){
        //FIXME: for testing.
        Board b =  BoardFactory.getBoard(Arrays.asList("Fede", "Gio"/*, "pippo"*/), true);
        Turn t = b.getTurn();
        try{
            for (String player: t.getSittingOrder()) {
                int cardId = player.equals("Fede") ? 1 : 10;
                b.playCard(player, cardId);
                b.changePhase();
            }
//            b.playCard("pippo", 8);
//            b.changePhase();
            List<StudentColor> studentColorList = b.getCastle("Fede").getWaitingRoom().subList(0,1);
            b.moveStudentsToDiningRoom("Fede", studentColorList);
            b.moveStudentToIsland("Fede", 1, studentColorList);
            b.moveStudentsToDiningRoom("Fede", b.getCastle("Fede").getWaitingRoom().subList(0,1));
            b.changePhase();
            b.moveMotherNature(1);
            b.changePhase();
//            b.chooseCloud("Fede", 1);
//            b.changePhase();

        } catch (PhaseNotRightException | NotYourTurnException | TooManyStudentsException e) {
            throw new RuntimeException(e);
        } catch (NoSuchStudentException e) {
            return createBoardData();
        }
        //System.out.println(bd);
        BoardData bd = b.getData("Fede");
        if(!bd.characters().stream().map(CharacterData::getName).toList().contains("MONK"))
            return createBoardData();
        return bd;
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
        stage.setScene(new Scene(loginFXML));
        stage.centerOnScreen();
    }

    @Override
    public void printLobby(LobbyInfo lobbyInfo) {
        //TODO:
    }

    @Override
    public void printError(String error) {
        if(gamePaneController != null) gamePaneController.printError(error);
    }

    public void connect(LoginPreferences loginPreferences) {
        Preferences preferences = loginPreferences.preferences();
        clientMain = new ClientMain(loginPreferences.IP(), loginPreferences.port(), preferences);
        try {
            clientMain.connect(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Command command) {
        clientMain.runCommand(command);
    }
}
