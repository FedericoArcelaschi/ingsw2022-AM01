package it.polimi.ingsw.server.model.exceptions;

public class StudentException extends Exception {
    public StudentException(Throwable cause) {
        super(cause);
    }
    public StudentException(String message) {
        super(message);
    }

    //could be generified with a "Color" Attribute and a "Position" attribute

}
