package it.polimi.ingsw.communication.message;

import com.google.gson.annotations.JsonAdapter;
import it.polimi.ingsw.communication.GsonProvider;

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
        return GsonProvider.get().toJson(this, Message.class);
    }
}