package it.polimi.ingsw.model;

import junit.framework.TestCase;

public class CloudTest extends TestCase {

    public void testRefill() {
        Bag b = new Bag(24,1);
        Cloud c = new Cloud(b,4);
        c.choose();
        assertTrue(c.refill());
    }

    public void testChoose() {
        Bag b = new Bag(24,1);
        Cloud c = new Cloud(b,4);
        assertNotNull(c.choose());
    }

    public void testConsecutiveChoose() {
        Bag b = new Bag(24,1);
        Cloud c = new Cloud(b,4);
        c.choose();
        assertNull(c.choose());
    }
}