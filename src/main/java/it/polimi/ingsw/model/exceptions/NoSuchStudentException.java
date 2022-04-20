package it.polimi.ingsw.model.exceptions;

public class NoSuchStudentException extends Exception{

    public NoSuchStudentException(String message){
        super(message);
    }

    public NoSuchStudentException(){
        super();
    }
}
