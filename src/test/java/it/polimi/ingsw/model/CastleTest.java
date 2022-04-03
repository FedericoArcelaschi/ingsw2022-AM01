package it.polimi.ingsw.model;

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
        Map<Color, Integer> oldList = new HashMap<>(c.getDiningRoom());
        oldList.put(Color.BLUE, oldList.get(Color.BLUE) + 1);
        oldList.put(Color.GREEN, oldList.get(Color.GREEN) + 1);
        oldList.put(Color.RED, oldList.get(Color.RED) + 1);
        c.addStudentDR(s);
        assertEquals(oldList, c.getDiningRoom());
    }
}
