package it.polimi.ingsw.model.baseLogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CloudTest{
    @Test
    public void testRefill() {
        Bag b = new Bag(24,1);
        Cloud c = new Cloud(b,4);
        c.choose();
        assertTrue(c.refill());
    }
    @Test
    public void testChoose() {
        Bag b = new Bag(24,1);
        Cloud c = new Cloud(b,4);
        assertNotNull(c.choose());
    }
    @Test
    public void testConsecutiveChoose() {
        Bag b = new Bag(24,1);
        Cloud c = new Cloud(b,4);
        c.choose();
        assertNull(c.choose());
    }
}