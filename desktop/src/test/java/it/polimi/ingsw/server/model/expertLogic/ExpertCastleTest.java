package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.Bag;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.exceptions.CoinException;
import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpertCastleTest{
    private static final List<String> players = Arrays.asList("pippo","pluto","paperino");
    private static final Turn t = new Turn(players);


    @Test
    public void testAddStudentDR_testpayCharacter() throws TooManyStudentsException {

        ExpertCastle expertCastle = new ExpertCastle(Team.WHITE, 3, Bag.extractMany(9));

        List<StudentColor> yellows_3 = Arrays.asList(StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.YELLOW);
            //one coin --> first payment okay, second payment failed
        assertEquals(1, expertCastle.getCoins(),
                "There is a coin in the castle");
        try {
            expertCastle.payCharacter(1);
        } catch (CoinException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0, expertCastle.getCoins(),
                "There are no more coins in the castle");
            //add another coin
        try {
            expertCastle.addStudentsInDiningRoom(yellows_3);
        } catch (it.polimi.ingsw.server.model.exceptions.TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        //one coin --> first payment okay, second payment failed
        assertEquals(1, expertCastle.getCoins(),
                "There is a coin in the castle");
        try {
            expertCastle.payCharacter(1);
        } catch (CoinException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0, expertCastle.getCoins(),
                "There are no more coins in the castle");
    }

    @Test
    //test not enough coins
    public void testFalsePayChar() {
        ExpertCastle expertCastle = new ExpertCastle(Team.WHITE, 3, Bag.extractMany(9));
        assertEquals(1, expertCastle.getCoins());
        assertEquals("You had only 1 coins, while 2 coins were needed.",
                assertThrowsExactly(CoinException.class,()->expertCastle.payCharacter(2)).getMessage()
                );
    }
    @Test
    //test adding coins
    public void testTruePayChar() throws TooManyStudentsException {
        ExpertCastle expertCastle = new ExpertCastle(Team.WHITE, 3, Bag.extractMany(9));
        for (int i = 0; i < 3; i++) {                          //add 3 students to give pippo a coin
            try {
                expertCastle.addStudentInDiningRoom(StudentColor.YELLOW);
            } catch (it.polimi.ingsw.server.model.exceptions.TooManyStudentsException e) {
                throw new RuntimeException(e);
            }
            try {
                expertCastle.addStudentInDiningRoom(StudentColor.BLUE);
            } catch (it.polimi.ingsw.server.model.exceptions.TooManyStudentsException e) {
                throw new RuntimeException(e);
            }
        }
        assertEquals(3, expertCastle.getCoins());
    }
    @Test
    public void testAddStudentInDiningRoom() throws TooManyStudentsException {
        ExpertCastle expertCastle = new ExpertCastle(Team.WHITE, 3, Bag.extractMany(9));
        for (int i = 0; i < 3; i++) {                          //add 3 students to give pippo a coin
            try {
                expertCastle.addStudentInDiningRoom(StudentColor.YELLOW);
            } catch (it.polimi.ingsw.server.model.exceptions.TooManyStudentsException e) {
                throw new RuntimeException(e);
            }
        }
        //one at the beginning of the game and 1 coin because of the three students
        assertEquals(2, expertCastle.getCoins());

    }
}