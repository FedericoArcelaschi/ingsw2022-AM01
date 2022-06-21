package it.polimi.ingsw.communication.message.subclasses;

import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.MessageType;

public class EndGame extends Message {
    private final String cause;

    public EndGame(String cause) {
        super(MessageType.END);
        this.cause = cause;
        //this.cause = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    }

    public String getCause() {
        return cause;
    }

}
