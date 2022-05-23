package it.polimi.ingsw.gui;

import it.polimi.ingsw.communication.modelData.TurnData;
import it.polimi.ingsw.gui.gamePane.TurnPane;
import it.polimi.ingsw.gui.preferencesPane.PreferencePane;
import it.polimi.ingsw.model.TurnPhase;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Gui extends Application implements EventHandler<ActionEvent> {
    BorderPane layout;
    Button exit;
    Stage stage;

    public void view(){
        launch();
    }

    @Override
    public void start(Stage stage){
        this.stage = stage;
        stage.setTitle("Eriantys");
        layout = new BorderPane();
        exit = new Button("Exit");

        layout.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(10))));
        exit.setOnAction(this);

        layout.setCenter(new PreferencePane(10));
        StackPane exitPane = new StackPane();
        exitPane.getChildren().add(exit);
        layout.setBottom(exitPane);

        layout.setRight(new TurnPane(10, new TurnData(new ArrayList<String>(){
            {
                add("fede");
                add("gio");
                add("lore");
            }
        }, new ArrayList<String>(){
            {
                add("fede");
                add("gio");
                add("lore");
            }
        }, TurnPhase.PLANNING, "fede")));
        Scene scene = new Scene(layout, 500, 500);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            stage.close();
        }
        else {
            alert.close();
        }
    }
}
