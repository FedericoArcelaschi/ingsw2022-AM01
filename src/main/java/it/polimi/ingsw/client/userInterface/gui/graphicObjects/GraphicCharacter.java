package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.*;

public class GraphicCharacter extends Pane {
    MultipleToggleGroup multipleToggleGroup;

    public GraphicCharacter(String name, List<StudentColor> students, String description, boolean active) {
        this.setPrefSize(111, 200);
        this.getStyleClass().addAll(List.of("character", CharacterExplanation.getInstance(name).getCSS()));

        FlowPane flowPane = new FlowPane();
        flowPane.setPrefSize(111, 100);
        flowPane.setHgap(5);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setLayoutY(50);
        MultipleToggleGroup toggleGroup = new MultipleToggleGroup(students.size());
        this.setMultipleToggleGroup(toggleGroup);

        for (StudentColor studentColor: students) {
            ToggleButton toggleButton = new GraphicStudent(studentColor, false);
            toggleGroup.add(toggleButton);
            flowPane.getChildren().add(toggleButton);
        }
        if(active) opacity();

        Tooltip tooltip = new Tooltip(description);
        Tooltip.install(this, tooltip);
        Tooltip.install(flowPane, tooltip);

        this.setAccessibleText(name);
        flowPane.setAccessibleText(name);

        this.getChildren().add(flowPane);
    }

    public MultipleToggleGroup getMultipleToggleGroup() {
        return multipleToggleGroup;
    }

    public void setMultipleToggleGroup(MultipleToggleGroup multipleToggleGroup) {
        this.multipleToggleGroup = multipleToggleGroup;
    }

    public void opacity() {
        setOpacity(0.5);
    }

    public List<String>  showDialog(int maxSelect) {
        return new ArrayList<>();
    }

    public static GraphicCharacter newGraphicCharacter(String name, Optional<List<StudentColor>> students, String description, boolean active, EnumMap<StudentColor, Integer> diningRoom) {
        switch (name.toLowerCase()) {
            case "taxman", "cook" -> {
                return new GraphicCharacterWithAlert(name, students.orElse(new ArrayList<>()), Arrays.asList(StudentColor.values()), description, active);
            }
            case "storyteller" -> {
                List<StudentColor> s = new ArrayList<>();
                diningRoom.keySet().forEach(key -> {
                    for (int i = 0; i < diningRoom.get(key); i++) {
                        s.add(key);
                    }
                });
                return new GraphicCharacterWithAlert(name, students.orElse(new ArrayList<>()), s, description, active);
            }
            default -> {
                return new GraphicCharacter(name, students.orElse(new ArrayList<>()), description, active);
            }
        }
    }
}
