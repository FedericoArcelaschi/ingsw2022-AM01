package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertCastleTest{
    @Test
    public void testAddStudentDR_testpayCharacter() {
        List<String> players = Arrays.asList("pippo","pluto","paperino");
        Board board = new ExpertBoard("pippo","pluto","paperino");
        ExpertCastle justACastle = (ExpertCastle) board.getCastleMap().get("pippo");

        List<Color> yellows_3 = Arrays.asList(Color.YELLOW,Color.YELLOW,Color.YELLOW);
        assertTrue(justACastle.payCharacter(1));
        assertFalse(justACastle.payCharacter(1));
        justACastle.addStudentDR(yellows_3);
        assertTrue(justACastle.payCharacter(1));
        assertFalse(justACastle.payCharacter(1));
    }
    @Test
    public void testRemoveStudentFromDiningRoom() {
        Bag b = new Bag(24);
        ExpertCastle ec = new ExpertCastle("pippo", Team.WHITE, 2, b.multipleExtract(9));
        ec.addStudentsInDiningRoom(Arrays.asList(Color.YELLOW, Color.YELLOW));
        ec.removeStudentFromDiningRoom(Color.YELLOW);
        int nOfYellows = ec.getDiningRoom().get(Color.YELLOW);
        assertEquals(1, nOfYellows);
    }
    @Test
    //test not enough coins
    public void testFalsePayChar() {
        Bag b = new Bag(24);
        ExpertCastle ec = new ExpertCastle("pippo", Team.WHITE, 2, b.multipleExtract(9));
        assertFalse(ec.payChar(1));
    }
    @Test
    public void testTruePayChar() {
        Bag b = new Bag(24);
        ExpertCastle ec = new ExpertCastle("pippo", Team.WHITE, 2, b.multipleExtract(9));
        for (int i = 0; i < 5; i++) { //add 3 students to give pippo a coin
            ec.addStudentInDiningRoom(Color.YELLOW);
        }
        assertTrue(ec.payChar(1));
    }
    @Test
    public void testAddStudentInDiningRoom() {
        Bag b = new Bag(24);
        ExpertCastle ec = new ExpertCastle("pippo", Team.WHITE, 2, b.multipleExtract(9));
        for (int i = 0; i < 5; i++) {
            ec.addStudentInDiningRoom(Color.YELLOW);
        }
        assertEquals(1, ec.getCoins());
    }
}