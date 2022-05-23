package it.polimi.ingsw.gui.gamePane;

import it.polimi.ingsw.communication.modelData.TurnData;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.TurnPhase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TurnPane extends VBox {
    private ListView<String> sittingOrder;
    private ListView<String> actionOrder;
    private Text turnPhase;
    private Text currentPlayer;



    public TurnPane(double v, TurnData t){
        super(v);
        ObservableList<String> s = FXCollections.observableArrayList(t.getSittingOrder());
        sittingOrder = new ListView<>(s);
        ObservableList<String> a = FXCollections.observableArrayList(t.getActionOrder());
        actionOrder = new ListView<>(s);
        this.turnPhase = new Text(t.getCurrentPhase().name());
        this.currentPlayer = new Text(t.getCurrentPlayer());
        getChildren().addAll(sittingOrder, actionOrder, turnPhase, currentPlayer);
    }

}
