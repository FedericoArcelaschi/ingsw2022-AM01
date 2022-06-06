package it.polimi.ingsw.communication.message.subclasses;

import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.MessageType;

public class Error extends Message {
    private final int errorCode; //TODO: UPDATE ERROR CODES
    private final String message;

    public Error(int errorCode, String message) {
        super(MessageType.ERROR);
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

}
