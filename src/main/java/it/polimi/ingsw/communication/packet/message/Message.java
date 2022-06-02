package it.polimi.ingsw.communication.packet.message;

/**
 * generalization of messages
 */
public abstract class Message {
    protected final MessageType messageType;
    protected Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}