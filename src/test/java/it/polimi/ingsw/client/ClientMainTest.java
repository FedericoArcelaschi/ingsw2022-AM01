package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.ServerMain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class ClientMainTest {
    ClientMain c1,c2;
    ServerMain s;

    @BeforeEach
    void beforeEach(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        s = new ServerMain(1234);
        //c1 = new ClientMain("Fede",2,false, "0.0.0.0", 1234);
        //c2 = new ClientMain("Lore",2,false, "0.0.0.0", 1234);
        executorService.submit(s);
    }

    @Test
    void testConnect() throws InterruptedException {//FIXME: need to synchronize access to connectedPlayers list
        int i;
        for (i = 1; i <= 10; i++) {
            new ClientMain("Lore"+i,2,false, "127.0.0.1", 1234).connect();
        }
        Thread.sleep(1000);
        assertEquals(10, s.getConnectedPlayers().size(), "10 players should be connected");
    }
}