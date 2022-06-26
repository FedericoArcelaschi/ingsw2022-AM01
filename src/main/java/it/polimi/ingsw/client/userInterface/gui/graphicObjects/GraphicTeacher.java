package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import javafx.scene.control.ToggleButton;

public class GraphicTeacher extends ToggleButton {

    public GraphicTeacher(StudentColor studentColor) {
        this.getStyleClass().addAll("teacher", studentColor.getTeacherCSS());
        this.setPrefSize(35,35);
        this.setDisable(true);
    }

    public GraphicTeacher() {
        this.getStyleClass().addAll("teacher");
        this.setPrefSize(35,35);
        this.setDisable(true);
    }
}
