package it.polimi.ingsw.gui.gamePane;

import it.polimi.ingsw.communication.modelData.BoardData;
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

import java.util.ArrayList;


public class GamePane extends BorderPane implements EventHandler<ActionEvent> {
    BorderPane game;
    BoardData bd;
    TurnPane tp;

    // SomeClass turnInfo;

    public GamePane(){
        tp = new TurnPane(10, new TurnData(bd.turn().getSittingOrder(), bd.turn().getActionOrder(), bd.turn().getCurrentPhase(), bd.turn().getCurrentPlayer()));
        getChildren().add(tp);
    }

    @Override
    public void handle(ActionEvent actionEvent){

    }

}
