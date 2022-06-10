package it.polimi.ingsw.communication.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.JsonAdapter;

/**
 * generalization of messages
 */
@JsonAdapter(MessageAdapterGson.class)
public abstract class Message {

    protected final MessageType messageType;

    protected Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getType() {
        return messageType;
    }

    public String toJson() {
        return new Gson().toJson(this, Message.class);
    }
}