package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import javafx.scene.control.ToggleButton;

public class GraphicStudent extends ToggleButton {

    public GraphicStudent(StudentColor studentColor) {
        this.getStyleClass().addAll("student", studentColor.getStudentCSS());
        this.setAccessibleText(studentColor.name());
        this.setPrefSize(25,25);
    }
    public GraphicStudent(StudentColor studentColor, boolean disable) {
        this.getStyleClass().addAll("student", studentColor.getStudentCSS());
        this.setAccessibleText(studentColor.name());
        this.setPrefSize(25,25);
        this.setDisable(disable);
    }
}
