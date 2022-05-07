package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.Color;

public class TooManyStudentsException extends StudentException{

    public TooManyStudentsException(String message) {
        super(message);
    }

}
