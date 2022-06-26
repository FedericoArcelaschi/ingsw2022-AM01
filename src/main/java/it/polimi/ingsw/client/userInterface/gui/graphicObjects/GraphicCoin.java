package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;

public class GraphicCoin extends FlowPane {

    public GraphicCoin(int nCoin){
        Label quantity = new Label(nCoin + " x");
        ToggleButton coinIcon = new ToggleButton();

        coinIcon.getStyleClass().addAll("coin", "coinBackground");
        coinIcon.setPrefSize(50, 50);
        coinIcon.setDisable(true);
        this.setHgap(10);
        this.getChildren().addAll(quantity, coinIcon);
    }
}
