package it.polimi.ingsw.communication.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PacketParser {
    public static Gson gson = new GsonBuilder().registerTypeAdapter(Packet.class, new PacketAdapterSerDes()).create();
}
