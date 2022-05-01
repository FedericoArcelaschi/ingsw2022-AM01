package it.polimi.ingsw.communication.exception;

public class ClientNotRespondingException extends Exception {
    public ClientNotRespondingException() {
        super();
    }
    public ClientNotRespondingException(String message) {
        super(message);
    }
}
