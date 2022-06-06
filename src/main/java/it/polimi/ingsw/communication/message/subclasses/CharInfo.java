package it.polimi.ingsw.communication.message.subclasses;

import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.MessageType;

public class CharInfo extends Message {
    private final String info;

    public CharInfo(String cause) {
        super(MessageType.CHARINFO);
        this.info = cause;
    }

    public String getInfo() {
        return info;
    }

}
