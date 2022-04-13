package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ExpertCastleTest{

    private static Turn t;
    private static ExpertBoard board;
    private static List<String> players;
    private static Bag bag;

    @BeforeAll
    static void beforeAll() {
        players = Arrays.asList("pippo","pluto","paperino");
        t = new Turn(players);
        board = new ExpertBoard("pippo","pluto","paperino", t);
        bag = board.getBag();
    }

    @Test
    public void testAddStudentDR_testpayCharacter() throws TooManyStudentsException {
        ExpertCastle justACastle = (ExpertCastle) board.getCastleMap().get("pippo");
        List<Color> yellows_3 = Arrays.asList(Color.YELLOW,Color.YELLOW,Color.YELLOW);
        //one coin --> first payment okay, second payment failed
        assertTrue(justACastle.payCharacter(1));
        assertFalse(justACastle.payCharacter(1));
        //add another coin
        justACastle.addStudentsInDiningRoom(yellows_3);
        //one coin --> first payment okay, second payment failed
        assertTrue(justACastle.payCharacter(1));
        assertFalse(justACastle.payCharacter(1));
    }

    @Test
    public void testRemoveStudentFromDiningRoom() throws TooManyStudentsException, NoSuchStudentException {
        ExpertCastle ec = new ExpertCastle("pippo", Team.WHITE, 2, bag.multipleExtract(9));
        ec.addStudentsInDiningRoom(Arrays.asList(Color.YELLOW, Color.YELLOW));
        ec.removeStudentFromDiningRoom(Color.YELLOW);
        int nOfYellows = ec.getDiningRoom().get(Color.YELLOW);
        assertEquals(1, nOfYellows);
    }

    @Test
    //test not enough coins
    public void testFalsePayChar() {
        ExpertCastle ec = new ExpertCastle("pippo", Team.WHITE, 2, bag.multipleExtract(9));
        assertFalse(ec.payCharacter(1));
    }
    @Test
    public void testTruePayChar() throws TooManyStudentsException {
        ExpertCastle ec = new ExpertCastle("pippo", Team.WHITE, 2, bag.multipleExtract(9));
        for (int i = 0; i < 5; i++) { //add 3 students to give pippo a coin
            ec.addStudentInDiningRoom(Color.YELLOW);
        }
        assertTrue(ec.payCharacter(1));
    }
    @Test
    public void testAddStudentInDiningRoom() throws TooManyStudentsException {
        ExpertCastle ec = new ExpertCastle("pippo", Team.WHITE, 2, bag.multipleExtract(9));
        for (int i = 0; i < 5; i++) {
            ec.addStudentInDiningRoom(Color.YELLOW);
        }
        assertEquals(1, ec.getCoins());
    }
}