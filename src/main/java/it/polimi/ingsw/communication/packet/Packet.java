package it.polimi.ingsw.communication.packet;

import com.google.gson.Gson;

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

    public String getMessageJson() {
        return messageJson;
    }
}
