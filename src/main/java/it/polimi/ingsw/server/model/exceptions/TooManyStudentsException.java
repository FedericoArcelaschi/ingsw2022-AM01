package it.polimi.ingsw.server.model.exceptions;

public class TooManyStudentsException extends StudentException{
    public TooManyStudentsException(String message) {
        super(message);
    }
}
