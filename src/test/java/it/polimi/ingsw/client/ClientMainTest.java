package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.CastleData;
import it.polimi.ingsw.communication.modelData.CloudData;
import it.polimi.ingsw.communication.modelData.IslandData;
import it.polimi.ingsw.communication.packet.message.ErrorMessage;
import it.polimi.ingsw.communication.packet.message.Message;
import it.polimi.ingsw.controller.GameType;
import it.polimi.ingsw.controller.ServerMain;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
        c1 = new ClientMain("Fede",2,false, "127.0.0.1", 1234);
        c2 = new ClientMain("Lore",2,false, "127.0.0.1", 1234);
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