package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.packet.message.Preferences;
import it.polimi.ingsw.server.controller.GameType;
import it.polimi.ingsw.server.communication.ServerMain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class ClientMainTest {
    ClientMain c1,c2;
    ServerMain s;

    @BeforeEach
    void beforeEach() throws IllegalAccessException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        s = new ServerMain(1234);
        c1 = new ClientMain("127.0.0.1", 1234, new Preferences("Fede",2,false));
        c2 = new ClientMain("127.0.0.1", 1234, new Preferences("Lore",2,false));
        executorService.submit(s);
    }

    @Test
    void testConnect() throws InterruptedException {//FIXME: need to synchronize access to connectedPlayers list
        int i;
        int n2 = 100, n3 = 90, n4=100;
        for (i = 1; i <= n2; i++) {
            //new ClientMain("Lore2-"+i,2,false, "127.0.0.1", 1234).connect();
        }
        for (i = 1; i <= n3; i++) {
            //new ClientMain("Lore3-"+i,3,false, "127.0.0.1", 1234).connect();
        }
        for (i = 1; i <= n4; i++) {
            //new ClientMain("Lore4-"+i,4,false, "127.0.0.1", 1234).connect();
        }
        Thread.sleep(1000);
        assertEquals(n2+n3+n4, s.getConnectedPlayers().size(), "n players should be connected");
        assertEquals(50, s.getGamesNumber(GameType.NORMAL_2_PLAYER));
        assertEquals(30, s.getGamesNumber(GameType.NORMAL_3_PLAYER));
        assertEquals(25, s.getGamesNumber(GameType.NORMAL_4_PLAYER));
        assertEquals(0, s.getGamesNumber(GameType.EXPERT_2_PLAYER));
        assertEquals(0, s.getGamesNumber(GameType.EXPERT_3_PLAYER));
        assertEquals(0, s.getGamesNumber(GameType.EXPERT_4_PLAYER));
    }

    @Test
    void testExecuteCommand() throws InterruptedException {
        //c1.connect();
        //c2.connect();

        Thread.sleep(100);
        c1.runCommand("playcard 1");

        Thread.sleep(3000);
        c1.runCommand("playcard 1");
        Thread.sleep(3000);
    }



}