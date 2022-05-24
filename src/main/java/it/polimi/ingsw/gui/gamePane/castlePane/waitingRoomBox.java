package it.polimi.ingsw.gui.gamePane.castlePane;

import it.polimi.ingsw.gui.gamePane.StudentImageView;
import it.polimi.ingsw.model.StudentColor;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class waitingRoomBox extends VBox {
    HBox topRow, bottomRow;

    public waitingRoomBox(double v, double parentHeight, List<StudentColor> students) {
        super(v);
        topRow = new HBox(17);
        bottomRow = new HBox(17);
        int i;
        topRow.getChildren().add(new StudentImageView(StudentColor.YELLOW, false));
        for (i = 0; i < 4; i++) {
            topRow.getChildren().add(new StudentImageView(students.get(i)));
        }
        bottomRow.getChildren().add(new StudentImageView(StudentColor.YELLOW, false));
        bottomRow.getChildren().add(new StudentImageView(StudentColor.YELLOW, false));
        for (; i < 7; i++) {
            bottomRow.getChildren().add(new StudentImageView(students.get(i)));
        }
        topRow.setAlignment(Pos.TOP_CENTER);
        bottomRow.setAlignment(Pos.TOP_CENTER);
        this.setPrefHeight(parentHeight * 16.5/100);
        this.getChildren().addAll(topRow, bottomRow);
    }
}
