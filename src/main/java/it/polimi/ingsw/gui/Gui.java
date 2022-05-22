package it.polimi.ingsw.gui;

import com.sun.prism.Graphics;
import it.polimi.ingsw.gui.preferencesPane.InputPane;
import it.polimi.ingsw.gui.preferencesPane.PreferencePane;
import it.polimi.ingsw.gui.preferencesPane.RadioButtonsPane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
         Image img = new Image("C:\\Users\\loren\\Desktop\\Università\\3° anno\\Progetto di ingegneria del software\\ingsw2022-AM01\\src\\main\\resources\\Personaggi\\CarteTOT_front4.jpg");
         ImageView image = new ImageView();
         image.setImage(img);
         layout.setLeft(image);
         Scene scene = new Scene(layout, 1000, 1000);
         stage.setScene(scene);
         stage.show();
    }


}
