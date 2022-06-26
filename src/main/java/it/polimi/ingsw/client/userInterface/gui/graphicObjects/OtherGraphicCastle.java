package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import it.polimi.ingsw.communication.modelData.CastleData;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OtherGraphicCastle extends VBox {

    public OtherGraphicCastle(CastleData castleData, boolean is3Game) {
        Pane topPane = new Pane();
        topPane.setPrefSize(290, 125);
        topPane.getChildren().add(new Label(castleData.username()));

        GraphicCastle castle = new GraphicCastle(castleData, true, is3Game);

        this.getChildren().addAll(topPane, castle);
    }
}
