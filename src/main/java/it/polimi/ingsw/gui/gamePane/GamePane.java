package it.polimi.ingsw.gui.gamePane;

import it.polimi.ingsw.communication.modelData.BoardData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class GamePane extends BorderPane implements EventHandler<ActionEvent> {
    BorderPane game;
    BoardData bd;

    // SomeClass turnInfo;

    public GamePane(){
        ObservableList<String> observableList = FXCollections.observableArrayList(bd.turn().getSittingOrder());
        ListView<String> sittingOrder = new ListView<>(observableList);
        getChildren().add(sittingOrder);
    }

    @Override
    public void handle(ActionEvent actionEvent){

    }

}
