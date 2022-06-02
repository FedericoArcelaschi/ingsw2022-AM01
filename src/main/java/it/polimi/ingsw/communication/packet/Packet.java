package it.polimi.ingsw.communication.packet;

import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.communication.packet.message.MessageType;

/**
 * class used to send data regarding both connection's and game's updates/commands/errors between server and client
 */
public class Packet {

    private final MessageType type;
    private final Message message;

    public Packet(Message message) {
        this.type = message.getMessageType();
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }
    public String getTypeSerializable() {
        return type.getTypeSerializable();
    }
        public Message getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Packet {" +
                "messagetype : " + type +
                ", message : " + message +
                '}';
    }

    public String toJson() {
        return PacketParser.gson.toJson(this, Packet.class);
    }
}
