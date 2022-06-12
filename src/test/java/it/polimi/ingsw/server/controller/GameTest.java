package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.communication.HeartBeatServer;
import it.polimi.ingsw.server.communication.ServerReceiver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @BeforeEach
    void setUp() {
        HeartBeatServer heartBeatServer = new HeartBeatServer();
        List<ServerReceiver> receivers = new ArrayList<>();
    }

    @Test
    public void sendAllUpdateTest() {

    }

    @Test
    public void payCharCommandTest() {

    }
}