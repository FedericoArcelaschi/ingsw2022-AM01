package it.polimi.ingsw.communication.packet;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.packet.message.Message;

import java.lang.reflect.Type;

/**
 * class used to send data regarding both connection's and game's updates/commands/errors between server and client
 */
public final class Packet {
    private final MessageType type;
    private final String messageJson;

    public Packet(MessageType type, Message message){
        this.type = type;
        Gson parser = new Gson();
        this.messageJson = parser.toJson(message);
    }

    public MessageType getType() {
        return type;
    }

    public Type getTypeClass(){
        return type.getTypeClass();
    }

    public String getMessageJson() {
        return messageJson;
    }
}
