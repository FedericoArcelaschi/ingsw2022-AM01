package it.polimi.ingsw.communication;

import com.google.gson.Gson;
import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.server.controller.GameType;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

public class GsonMessageTest {

    @Test
    public void GsonMyLobbyInfo() {
        Map<GameType, Set<String>> clientsInLobbyMap
                =Map.of(GameType.EXPERT_2_PLAYER, Set.of("pippo"),
                        GameType.EXPERT_3_PLAYER, Set.of("lapoElKan"));
        Map<GameType, Integer> activeGames
                =Map.of(GameType.EXPERT_2_PLAYER, 4,
                        GameType.EXPERT_3_PLAYER, 5);
        LobbyInfo lobbyInfo = new LobbyInfo(clientsInLobbyMap, activeGames);

        String json = lobbyInfo.toJson();
        System.out.println(json);
        Message lobbyInfo1 = new Gson().fromJson(json, Message.class);
        System.out.println(lobbyInfo1);
    }

}
