package it.polimi.ingsw.model.exceptions;

public class NoSuchStudentException extends StudentException{

    public NoSuchStudentException(String message){
        super(message);
    }

    public NoSuchStudentException(){
        super();
    }

    public NoSuchStudentException(Throwable cause) {
        super(cause);
    }
}
