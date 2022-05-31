package it.polimi.ingsw.communication;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.communication.serverSide.ServerMain;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class HeartBeatServerTest {

    ServerMain sm;
    ClientMain c1, c2;
    ExecutorService es;

    @Test
    void testRun() throws InterruptedException {
        sm = new ServerMain(12345);
        c1 = new ClientMain("fede", 2, false, "127.0.0.1", 12345);
        c2 = new ClientMain("gio", 2, false, "127.0.0.1", 12345);
        es = Executors.newCachedThreadPool();
        es.submit(sm);
        Thread.sleep(50);
        //c1.connect();
        //c2.connect();
        Thread.sleep(5000);
    }
}