package it.polimi.ingsw.client.userInterface.gui.graphicObjects;

import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class MultipleToggleGroup {
    List<ToggleButton> elements;
    List<ToggleButton> selectedElements;
    final int maxSelected;

    public MultipleToggleGroup(int maxSelected) {
        this.maxSelected = maxSelected;
        this.elements = new ArrayList<>();
        this.selectedElements = new ArrayList<>();
    }

    public void add(List<ToggleButton> buttons) {
        for (ToggleButton button: buttons) {
            this.add(button);
        }
    }

    public void add(ToggleButton button) {
        button.setOnMouseClicked(this::selectWaitingRoom);
        if(!elements.contains(button))
            this.elements.add(button);
    }

    private void selectWaitingRoom(MouseEvent mouseEvent) {
        ToggleButton toggleButton = (ToggleButton) mouseEvent.getTarget();
        this.select(toggleButton);
    }

    public void select(ToggleButton button) {
        if(selectedElements.contains(button)){
            this.deselect(button);
        }
        else if(elements.contains(button)) {
            if (selectedElements.size() == maxSelected) {
                selectedElements.get(0).setSelected(false);
                selectedElements.remove(selectedElements.get(0));
            }
            selectedElements.add(button);
            button.setSelected(true);
        }
        else
            throw new IllegalArgumentException("no such button in toggle group");
    }

    private void deselect(ToggleButton button) {
        if(!elements.contains(button))
            throw new IllegalArgumentException("no such button in toggle group");
        button.setSelected(false);
        selectedElements.remove(button);
    }

    public List<ToggleButton> getSelectedToggles() {
        return new ArrayList<>(selectedElements);
    }
}
