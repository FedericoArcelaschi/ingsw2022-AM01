package it.polimi.ingsw.userInterface.gui.gamePane;

import it.polimi.ingsw.communication.modelData.TurnData;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.TurnPhase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.stream.Collectors;

public class TurnPane extends VBox {
    private Text sittingOrder;
    private Text actionOrder;
    private Text turnPhase;
    private Text currentPlayer;



    public TurnPane(double v, TurnData t){
        super(v);
        ObservableList<String> s = FXCollections.observableArrayList(t.getSittingOrder());
        sittingOrder = new Text("Players are sat in this order: " + String.join(", ", s));
        ObservableList<String> a = FXCollections.observableArrayList(t.getActionOrder());
        actionOrder = new Text("Players play in this order: " + String.join(", ", a));
        this.turnPhase = new Text("Current phase: " + t.getCurrentPhase().name());
        this.currentPlayer = new Text("Current player: " + t.getCurrentPlayer());
        getChildren().addAll(sittingOrder, actionOrder, turnPhase, currentPlayer);
    }

    //TODO: add ways to update the TurnPane. (Takes in new TurnData...)

}
