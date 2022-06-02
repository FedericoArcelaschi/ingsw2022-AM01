package it.polimi.ingsw.communication.packet.message;

import it.polimi.ingsw.communication.packet.message.command.CommandMessage;

import java.lang.reflect.Type;

public enum MessageType {
    PREFERENCES(Preferences.class),
    LOBBYINFO(LobbyInfoMessage.class),
    COMMAND(CommandMessage.class),
    UPDATE(Update.class),
    PING(Ping.class),
    END(EndGameMessage.class),
    ERROR(ErrorMessage.class);


    private final Type type;

    MessageType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
