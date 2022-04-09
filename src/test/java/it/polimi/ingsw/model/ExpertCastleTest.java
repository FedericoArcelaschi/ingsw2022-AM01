package it.polimi.ingsw.model;

import junit.framework.TestCase;

import java.util.Arrays;

public class ExpertCastleTest extends TestCase {

    public void testRemoveStudentFromDiningRoom() {
        ExpertCastle ec = new ExpertCastle("pippo", Team.WHITE, 2);
        ec.addStudentsInDiningRoom(Arrays.asList(Color.YELLOW, Color.YELLOW));
        ec.removeStudentFromDiningRoom(Color.YELLOW);
        int nOfYellows = ec.getDiningRoom().get(Color.YELLOW);
        assertEquals(1, nOfYellows);
    }

    //test not enough coins
    public void testFalsePayChar() {
        ExpertCastle ec = new ExpertCastle("pippo", Team.WHITE, 2);
        assertFalse(ec.payChar(1));
    }

    public void testTruePayChar() {
        ExpertCastle ec = new ExpertCastle("pippo", Team.WHITE, 2);
        for (int i = 0; i < 5; i++) { //add 3 students to give pippo a coin
            ec.addStudentInDiningRoom(Color.YELLOW);
        }
        System.out.println(ec.getCoins());
        assertTrue(ec.payChar(1));
    }

    public void testAddStudentInDiningRoom() {
        ExpertCastle ec = new ExpertCastle("pippo", Team.WHITE, 2);
        for (int i = 0; i < 5; i++) {
            ec.addStudentInDiningRoom(Color.YELLOW);
        }
        assertEquals(1, ec.getCoins());
    }
}