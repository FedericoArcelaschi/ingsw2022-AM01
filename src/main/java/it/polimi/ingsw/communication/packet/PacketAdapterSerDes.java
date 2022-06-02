package it.polimi.ingsw.communication.packet;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PacketAdapterSerDes implements JsonSerializer<Packet>, JsonDeserializer<Packet> {

    @Override
    public JsonElement serialize(Packet packet, Type type, JsonSerializationContext context) {
        final JsonObject wrapper = new JsonObject();
        wrapper.addProperty("messageType", packet.getTypeSerializable());
        wrapper.add("message", context.serialize(packet.getMessage(), packet.getType().getType()));
        return wrapper;
    }

    @Override
    public Packet deserialize(JsonElement packetJson, Type type, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject wrapper = (JsonObject) packetJson;
        final JsonElement messageType = get(wrapper, "messageType");
        final JsonElement message = get(wrapper, "message");
        final Type actualMessageType = typeForName(messageType);
        return new Packet(
                context.deserialize(message, actualMessageType)
        );
    }

    private Type typeForName(final JsonElement typeElem) {
        try {
            return Class.forName(typeElem.getAsString());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    private JsonElement get(final JsonObject wrapper, String memberName) {
        //for debugging:
        System.out.println("in SerDes get:\n\t" + wrapper);

        final JsonElement elem = wrapper.get(memberName);
        if (elem == null)
            throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an packet wrapper");
        return elem;
    }
}

