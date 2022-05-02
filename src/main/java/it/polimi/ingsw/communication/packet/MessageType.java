package it.polimi.ingsw.communication.packet;

import java.lang.reflect.Type;

public enum MessageType {
    PING(Ping.class),
    UPDATE(Update.class),
    ERROR(Error.class),
    PREFERENCES(Preferences.class),
    END(Error.class),
    COMMAND(CommandMessage.class);

    private Type type;

    MessageType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
