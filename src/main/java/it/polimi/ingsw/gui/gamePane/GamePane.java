package it.polimi.ingsw.gui.gamePane;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.DataBuilder;
import it.polimi.ingsw.gui.gamePane.castlePane.CastlePane;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.BoardFactory;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.communication.modelData.TurnData;
import it.polimi.ingsw.model.TurnPhase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.Arrays;


public class GamePane extends BorderPane implements EventHandler<ActionEvent> {

    BorderPane game;
    BoardData bd;
    TurnPane tp;

    public GamePane() {
        Board b = BoardFactory.getBoard(Arrays.asList("fede", "gio"), new Turn(Arrays.asList("fede", "gio")), 1);
        BoardData bd = DataBuilder.newBoardData("fede", b);
        tp = new TurnPane(10, new TurnData(bd.turn().getSittingOrder(), bd.turn().getActionOrder(), bd.turn().getCurrentPhase(), bd.turn().getCurrentPlayer()));
        double sceneWidth = Screen.getPrimary().getBounds().getWidth() * 80 / 100;
        double sceneHeight = Screen.getPrimary().getBounds().getHeight() * 80 / 100;
        setRight(new CastlePane(sceneWidth, sceneHeight, bd.myCastle()));
        setLeft(tp);
    }

        @Override
        public void handle (ActionEvent actionEvent){

        }

}

