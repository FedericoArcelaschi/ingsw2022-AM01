package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphicCloud extends Pane {

    public GraphicCloud(List<StudentColor> students){
        List<GraphicStudent> studentButtons = new ArrayList<>();
        for (StudentColor key : students) {
            studentButtons.add(new GraphicStudent(key));
        }
        setStudentPosition(studentButtons);
        this.getStyleClass().add("cloud");
        this.setPrefSize(185,200);
    }

    private void setStudentPosition(List<GraphicStudent> students){
        if(students.size() == 3){
            students.get(0).setLayoutX(60);
            students.get(0).setLayoutY(60);
            students.get(1).setLayoutX(100);
            students.get(1).setLayoutY(87.5);
            students.get(2).setLayoutX(60);
            students.get(2).setLayoutY(115);
        } else if (students.size() == 4) {
            students.get(0).setLayoutX(60);
            students.get(0).setLayoutY(60);
            students.get(1).setLayoutX(100);
            students.get(1).setLayoutY(60);
            students.get(2).setLayoutX(60);
            students.get(2).setLayoutY(115);
            students.get(3).setLayoutX(100);
            students.get(3).setLayoutY(115);
        }
        this.getChildren().addAll(students);
    }
}
