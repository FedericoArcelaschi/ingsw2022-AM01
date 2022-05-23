package it.polimi.ingsw.communication.packet;

import it.polimi.ingsw.communication.packet.message.*;

import java.lang.reflect.Type;

public enum MessageType {
    PING(Ping.class),
    UPDATE(Update.class),
    ERROR(ErrorMessage.class),
    PREFERENCES(Preferences.class),
    END(EndGameMessage.class),
    COMMAND(CommandMessage.class),
    LOBBY(LobbyInfoMessage.class);

    private Type type;

    MessageType(Type type) {
        this.type = type;
    }

    public Type getTypeClass() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
