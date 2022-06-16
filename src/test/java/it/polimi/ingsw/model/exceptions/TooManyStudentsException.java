package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;

public class TooManyStudentsException extends StudentException {

    StudentColor color;

    public TooManyStudentsException(String message, StudentColor color) {
        super(message);
        this.color = color;
    }
    public TooManyStudentsException(String message) {
        super(message);
    }


    public StudentColor getColor() {
        return color;
    }
}
