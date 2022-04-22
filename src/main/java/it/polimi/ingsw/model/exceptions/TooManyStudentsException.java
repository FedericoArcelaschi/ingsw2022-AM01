package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.Color;

public class TooManyStudentsException extends StudentException{
    public TooManyStudentsException() {
    }


    public TooManyStudentsException(String message) {
        super(message);
    }

    public TooManyStudentsException(Throwable cause) {
        super(cause);
    }

    public TooManyStudentsException(String message, Color student) {
        super(message, student);
    }


}
