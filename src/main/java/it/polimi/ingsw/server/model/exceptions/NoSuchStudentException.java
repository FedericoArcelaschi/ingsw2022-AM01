package it.polimi.ingsw.server.model.exceptions;

public class NoSuchStudentException extends StudentException {

    public NoSuchStudentException(String message) {
        super(message);
    }

    //TODO: public NoSuchStudentException(Error message) {super(message);}

}

