package it.polimi.ingsw.client;

import it.polimi.ingsw.client.communication.ClientMain;
import it.polimi.ingsw.server.controller.GameType;
import it.polimi.ingsw.server.communication.ServerMain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class ServerReceiverMainTest {
    ClientMain c1,c2;
    ServerMain serverMain;

    @BeforeEach
    void beforeEach() throws IllegalAccessException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        serverMain = new ServerMain(1234);
        //c1 = new ClientMain("127.0.0.1", 1234, new Preferences("Fede",2,false));
        //c2 = new ClientMain("127.0.0.1", 1234, new Preferences("Lore",2,false));
        executorService.submit(serverMain);
    }

    @Test
    void testConnect() throws InterruptedException {
        int i;
        int n2 = 100, n3 = 90, n4=100;
        for (i = 1; i <= n2; i++) {
            new ClientMain(null).connect(new InetSocketAddress("127.0.0.1", 1234));
        }
        for (i = 1; i <= n3; i++) {
            //new ClientMain("Lore3-"+i,3,false, "127.0.0.1", 1234).connect();
        }
        for (i = 1; i <= n4; i++) {
            //new ClientMain("Lore4-"+i,4,false, "127.0.0.1", 1234).connect();
        }
        Thread.sleep(1000);
        assertEquals(50, serverMain.getGamesNumber(GameType.NORMAL_2_PLAYER));
        assertEquals(30, serverMain.getGamesNumber(GameType.NORMAL_3_PLAYER));
        assertEquals(25, serverMain.getGamesNumber(GameType.NORMAL_4_PLAYER));
        assertEquals(0, serverMain.getGamesNumber(GameType.EXPERT_2_PLAYER));
        assertEquals(0, serverMain.getGamesNumber(GameType.EXPERT_3_PLAYER));
        assertEquals(0, serverMain.getGamesNumber(GameType.EXPERT_4_PLAYER));
    }

    @Test
    void testExecuteCommand() throws InterruptedException {

        Thread.sleep(100);
        c1.runCommand("playcard 1");

        Thread.sleep(3000);
        c1.runCommand("playcard 1");
        Thread.sleep(3000);
    }



}