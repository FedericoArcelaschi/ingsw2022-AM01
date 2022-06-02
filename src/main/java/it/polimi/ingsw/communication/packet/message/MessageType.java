package it.polimi.ingsw.communication.packet.message;

import it.polimi.ingsw.communication.packet.message.command.Command;

import java.lang.reflect.Type;

public enum MessageType {
    PREFERENCES(Preferences.class),
    LOBBYINFO(LobbyInfo.class),
    COMMAND(Command.class),
    UPDATE(Update.class),
    PING(Ping.class),
    END(EndGame.class),
    CHARINFO(CharInfo.class),
    ERROR(Error.class);
    private final Type type;

    MessageType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String getTypeSerializable() {
        return type.getTypeName();
    }

    public static MessageType get(Type messageType) {
        for (MessageType mt : MessageType.values())
            if (mt.type.equals(messageType))
                return mt;
        return null;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
