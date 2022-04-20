package it.polimi.ingsw.model.exceptions;

public class NotYourTurnException extends Exception{
    
    public NotYourTurnException() {
    }

    public NotYourTurnException(String message) {
        super(message);
    }

}
