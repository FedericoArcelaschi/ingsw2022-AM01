package it.polimi.ingsw.gui;

import it.polimi.ingsw.gui.preferencesPane.InputPane;
import it.polimi.ingsw.gui.preferencesPane.PreferencePane;
import it.polimi.ingsw.gui.preferencesPane.RadioButtonsPane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;

public class Gui extends Application{
    BorderPane layout;

    public void view(){
        launch();
    }

    @Override
    public void start(Stage stage){
         stage.setTitle("Eriantys");
         layout = new BorderPane();
         layout.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(10))));

         layout.setRight(new PreferencePane(10));
         Scene scene = new Scene(layout, 1000, 1000);
         stage.setScene(scene);
         stage.show();
    }


}
