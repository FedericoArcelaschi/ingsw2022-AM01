package it.polimi.ingsw.model.exceptions;

public class TooManyStudentsException extends StudentException{
    public TooManyStudentsException() {
    }

    public TooManyStudentsException(String message) {
        super(message);
    }

    public TooManyStudentsException(Throwable cause) {
        super(cause);
    }
}
