package it.polimi.ingsw.communication;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class PreferencesTest {

    @Test
    void testGson(){
        Gson parser = new Gson();
        String json = "{\"username\":\"Fede\",\"nPlayer\":2,\"expertMode\":false}";
        Preferences p1 = new Preferences("Fede",2,false);
        Preferences p2 = parser.fromJson(json, Preferences.class);
        assertEquals(p1,p2);
    }
}