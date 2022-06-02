package it.polimi.ingsw.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.Archipelago;
import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class ArchipelagoTest{
    @Test
    public void testGetIslandNumber() {
        Island i1, i2, i3;
        Island a1, a2, a3;
        i1 = new Island(StudentColor.YELLOW);
        i2 = new Island(StudentColor.YELLOW);
        i3 = new Island(StudentColor.YELLOW);
        a1 = new Archipelago(i1, i2);
        a2 = new Archipelago(i1, i2, i3);
        a3 = new Archipelago(a1, a2);

        assertEquals(2, a1.getIslandNumber());
        assertEquals(3, a2.getIslandNumber());
        assertEquals(5, a3.getIslandNumber());
    }
    @Test
    public void testAddStudents(){
        Island i1, i2, i3;
        Island a1, a2, a3;
        i1 = new Island(StudentColor.YELLOW);
        i2 = new Island(StudentColor.YELLOW);
        i3 = new Island(StudentColor.YELLOW);
        a1 = new Archipelago(i1, i2);
        a2 = new Archipelago(i1, i2, i3);
        a3 = new Archipelago(a1, a2);
        Map<StudentColor, Integer> m1 = new HashMap<>();
        Map<StudentColor, Integer> m2 = new HashMap<>();
        Map<StudentColor, Integer> m3 = new HashMap<>();
        for(StudentColor c: StudentColor.values()){
            m1.put(c,0);
            m2.put(c,0);
            m3.put(c,0);
        }
        m1.put(StudentColor.YELLOW,2);
        m2.put(StudentColor.YELLOW,3);
        m3.put(StudentColor.YELLOW,5);

        assertEquals(m1,a1.getStudents());
        assertEquals(m2,a2.getStudents());
        assertEquals(m3,a3.getStudents());

        a3.addStudent(StudentColor.GREEN);
        a3.addStudent(StudentColor.GREEN);
        m3.put(StudentColor.GREEN, 2);
        assertEquals(m3,a3.getStudents());
    }
}