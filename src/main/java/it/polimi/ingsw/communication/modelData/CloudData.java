package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Color;

import java.util.List;

public record CloudData(
        List<Color> studentList
) {
    public CloudData(Cloud cloud){
        this(cloud.getStudentList());
    }
}
