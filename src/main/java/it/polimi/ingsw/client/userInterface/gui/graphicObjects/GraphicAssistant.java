package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import javafx.scene.layout.Pane;

public class GraphicAssistant extends Pane {

    public GraphicAssistant(int i) {
        this.setPrefSize(111, 200);
        this.setAccessibleText("card"+i);
        this.getStyleClass().addAll("cardAssistant" + (i), "assistant");
    }
}
