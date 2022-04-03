package it.polimi.ingsw.model;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class ArchipelagoTest extends TestCase {

    public void testGetIslandNumber() {
        Island i1, i2, i3;
        Island a1, a2, a3;
        i1 = new Island(Color.YELLOW);
        i2 = new Island(Color.YELLOW);
        i3 = new Island(Color.YELLOW);
        a1 = new Archipelago(i1, i2);
        a2 = new Archipelago(i1, i2, i3);
        a3 = new Archipelago(a1, a2);

        assertEquals(2, a1.getIslandNumber());
        assertEquals(3, a2.getIslandNumber());
        assertEquals(5, a3.getIslandNumber());
    }

    public void testAddStudents(){
        Island i1, i2, i3;
        Island a1, a2, a3;
        i1 = new Island(Color.YELLOW);
        i2 = new Island(Color.YELLOW);
        i3 = new Island(Color.YELLOW);
        a1 = new Archipelago(i1, i2);
        a2 = new Archipelago(i1, i2, i3);
        a3 = new Archipelago(a1, a2);
        Map<Color, Integer> m1 = new HashMap<>();
        Map<Color, Integer> m2 = new HashMap<>();
        Map<Color, Integer> m3 = new HashMap<>();
        for(Color c: Color.values()){
            m1.put(c,0);
            m2.put(c,0);
            m3.put(c,0);
        }
        m1.put(Color.YELLOW,2);
        m2.put(Color.YELLOW,3);
        m3.put(Color.YELLOW,5);

        assertEquals(m1,a1.getStudents());
        assertEquals(m2,a2.getStudents());
        assertEquals(m3,a3.getStudents());

        a3.addStudent(Color.GREEN);
        a3.addStudent(Color.GREEN);
        m3.put(Color.GREEN, 2);
        assertEquals(m3,a3.getStudents());
    }
}