package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.Bag;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BagTest {

    @Test
    public void testExtract() {
        Bag b = new Bag(24, 1);
        int nStudentsBefore = b.remainingStudents();
        b.extract();
        assertEquals(nStudentsBefore-1,b.remainingStudents());
    }
    @Test
    public void testExtractEmpty(){
        Bag b = new Bag(24, 1);
        int remainingStudents = b.remainingStudents();
        for(int i=0; i < remainingStudents; i++) {
            b.extract();
        }
        assertNull(b.extract());

    }
    @Test
    public void testExtractColor() { //test that the extracted color is decreased
        Bag b = new Bag(24, 1);
        StudentColor c = b.extract();
        assertEquals(23, b.getStudents(c));
    }
    @Test
    public void testExtractForSetup() {
        Bag b = new Bag(24, 1);
        List<StudentColor> studentColorList = b.extractForIslandSetup();
        boolean a=true;
        for(int i=0; i<2; i++) {
            for (StudentColor c : StudentColor.values()) {
                if (!studentColorList.remove(c)) a = false;
            }
        }
        assertTrue(a);
    }
    @Test
    public void testExtractForSetupTotal() { //check that students extracted are removed from the bag
        Bag b = new Bag(24, 1);
        b.extractForIslandSetup();
        boolean a=true;
        for (StudentColor c : StudentColor.values()) {
            if(b.getStudents(c) != 22) a=false;
        }
        assertTrue(a);
    }

    @Test
    public void testExtractForCastleSetupTotal() {
        Bag b = new Bag(24, 1);
        boolean a=true;
        List<StudentColor> studentColorList = b.multipleExtract(7);
        assertEquals(b.remainingStudents(), 113);
    }

    @Test
    public void testRemainingStudents() {
        Bag b = new Bag(24, 1);
        assertEquals(120, b.remainingStudents());
        b.multipleExtract(100);
        assertEquals(20, b.remainingStudents());
        b.extract();
        assertEquals(19, b.remainingStudents());
    }

    @Test
    public void testGetSeed() {
        Bag b = new Bag(24, 1);
        final int seed = 1;
        Bag b1 = new Bag(24, seed);
        Bag b2 = new Bag(24, seed);
        assertEquals(b2.getSeed(),b.getSeed());
        for (int i = 0; i < 120; i++) {
            assertEquals(b1.extract(), b2.extract());
        }
    }
}