package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CastleTest extends TestCase{

    public void testAddStudentWR(){
        Castle c = new Castle("Lorenzo", Team.BLACK, 1);
        List<Color> s = new ArrayList<>();
        s.add(Color.BLUE);
        s.add(Color.GREEN);
        s.add(Color.RED);
        List<Color> oldList = new ArrayList<>(c.getWaitingRoom());
        oldList.add(Color.BLUE);
        oldList.add(Color.GREEN);
        oldList.add(Color.RED);
        c.addStudentsInWaitingRoom(s);
        assertEquals(oldList, c.getWaitingRoom());
    }

    public void testAddStudentDR(){
        Castle c = new Castle("Lorenzo", Team.BLACK, 1);
        List<Color> s = new ArrayList<>();
        s.add(Color.BLUE);
        s.add(Color.GREEN);
        s.add(Color.RED);
        Map<Color, Integer> oldList = new HashMap<>(c.getDiningRoom());
        oldList.put(Color.BLUE, oldList.get(Color.BLUE) + 1);
        oldList.put(Color.GREEN, oldList.get(Color.GREEN) + 1);
        oldList.put(Color.RED, oldList.get(Color.RED) + 1);
        c.addStudentsInDiningRoom(s);
        assertEquals(oldList, c.getDiningRoom());
    }

    public void testRemoveWR() throws NoSuchStudentException {
        Castle c = new Castle("Lorenzo", Team.BLACK, 1);
        List<Color> s = new ArrayList<>();
        s.add(Color.BLUE);
        s.add(Color.GREEN);
        s.add(Color.RED);
        c.addStudentsInWaitingRoom(s);
        List<Color> rm = new ArrayList<>();
        List<Color> eq = new ArrayList<>();
        eq.add(Color.BLUE);
        eq.add(Color.RED);
        rm.add(Color.GREEN);
        c.removeStudentsFromWaitingRoom(rm);
        assertEquals(eq, c.getWaitingRoom());
    }

    public void testPlayCard(){
        Castle c = new Castle("Lorenzo", Team.BLACK, 1);
        assertTrue(c.playCard(3));
        assertEquals(c.getCards().get(3-1), c.getLastCardPlayed());
        assertFalse(c.playCard(3));
    }
}
