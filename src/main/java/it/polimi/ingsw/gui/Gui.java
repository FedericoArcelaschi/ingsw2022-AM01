package it.polimi.ingsw.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;

public class Gui extends Application implements EventHandler<ActionEvent> {
    private StackPane layout;
    private Button button;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
         stage.setTitle("Eriantys");
         layout = new StackPane();
         button = new Button("Play");

         button.setOnAction(this);
         layout.getChildren().add(button);
         Scene scene = new Scene(layout, 300, 300);
         stage.setScene(scene);
         stage.show();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource() == button){

        }
    }
}
