package it.polimi.ingsw.userInterface.gui.gamePane;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.userInterface.gui.gamePane.castlePane.CastlePane;
import it.polimi.ingsw.communication.modelData.TurnData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;


public class GamePane extends BorderPane implements EventHandler<ActionEvent> {

    BorderPane game;
    BoardData bd;
    TurnPane tp;

    //TODO: DELETE BOARDDATA AS CLASS ATTRIBUTE
    public GamePane(BoardData bd) {
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

