package it.polimi.ingsw.communication.modelData.expertMode;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.modelData.*;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BoardDataAdapter implements JsonSerializer<BoardData>, JsonDeserializer<BoardData> {

    @Override
    public JsonElement serialize(BoardData src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject wrapper = new JsonObject();
        wrapper.addProperty("type", src.getClass().getName());
        wrapper.add("username", context.serialize(src.username()));
        wrapper.add("nPlayer", context.serialize(src.nPlayer()));
        wrapper.add("motherNaturePosition", context.serialize(src.motherNaturePosition()));
        wrapper.add("cloudList", context.serialize(src.cloudList()));
        wrapper.add("islandList", context.serialize(src.islandList()));
        wrapper.add("myCastle", context.serialize(src.myCastle()));
        wrapper.add("otherCastles", context.serialize(src.otherCastles()));
        wrapper.add("turn", context.serialize(src.turn()));
        wrapper.add("characters", context.serialize(src.characters()));
        wrapper.add("activeCharacter", context.serialize(src.activeChar()));
        return wrapper;
    }

    @Override
    public BoardData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject wrapper = (JsonObject) json;
        if (typeForName(wrapper.get("type"))==BoardData.class) {
            Type islandListType = new TypeToken<List<IslandData>>() {
            }.getType();
            Type castleListType = new TypeToken<List<CastleData>>() {
            }.getType();
            Type cloudListType = new TypeToken<List<CloudData>>() {
            }.getType();
            return new BoardData(context.deserialize(wrapper.get("username"), String.class),
                    context.deserialize(wrapper.get("nPlayer"), Integer.class),
                    context.deserialize(wrapper.get("motherNaturePosition"), Integer.class),
                    context.deserialize(wrapper.get("cloudList"), cloudListType),
                    context.deserialize(wrapper.get("islandList"), islandListType),
                    context.deserialize(wrapper.get("myCastle"), ExpertCastleData.class),
                    context.deserialize(wrapper.get("otherCastles"), castleListType),
                    context.deserialize(wrapper.get("turn"), TurnData.class));
        }
        Type characterListType = new TypeToken<List<CharacterData>>() {}.getType();
        Type expertIslandListType = new TypeToken<List<ExpertIslandData>>() {}.getType();
        Type expertCastleListType = new TypeToken<List<ExpertCastleData>>() {}.getType();
        Type cloudListType = new TypeToken<List<CloudData>>() {}.getType();
        return new ExpertBoardData(
                context.deserialize(wrapper.get("username"), String.class),
                context.deserialize(wrapper.get("nPlayer"), Integer.class),
                context.deserialize(wrapper.get("motherNaturePosition"), Integer.class),
                context.deserialize(wrapper.get("cloudList"), cloudListType),
                context.deserialize(wrapper.get("islandList"), expertIslandListType),
                context.deserialize(wrapper.get("myCastle"), ExpertCastleData.class),
                context.deserialize(wrapper.get("otherCastles"), expertCastleListType),
                context.deserialize(wrapper.get("turn"), TurnData.class),
                context.deserialize(wrapper.get("characters"), characterListType),
                context.deserialize(wrapper.get("activeCharacter"), CharacterUtility.class)
                );
    }

    private Type typeForName(final JsonElement typeElem) {
        try {
            return Class.forName(typeElem.getAsString());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }
}
