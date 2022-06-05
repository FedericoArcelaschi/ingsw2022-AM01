package it.polimi.ingsw.communication.message;
import com.google.gson.*;
import java.lang.reflect.Type;

public class MessageAdapterGson implements JsonSerializer<Message>, JsonDeserializer<Message> {
    @Override
    public JsonElement serialize(Message message, Type type, JsonSerializationContext context) {
        return context.serialize(message);
    }

    @Override
    public Message deserialize(JsonElement messageJson, Type type, JsonDeserializationContext context)throws JsonParseException {
        final JsonElement messageType = getMessageType(messageJson.getAsJsonObject());
        final Type actualMessageType = MessageType.getClass(messageType);
        return context.deserialize(messageJson, actualMessageType);
    }

    private JsonElement getMessageType(final JsonObject wrapper) {
        final JsonElement elem = wrapper.get("messageType");
        if (elem == null)
            throw new JsonParseException("no 'messageType' member found in what was expected to be an message wrapper!!\r");
        return elem;
    }
}

