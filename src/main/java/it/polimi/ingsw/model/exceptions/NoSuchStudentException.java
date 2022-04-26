package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.Color;

public class NoSuchStudentException extends StudentException {

    public NoSuchStudentException(String message) {
        super(message);
    }

    public NoSuchStudentException() {
        super();
    }

    public NoSuchStudentException(Throwable cause) {
        super(cause);
    }

    public NoSuchStudentException(Color color) {
        this.student = color;
    }

    public NoSuchStudentException(String message, Color color) {
        super(message);
        this.student = color;
    }

    public Color getColor() {
        return student;
    }


}

