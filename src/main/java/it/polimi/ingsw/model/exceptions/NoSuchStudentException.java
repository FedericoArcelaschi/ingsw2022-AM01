package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.Color;

public class NoSuchStudentException extends StudentException {

    public NoSuchStudentException(String message) {
        super(message);
    }

    //TODO: public NoSuchStudentException(ErrorMessage message) {super(message);}

}

