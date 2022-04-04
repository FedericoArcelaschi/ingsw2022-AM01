package it.polimi.ingsw.model;

import junit.framework.TestCase;

import java.util.List;

public class BagTest extends TestCase {

    public void testExtract() {
        Bag b = new Bag(24,1);
        int nStudentsBefore = b.remainingStudents();
        b.extract();
        assertEquals(nStudentsBefore-1,b.remainingStudents());
    }

    public void testExtractEmpty(){
        Bag b = new Bag(24,1);
        int rs = b.remainingStudents();
        for(int i=0; i<rs; i++) {
            b.extract();
        }
        assertNull(b.extract());
    }

    public void testExtractColor(){ //test that the extracted color is decresed
        Bag b = new Bag(24,1);
        Color c = b.extract();
        assertEquals(23, b.getStudents(c));
    }

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

    public void testExtractForSetupTotal(){ //check that students extracted are removed from the bag
        Bag b = new Bag(24,1);
        b.extractForIslandSetup();
        boolean a=true;
        for (Color c : Color.values()) {
            if(b.getStudents(c) != 22) a=false;
        }
        assertTrue(a);
    }

    public void testRemainingStudents(){
        Bag b = new Bag(24,1);
        assertEquals(24*Color.values().length,b.remainingStudents());
    }
}