package it.polimi.ingsw.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientMainTest {
    ClientMain c1,c2;

    @BeforeEach
    void beforeEach(){
        c1 = new ClientMain("Fede",2,false, "0.0.0.0", 1234);
        c2 = new ClientMain("Lore",2,false, "0.0.0.0", 1234);
    }

    @Test
    void testConnect() {
        c1.connect();
        c2.connect();
        c1.runCommand("playcard 1");
    }
}