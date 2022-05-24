package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.DataBuilder;
import it.polimi.ingsw.gui.gamePane.GamePane;
import it.polimi.ingsw.gui.gamePane.castlePane.CastlePane;
import it.polimi.ingsw.communication.modelData.TurnData;
import it.polimi.ingsw.gui.gamePane.TurnPane;
import it.polimi.ingsw.gui.preferencesPane.PreferencePane;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.BoardFactory;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.TurnPhase;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

public class Gui extends Application implements EventHandler<ActionEvent> {

    double screenHeight, screenWidth;
    double sceneHeight, sceneWidth;
    BorderPane layout;
    Button exit, submit;
    Stage stage;
    PreferencePane preferencePane;

    public void view(){
        launch();
    }

    @Override
    public void start(Stage stage){

        screenHeight = Screen.getPrimary().getBounds().getHeight();
        screenWidth = Screen.getPrimary().getBounds().getWidth();
        sceneHeight = screenHeight * 40 / 100;
        sceneWidth = screenWidth * 40 / 100;

        this.stage = stage;
        stage.setTitle("Eriantys");
        layout = new BorderPane();
        exit = new Button("Exit");
        submit = new Button("submit");

        layout.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(10))));
        exit.setOnAction(this);
        submit.setOnAction(this);

        preferencePane = new PreferencePane(10);
        layout.setCenter(preferencePane);
        HBox exitPane = new HBox(10);
        exitPane.getChildren().addAll(submit, exit);
        layout.setBottom(exitPane);
        Scene scene = new Scene(layout, sceneWidth, sceneHeight);

        layout.setRight(new TurnPane(1, new TurnData(new ArrayList<String>(){
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

        stage.setScene(scene);
        //FIXME: icon not working
        stage.getIcons().add(new Image(ResourcesPath.ISLANDS.path + "island1.png"));
        stage.show();
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        if (exit.equals(actionEvent.getSource())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Are you sure you want to exit?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage.close();
            } else {
                alert.close();
            }
        }
        else if(submit.equals(actionEvent.getSource())){
            //TODO: start client main
            ClientMain clientMain = new ClientMain(preferencePane.getUsername(),
                    preferencePane.getNPlayer(),
                    preferencePane.getMode(),
                    "127.0.0.1",
                    1234);
            clientMain.connect();

            Scene scene = new Scene(new GamePane(), screenWidth * 80/100, screenHeight * 80/100);
            stage.setScene(scene);
            stage.centerOnScreen();
        }
    }
}
