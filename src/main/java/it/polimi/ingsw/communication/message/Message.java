package it.polimi.ingsw.communication.message;

/**
 * generalization of messages
 */
public abstract class Message {

    protected final MessageType messageType;

    protected Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getType() {
        return messageType;
    }

    public String toJson() {
        return MessageParser.gson.toJson(this, Message.class);
    }
}