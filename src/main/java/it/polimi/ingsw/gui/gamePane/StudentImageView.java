package it.polimi.ingsw.gui.gamePane;

import it.polimi.ingsw.model.StudentColor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class StudentImageView extends ImageView {
    Image image;
    StudentColor color;

    public StudentImageView(StudentColor color) {
        this.color = color;
        this.image = new Image(color.path, 30, 30, false, false);
        this.setImage(image);
        this.setOnMouseClicked(this::onClick);
    }

    public StudentImageView(StudentColor color, boolean visible) {
        this(color);
        this.setVisible(visible);
    }

    private void onClick(MouseEvent e){

    }

}
