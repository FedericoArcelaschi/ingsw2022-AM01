package it.polimi.ingsw.communication.message.subclasses;

import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.MessageType;

public class Error extends Message {
    private final String message;

    public Error(String message) {
        super(MessageType.ERROR);
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

}
