package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.expert.Characters.Generic;
import it.polimi.ingsw.model.expert.Characters.Parameters;
import it.polimi.ingsw.model.expert.Characters.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class ExpertBoardTest{
    private static ExpertBoard board;

    @BeforeAll
    public static void setUp() throws Exception {
        Turn t = new Turn(Arrays.asList("Lorenzo", "Federico", "Giovanni"));
        board = new ExpertBoard("Lorenzo", "Federico", "Giovanni", t);
    }

    public void testPlayExpertCard() {
    }

    @Test
    public void testSetup4CharacterTesting() {
        board.setup4CharacterTesting(1);
        assertTrue(board.getAvailableCharacterCards()
                        .contains(
                        new Student(1, board.getBag())
                        ));
    }

    @Test
    public void testPlayExpertCards4MONK() {
        Generic monkChar;
        List<Color> availableStudent;
        ExpertIsland island = ((ExpertIsland) board.getIslandList().get(1));
        Map<Color, Integer> presentStudents = island.getStudents();
        board.setup4CharacterTesting(1);
        monkChar = board.getAvailableCharacterCards().get(1);
        availableStudent = ((List<Color>) monkChar.getEffect().get(Parameters.STUDENTLIST));
        //note: I pass 4 colors and the MONK adds only the first
        //the effect should be applied only for the fist time (1 coin is present)
        assertTrue(board.playExpertCard(1, island, 0, availableStudent), "Assertion playExpertCardFailed #1");
        assertEquals(0, ((ExpertCastle) board.getCastle(board.getTurn())).getCoins(), "Error: coins");
        assertFalse(board.playExpertCard(1, island, 0, availableStudent), "Assertion playExpertCardFailed #2");
        assertEquals(0, ((ExpertCastle) board.getCastle(board.getTurn())).getCoins(), "Error: coins");
        //check if it works as expected
        for (Color c: presentStudents.keySet()){
            if(c == availableStudent.get(0))
                assertEquals(presentStudents.get(c) + 1 , island.getStudents().get(c), "Assertion color "+ c +" failed");
            else
                assertEquals(presentStudents.get(c), island.getStudents().get(c),"Assertion color "+ c +" failed");
        }
    }

}