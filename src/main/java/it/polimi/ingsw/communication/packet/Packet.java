package it.polimi.ingsw.communication.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.message.MessageType;

/**
 * class used to send data regarding both connection's and game's updates/commands/errors between server and client
 */
public class Packet {
    private final MessageType type;
    private final String messageJson;
    private GsonBuilder parser;
    public Packet(Message message, MessageType type) {
        this.type = type;
        parser = new GsonBuilder();
        parser.setPrettyPrinting();
        this.messageJson = parser.create().toJson(message);
    }

    public MessageType getType() {
        return type;
    }
    public Message getMessage() {
        return parser.create().fromJson(messageJson, type.getTypeClass());
    }

    @Override
    public String toString() {
        return "Packet {" +
                "type = " + type +
                ", messageJson='" + new Gson().fromJson(messageJson, type.getTypeClass().getClass()) + '\'' +
                '}';
    }
}
