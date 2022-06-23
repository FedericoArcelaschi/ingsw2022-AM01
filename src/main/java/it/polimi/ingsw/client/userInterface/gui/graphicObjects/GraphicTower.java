package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import it.polimi.ingsw.server.model.baseLogic.Team;
import javafx.scene.control.ToggleButton;

public class GraphicTower extends ToggleButton {

    public GraphicTower(Team team) {
        this.getStyleClass().addAll("tower", team.getCSS());
        this.setPrefSize(35,35);
    }
}
