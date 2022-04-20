package it.polimi.ingsw.model.exceptions;

public class TooManyStudentsException extends Exception{
    public TooManyStudentsException() {
    }

    public TooManyStudentsException(String message) {
        super(message);
    }
}
