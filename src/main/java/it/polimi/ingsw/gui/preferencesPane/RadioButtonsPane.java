package it.polimi.ingsw.gui.preferencesPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * A VBox that contains label and horizontal list of RadioButton and select logics
 */
public class RadioButtonsPane extends VBox {
    private List<String> options;
    private List<RadioButton> buttons;
    private HBox optionsView;
    private ToggleGroup toggleGroup;
    private String selected;

    public RadioButtonsPane(String s, List<String> options) {
        this.options = options;  //List of the names of each single RadioButton
        buttons = new ArrayList<>();
        optionsView = new HBox(8);
        Label label = new Label(s+":");
        for (String option: options) {
            buttons.add(new RadioButton(option));
        }
        toggleGroup = new ToggleGroup();
        getChildren().add(label);
        buttons.get(0).setSelected(true);
        selected = options.get(0);
        for (RadioButton rb: buttons) {
            rb.setToggleGroup(toggleGroup);
            optionsView.getChildren().add(rb);
        }
        getChildren().add(optionsView);
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<>() {
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
                RadioButton rb = (RadioButton) toggleGroup.getSelectedToggle();
                if (rb != null) {
                    selected = rb.getText();
                }
            }
        });

    }

    public String getSelected() {
        return selected;
    }
}
