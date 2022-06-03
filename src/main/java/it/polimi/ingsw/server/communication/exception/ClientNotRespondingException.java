package it.polimi.ingsw.server.communication.exception;

import com.sun.jdi.connect.spi.ClosedConnectionException;

public class ClientNotRespondingException extends ClosedConnectionException {
    public ClientNotRespondingException() {
        super();
    }
    public ClientNotRespondingException(String message) {
        super(message);
    }
}
