package it.polimi.ingsw.model.exceptions;

public class StudentException extends Exception {

    public StudentException() {
    }

    public StudentException(String message) {
        super(message);
    }

    public StudentException(Throwable cause) {
        super(cause);
    }
}
