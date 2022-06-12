package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.communication.message.subclasses.LobbyInfo;
import it.polimi.ingsw.server.controller.GameType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CLITEST {

    @Test
    void LobbyInfoTest() {
        Map<GameType, Set<String>> clientsInLobbyMap = new HashMap<>();
        Map<GameType, Integer> activeGames = new HashMap<>();

        clientsInLobbyMap.put(GameType.EXPERT_2_PLAYER, Set.of("Worenzo"));
        clientsInLobbyMap.put(GameType.NORMAL_4_PLAYER, Set.of("lorenzo", "federico", "giovanni"));
        clientsInLobbyMap.put(GameType.NORMAL_3_PLAYER, Set.of("ingcontri", "asdjnsjdnadjadnjsnajdas", "mario bros"));

        activeGames.put(GameType.NORMAL_3_PLAYER, 5);
        LobbyInfo lobbyInfo = new LobbyInfo(clientsInLobbyMap, activeGames);

        System.out.println(lobbyInfo);
    }
}
