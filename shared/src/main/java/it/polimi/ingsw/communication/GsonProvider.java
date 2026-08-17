package it.polimi.ingsw.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.sql.Timestamp;

/**
 * Shared {@link Gson} instance for the wire protocol.
 * <p>
 * Gson's default {@code Date}/{@code Timestamp} handling formats/parses with the JVM's locale-dependent
 * {@code DateFormat}, which is not stable across JVMs (e.g. the desktop JDK and Android's ART runtime use
 * different ICU data and can disagree on whether "PM" is preceded by a regular or narrow no-break space).
 * A message serialized on one platform can then fail to deserialize on the other, silently killing the
 * receiver thread. Serializing as epoch millis instead avoids any locale/platform dependency.
 */
public final class GsonProvider {

    private static final Gson INSTANCE = new GsonBuilder()
            .registerTypeAdapter(Timestamp.class, (JsonSerializer<Timestamp>) (src, type, context) ->
                    new JsonPrimitive(src.getTime()))
            .registerTypeAdapter(Timestamp.class, (JsonDeserializer<Timestamp>) (json, type, context) ->
                    new Timestamp(json.getAsLong()))
            .create();

    private GsonProvider() {
    }

    public static Gson get() {
        return INSTANCE;
    }
}
