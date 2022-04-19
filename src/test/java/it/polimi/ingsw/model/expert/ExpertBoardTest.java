package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.Characters.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class ExpertBoardTest{
    private static ExpertBoard board;

    @BeforeAll
    public static void setUp() throws Exception {
        Turn t = new Turn(Arrays.asList("Lorenzo", "Federico", "Giovanni"));
        board = new ExpertBoard("Lorenzo", "Federico", "Giovanni", t);
    }

    @Test
    public void testSetup4CharacterTesting() {
        board.extract4CharacterTesting(1);
        assertTrue(board.getAvailableCharacterCards()
                        .contains(
                        new Student(1, board.getBag())
                        ));
    }

    /**
     * Tries the easiest implementation of the method - Deeper testing in each Character test class
     * @throws NoSuchStudentException
     * @throws TooManyStudentsException
     */
    @Test
    public void testPlayExpertCard() throws NoSuchStudentException, TooManyStudentsException, NotYourTurnException {
        board.playCard("Lorenzo", 1);
        if(board.getAvailableCharacterCards().get(4) == null) {
            assertFalse(board.playExpertCard(4));
            board.extract4CharacterTesting(4);
            assertTrue(board.playExpertCard(4));
        }else
            assertTrue(board.playExpertCard(4));

    }


}