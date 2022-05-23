package it.polimi.ingsw.gui.preferencesPane;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * A VBox that contains label and an inputField.
 */
public class InputPane extends VBox {
    private TextField field;
    private Label label;

    /**
     * @param s label string
     */
    public InputPane(String s){
        field = new TextField();
        label = new Label(s+":");

        field.setMaxSize(200,100);
        getChildren().addAll(label, field);
    }

    public String getText(){
        return field.getText();
    }

    public void setText(String s){
        field.setText(s);
    }
}
