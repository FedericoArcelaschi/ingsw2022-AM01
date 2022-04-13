package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BagTest {
    @Test
    public void testExtract() {
        Bag b = new Bag(24,1);
        int nStudentsBefore = b.remainingStudents();
        b.extract();
        assertEquals(nStudentsBefore-1,b.remainingStudents());
    }
    @Test
    public void testExtractEmpty(){
        Bag b = new Bag(24,1);
        int rs = b.remainingStudents();
        for(int i=0; i<rs; i++) {
            b.extract();
        }
        assertNull(b.extract());
    }
    @Test
    public void testExtractColor(){ //test that the extracted color is decresed
        Bag b = new Bag(24,1);
        Color c = b.extract();
        assertEquals(23, b.getStudents(c));
    }
    @Test
    public void testExtractForSetup(){
        Bag b = new Bag(24,1);
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
        Bag b = new Bag(24,1);
        b.extractForIslandSetup();
        boolean a=true;
        for (Color c : Color.values()) {
            if(b.getStudents(c) != 22) a=false;
        }
        assertTrue(a);
    }
    @Test
    public void testExtractForCastleSetupTotal(){
        Bag b = new Bag(24,1);
        boolean a=true;
        List<Color> colorList = b.multipleExtract(7);
        assertEquals(b.remainingStudents(), 113);
    }
    @Test
    public void testRemainingStudents(){
        Bag b = new Bag(24,1);
        assertEquals(24*Color.values().length,b.remainingStudents());
    }
    @Test
    public void testGetSeed(){
        int seed = 1;
        Bag b = new Bag(24,seed);

        assertEquals(seed,b.getSeed());
    }
}