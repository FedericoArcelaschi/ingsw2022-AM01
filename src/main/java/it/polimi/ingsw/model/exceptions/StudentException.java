package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.Color;

public class StudentException extends Exception {

    protected Color student;

    public StudentException() {
    }

    public StudentException(String message) {
        super(message);
    }

    public StudentException(Throwable cause) {
        super(cause);
    }

    public StudentException(String message, Color student) {
        super(message);
        this.student = student;
    }


    public Color getStudent() {
        return student;
    }
}
