package it.polimi.ingsw.communication.packet.message;

import it.polimi.ingsw.communication.packet.message.command.Command;
import it.polimi.ingsw.communication.packet.message.command.CommandMessage;

import java.lang.reflect.Type;

public enum MessageType {
    PREFERENCES(Preferences.class),
    PING(Ping.class),
    LOBBYINFO(LobbyInfo.class),
    COMMAND(CommandMessage.class),
    UPDATE(Update.class),
    CHARINFO(CharInfo.class),
    ERROR(Error.class),
    END(EndGame.class);

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
        return name().toLowerCase();
    }

}
