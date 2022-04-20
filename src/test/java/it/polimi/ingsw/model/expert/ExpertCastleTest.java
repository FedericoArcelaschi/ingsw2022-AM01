package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpertCastleTest{
    private static final List<String> players = Arrays.asList("pippo","pluto","paperino");
    private static final Turn t = new Turn(players);
    private static ExpertBoard board;
    private static Bag bag;
    private static ExpertCastle expertCastle;

    @BeforeEach
    void setUp() {
        board = new ExpertBoard(players.get(0), players.get(1), players.get(2), t);
        bag = board.getBag();
        expertCastle = (ExpertCastle) board.getCastle(players.get(0));
    }

    @Test
    public void testAddStudentDR_testpayCharacter() throws TooManyStudentsException {
        List<Color> yellows_3 = Arrays.asList(Color.YELLOW, Color.YELLOW, Color.YELLOW);
            //one coin --> first payment okay, second payment failed
        assertTrue(expertCastle.payCharacter(1));
        assertFalse(expertCastle.payCharacter(1));
            //add another coin
        expertCastle.addStudentsInDiningRoom(yellows_3);
            //one coin --> first payment okay, second payment failed
        assertTrue(expertCastle.payCharacter(1));
        assertFalse(expertCastle.payCharacter(1));
    }

    @Test
    public void testRemoveStudentFromDiningRoom() throws TooManyStudentsException, NoSuchStudentException {
        expertCastle.addStudentsInDiningRoom(Arrays.asList(Color.YELLOW, Color.YELLOW));
        expertCastle.removeStudentFromDiningRoom(Color.YELLOW);
        int numberOfYellows = expertCastle.getDiningRoom().get(Color.YELLOW);
        assertEquals(1, numberOfYellows);
    }

    @Test
    //test not enough coins
    public void testFalsePayChar() {
        assertEquals(1, expertCastle.getCoins());
        assertFalse(expertCastle.payCharacter(2));
    }
    @Test
    //test adding coins
    public void testTruePayChar() throws TooManyStudentsException {
        for (int i = 0; i < 3; i++) {                          //add 3 students to give pippo a coin
            expertCastle.addStudentInDiningRoom(Color.YELLOW);
            expertCastle.addStudentInDiningRoom(Color.BLUE);
        }
        assertTrue(expertCastle.payCharacter(3));
    }
    @Test
    public void testAddStudentInDiningRoom() throws TooManyStudentsException {
        for (int i = 0; i < 3; i++) {                          //add 3 students to give pippo a coin
            expertCastle.addStudentInDiningRoom(Color.YELLOW);
        }
        //one at the beginning of the game and 1 coin because of the three students
        assertEquals(2, expertCastle.getCoins());

    }
}