package it.polimi.ingsw.communication.packet;

import it.polimi.ingsw.communication.packet.message.CommandMessage;
import it.polimi.ingsw.communication.packet.message.ErrorMessage;
import it.polimi.ingsw.communication.packet.message.Ping;
import it.polimi.ingsw.communication.packet.message.Preferences;
import it.polimi.ingsw.communication.packet.message.Update;

import java.lang.reflect.Type;

public enum MessageType {
    PING(Ping.class),
    UPDATE(Update.class),
    ERROR(ErrorMessage.class),
    PREFERENCES(Preferences.class),
    END(ErrorMessage.class),
    COMMAND(CommandMessage.class);

    private Type type;

    MessageType(Type type) {
        this.type = type;
    }

    public Type getTypeClass() {
        return type;
    }
}
