package it.polimi.ingsw.communication;

import com.google.gson.GsonBuilder;
import it.polimi.ingsw.communication.packet.message.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.LobbyInfoMessage;
import it.polimi.ingsw.server.controller.GameType;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GsonTests {

    @Test
    void GsonParseTest() {
        List<String> players = List.of("lorenzo", "giovanni", "federico");
        GameType gameType = GameType.NORMAL_2_PLAYER;
        LobbyInfoMessage lobbyMessage
                = new LobbyInfoMessage(players, gameType);
        Packet packet = new Packet(lobbyMessage, MessageType.LOBBYINFO);
        GsonBuilder gsonBuilder = new GsonBuilder();
        String jsonOut
                = gsonBuilder.setPrettyPrinting().create().toJson(packet, Packet.class);
        System.out.println(jsonOut);
        packet = gsonBuilder.create().fromJson(jsonOut, Packet.class);
        System.out.println(packet.getMessage());
    }
}