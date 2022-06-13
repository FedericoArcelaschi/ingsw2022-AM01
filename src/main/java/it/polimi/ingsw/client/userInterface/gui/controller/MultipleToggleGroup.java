package it.polimi.ingsw.client.userInterface.gui.controller;

import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.util.ArrayList;
import java.util.List;

public class MultipleToggleGroup {
    List<ToggleButton> elements;
    List<ToggleButton> selectedElements;
    final int maxSelected;

    public MultipleToggleGroup(int maxSelected) {
        this.maxSelected = maxSelected;
        elements = new ArrayList<>();
        selectedElements = new ArrayList<>();
    }

    public void add(ToggleButton button) {
        if(!elements.contains(button))
            elements.add(button);
    }

    public void add(List<ToggleButton> buttons) {
        for (ToggleButton button: buttons) {
            add(button);
        }
    }

    public void select(ToggleButton button) {
        if(selectedElements.contains(button)){
            deselect(button);
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
        if(elements.contains(button)) {
            button.setSelected(false);
            selectedElements.remove(button);
        }
        else
            throw new IllegalArgumentException("no such button in toggle group");
    }

    public List<ToggleButton> getSelectedToggles() {
        return new ArrayList<ToggleButton>(selectedElements);
    }
}
