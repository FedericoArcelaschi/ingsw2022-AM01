package it.polimi.ingsw.gui.gamePane.castlePane;

import it.polimi.ingsw.communication.modelData.CastleData;
import it.polimi.ingsw.gui.ResourcesPath;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class CastlePane extends BorderPane {
    public CastlePane(double parentWidth, double parentHeight, CastleData castleData) {
        BackgroundSize bgs = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        this.setBackground(new Background(new BackgroundImage(
                new Image(ResourcesPath.CASTLE.path),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bgs
                )));
        this.setPrefWidth(parentWidth * 25/100);
        this.setBottom(new waitingRoomBox(9, parentHeight, castleData.waitingRoom()));
    }
}
