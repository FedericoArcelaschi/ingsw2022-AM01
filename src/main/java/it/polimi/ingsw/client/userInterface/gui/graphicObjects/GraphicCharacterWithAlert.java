package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class GraphicCharacterWithAlert extends GraphicCharacter {
    private final List<StudentColor> alertStudents;

    public GraphicCharacterWithAlert(String name, List<StudentColor> students, List<StudentColor> alertStudents, String description, boolean active) {
        super(name, students, description, active);

        this.alertStudents = alertStudents;
    }

    public List<String> showDialog(int maxSelect){
        List<ToggleButton> graphicStudents = new ArrayList<>(alertStudents.stream().map(GraphicStudent::new).toList());
        MultipleToggleGroup toggleGroup = new MultipleToggleGroup(maxSelect);
        toggleGroup.add(graphicStudents);
        FlowPane pane = new FlowPane();
        pane.getChildren().addAll(graphicStudents);
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(pane);
        dialogPane.getStylesheets().add(getClass().getResource("/fxml/css/style.css").toExternalForm());
        dialogPane.setHeaderText("Chose " + maxSelect + " students");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setDialogPane(dialogPane);
        alert.getButtonTypes().addAll(new ButtonType("confirm"));
        alert.showAndWait();

        return toggleGroup.getSelectedToggles().stream().map(ToggleButton::getAccessibleText).toList();
    }
}
