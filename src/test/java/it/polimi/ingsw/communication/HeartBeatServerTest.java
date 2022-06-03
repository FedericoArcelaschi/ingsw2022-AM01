package it.polimi.ingsw.communication;

import it.polimi.ingsw.client.communication.ClientMain;
import it.polimi.ingsw.communication.packet.message.Preferences;
import it.polimi.ingsw.server.communication.ServerMain;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class HeartBeatServerTest {

    ServerMain sm;
    ClientMain c1, c2;
    ExecutorService es;

    @Test
    void testRun() throws InterruptedException, IllegalAccessException {
        sm = new ServerMain(12345);
        c1 = new ClientMain("127.0.0.1", 12345, new Preferences("Fede",2,false));
        c2 = new ClientMain("127.0.0.1", 12345, new Preferences("Andrea Albergo",2,false));
        es = Executors.newCachedThreadPool();
        es.submit(sm);
        Thread.sleep(50);
        //c1.connect();
        //c2.connect();
        Thread.sleep(5000);
    }
}