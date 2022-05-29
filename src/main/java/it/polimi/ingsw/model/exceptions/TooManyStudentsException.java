package it.polimi.ingsw.model.exceptions;

public class TooManyStudentsException extends StudentException{
    public TooManyStudentsException(String message) {
        super(message);
    }
}
