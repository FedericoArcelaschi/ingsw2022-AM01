package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import it.polimi.ingsw.communication.modelData.IslandData;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.EnumMap;

public class GraphicIsland extends Pane {

    public GraphicIsland(EnumMap<StudentColor, Integer> students, int size, Team ownership, int index, boolean motherNature) {
        this.setPrefSize(185, 200);
        this.getStyleClass().add("island");
        this.setAccessibleText(String.valueOf(index+1));
        FlowPane islandFlowPane = new FlowPane();
        islandFlowPane.setPrefSize(145, 160);
        islandFlowPane.setLayoutX(20);
        islandFlowPane.setLayoutY(20);
        islandFlowPane.setAlignment(Pos.CENTER);
        islandFlowPane.setAccessibleText(String.valueOf(index+1));
        //Adding students
        for (StudentColor color : students.keySet()) {
            for (int j = 0; j < students.get(color); j++) {
                ToggleButton toggleButton = new GraphicStudent(color, true);
                islandFlowPane.getChildren().add(toggleButton);
            }
        }
        //adding mother nature
        if (motherNature) {
            ToggleButton toggleButton = new GraphicMotherNature();
            islandFlowPane.getChildren().add(toggleButton);
        }
        //Adding towers
        for (int j = 0; j < size && ownership != null; j++) {
            ToggleButton toggleButton = new GraphicTower(ownership);
            islandFlowPane.getChildren().add(toggleButton);
        }
        this.getChildren().add(islandFlowPane);
    }
}
