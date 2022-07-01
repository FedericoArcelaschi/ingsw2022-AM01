package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import it.polimi.ingsw.communication.modelData.CastleData;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OtherGraphicCastle extends VBox {

    public OtherGraphicCastle(CastleData castleData, boolean is3Game, boolean isExpertGame) {
        Pane topPane = new TopPane(castleData.username(), castleData.lastPlayedCard(), isExpertGame, castleData.coins());

        GraphicCastle castle = new GraphicCastle(castleData, true, is3Game);

        this.getChildren().addAll(topPane, castle);
    }

    public static class TopPane extends BorderPane {

        public TopPane(String username, String lastPlayedCard, boolean expert, int nCoin) {
            this.setPrefSize(290, 125);

            Pane usernamePane = new Pane();
            Label usernameLabel = new Label(username);
            usernameLabel.getStyleClass().add("username");

            int i;
            for (i=1; i<=10; i++) {
                String s = "[" + i + ", " + (i + 1) / 2 + "]";
                if (s.equals(lastPlayedCard)) break;
            }
            Pane lastPlayedCardPane = new GraphicAssistant(i);

            if(expert) {
                GraphicCoin coin = new GraphicCoin(nCoin);
                coin.setLayoutY(50);
                this.setCenter(coin);
            }

            lastPlayedCardPane.setPrefSize(69.375, 125);
            lastPlayedCardPane.getStyleClass().add("assistant-small");

            usernamePane.setMinSize(100, 125);
            usernamePane.setMaxSize(100, 125);
            usernamePane.getChildren().add(usernameLabel);

            this.setLeft(usernamePane);
            this.setRight(lastPlayedCardPane);
        }
    }
}
