package it.polimi.ingsw.communication.message;

import com.google.gson.JsonElement;
import it.polimi.ingsw.communication.message.subclasses.CommandMessage;
import it.polimi.ingsw.communication.message.subclasses.*;
import it.polimi.ingsw.communication.message.subclasses.Error;

import java.lang.reflect.Type;

public enum MessageType {
    PREFERENCES(Preferences.class),
    PING(Ping.class),
    LOBBYINFO(LobbyInfo.class),
    COMMAND(CommandMessage.class),
    UPDATE(Update.class),
    ERROR(Error.class),
    END(EndGame.class);

    private final Type type;

    MessageType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    //for message deserialization:
    public static Type getClass(JsonElement messageType) {
        for (MessageType mt : MessageType.values()) {
            if(mt.name().equals(messageType.getAsString()))
                return mt.type;
        }
        throw new IllegalArgumentException(messageType + "not a valid MessageType");
    }

}
