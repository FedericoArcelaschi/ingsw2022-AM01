package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BagTest {
    private Bag b;

    @BeforeEach
    void setUp(){
        b = new Bag(24, 1);
    }

    @Test
    public void testExtract() {
        int nStudentsBefore = b.remainingStudents();
        b.extract();
        assertEquals(nStudentsBefore-1,b.remainingStudents());
    }
    @Test
    public void testExtractEmpty(){
        int rs = b.remainingStudents();
        for(int i=0; i<rs; i++) {
            b.extract();
        }
        assertNull(b.extract());
    }
    @Test
    public void testExtractColor(){ //test that the extracted color is decresed
        Color c = b.extract();
        assertEquals(23, b.getStudents(c));
    }
    @Test
    public void testExtractForSetup(){
        List<Color> colorList = b.extractForIslandSetup();
        boolean a=true;
        for(int i=0; i<2; i++) {
            for (Color c : Color.values()) {
                if (!colorList.remove(c)) a = false;
            }
        }
        assertTrue(a);
    }
    @Test
    public void testExtractForSetupTotal(){ //check that students extracted are removed from the bag
        b.extractForIslandSetup();
        boolean a=true;
        for (Color c : Color.values()) {
            if(b.getStudents(c) != 22) a=false;
        }
        assertTrue(a);
    }

    @Test
    public void testExtractForCastleSetupTotal(){
        boolean a=true;
        List<Color> colorList = b.multipleExtract(7);
        assertEquals(b.remainingStudents(), 113);
    }

    @Test
    public void testRemainingStudents(){
        assertEquals(120, b.remainingStudents());
        b.multipleExtract(100);
        assertEquals(20, b.remainingStudents());
        b.extract();
        assertEquals(19, b.remainingStudents());
    }

    @Test
    public void testGetSeed(){
        final int seed = 1;
        Bag b1 = new Bag(24, seed);
        Bag b2 = new Bag(24, seed);
        assertEquals(b2.getSeed(),b.getSeed());
        for (int i = 0; i < 120; i++) {
            assertEquals(b1.extract(), b2.extract());
        }
    }
}