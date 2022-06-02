package it.polimi.ingsw.communication;

import com.google.gson.GsonBuilder;
import it.polimi.ingsw.communication.packet.message.MessageType;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.message.LobbyInfo;
import it.polimi.ingsw.server.controller.GameType;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GsonTests {

    @Test
    void GsonParseTest() {
        System.out.println(MessageType.PREFERENCES.getTypeSerializable());
    }
}