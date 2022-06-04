package it.polimi.ingsw.communication.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageParser {
    public static Gson gson = new GsonBuilder().registerTypeAdapter(Message.class,
            new MessageAdapterSerDes())
            //.setPrettyPrinting()//FIXME: warning!! This could cause problems with new lines!
            .create();
}
