package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.Color;

public class StudentException extends Exception {
    private PlaceEnum where;
    public StudentException(Throwable cause) {
        super(cause);
    }
    public StudentException(String message) {
        super(message);
    }

    //TODO: PlaceEnum getWhere(){return where;}

}
