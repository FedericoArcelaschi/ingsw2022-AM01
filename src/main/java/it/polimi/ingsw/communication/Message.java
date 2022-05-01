package it.polimi.ingsw.communication;

import it.polimi.ingsw.communication.modelData.BoardData;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public final class Message {
    private final UUID id;
    private final MessageType type;
    private final String message;
    private final BoardData data;
    private final Timestamp time;

    public Message(UUID id,MessageType type, String message, BoardData data, Timestamp time) {
        this.type = type;
        this.message = message;
        this.data = data;
        this.time = time;
        this.id = id;
    }

    public Message(MessageType type) {
        this(UUID.randomUUID(), type, null, null, new Timestamp(System.currentTimeMillis()));
    }

    public Message(UUID id,MessageType type){
        this(id, type, null, null, new Timestamp(System.currentTimeMillis()));
    }

    public Message(MessageType type, BoardData data) {
        this(UUID.randomUUID(), type, null, data, new Timestamp(System.currentTimeMillis()));
    }

    public Message(MessageType type, String message) {
        this(UUID.randomUUID(), type, message, null, new Timestamp(System.currentTimeMillis()));
    }

    public Message(@NotNull Message message) {
        this(message.id(), message.type(), message.message(), message.data(), message.time());
    }

    public UUID id(){
        return id;
    }

    public MessageType type() {
        return type;
    }

    public String message() {
        return message;
    }

    public BoardData data() {
        return data;
    }

    public Timestamp time() {
        return time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Message) obj;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, message, data, time);
    }

    @Override
    public String toString() {
        return "Message[" +
                "type=" + type + ", " +
                "message=" + message + ", " +
                "data=" + data + ", " +
                "time=" + time + ']';
    }

}
