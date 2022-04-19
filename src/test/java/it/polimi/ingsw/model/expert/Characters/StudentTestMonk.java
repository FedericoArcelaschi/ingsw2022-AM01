package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertCastle;
import it.polimi.ingsw.model.expert.ExpertIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTestMonk {

    private static Turn t;
    private static ExpertBoard board;
    @BeforeEach
    void setUp() {
        t = new Turn(Arrays.asList("Lorenzo", "Giovanni"));
        board = new ExpertBoard("Lorenzo", "Giovanni", t);
    }

    /**
     * Tests adding a YELLOW to the island.
     * Problem: doesn't know for sure if there is a YELLOW in MONK
     */
    @Test
    public void testApplyEffectFromBoard() throws NoSuchStudentException, TooManyStudentsException {
        ExpertIsland testIsland = (ExpertIsland) board.getIslandList().get(1);
        board.extract4CharacterTesting(1);
        int countAll = 0;
        for (Color c: Color.values()){
            countAll +=  testIsland.getStudents().get(c);
        }

        int countYellows =  testIsland.getStudents().get(Color.YELLOW).intValue();
        if(board.playExpertCard(1, testIsland, List.of(Color.YELLOW)))
            assertEquals(countYellows + 1, testIsland.getStudents().get(Color.YELLOW).intValue());
        else
            assertEquals(countYellows, testIsland.getStudents().get(Color.YELLOW).intValue());

        int countGreen =  testIsland.getStudents().get(Color.GREEN).intValue();
        if(board.playExpertCard(1, testIsland, List.of(Color.GREEN)))
            assertEquals(countGreen + 1, testIsland.getStudents().get(Color.GREEN).intValue());
        else
            assertEquals(countGreen, testIsland.getStudents().get(Color.GREEN).intValue());

        int countPink =  testIsland.getStudents().get(Color.PINK).intValue();
        if(board.playExpertCard(1, testIsland, List.of(Color.PINK)))
            assertEquals(countPink + 1, testIsland.getStudents().get(Color.PINK).intValue());
        else
            assertEquals(countPink, testIsland.getStudents().get(Color.PINK).intValue());

        int countBlue =  testIsland.getStudents().get(Color.BLUE).intValue();
        if(board.playExpertCard(1, testIsland, List.of(Color.BLUE)))
            assertEquals(countBlue + 1, testIsland.getStudents().get(Color.BLUE).intValue());
        else
            assertEquals(countBlue, testIsland.getStudents().get(Color.BLUE).intValue());

        int countRed =  testIsland.getStudents().get(Color.RED).intValue();
        if(board.playExpertCard(1, testIsland, List.of(Color.RED)))
            assertEquals(countRed + 1, testIsland.getStudents().get(Color.RED).intValue());
        else
            assertEquals(countRed, testIsland.getStudents().get(Color.RED).intValue());

        int CountAfter = 0;
        for (Color c: Color.values()) {
            CountAfter +=  testIsland.getStudents().get(c);
        }
        assertEquals(countAll + 1, CountAfter,
                "I don't know witch one, but at least one student was added");
    }

    /**
     * Tests adding a YELLOW to the island.
     * Using the new method <code>.GetEffect()</code>
     */
    @Test
    public void testApplyEffectWithGetEffect() throws NoSuchStudentException, TooManyStudentsException {
        ExpertIsland testIsland = (ExpertIsland) board.getIslandList().get(1);
        board.extract4CharacterTesting(1);
        Generic monkCharacter
                = board.getAvailableCharacterCards().get(1);
        ExpertIsland island
                = (ExpertIsland) board.getIslandList().get(0);
        int numberOfStudentsBefore; //can be either zero or one
        int numberOfStudentsAfter; //can be either one or two
        Color availableStudent
                = ((List<Color>) board
                .getAvailableCharacterCards()
                .get(1)
                .getEffect()
                .get(Parameters.STUDENTLIST))
                .get(0);//gets the first student that "the character can place"
        Map<Parameters, Object> parametersMap
                = new HashMap<>(Map.of(
                    Parameters.STUDENTLIST, List.of(availableStudent),
                    Parameters.ISLAND, island));
        numberOfStudentsBefore = island.getStudents().get(availableStudent);
        //In this test I invoke directly the Character's method
        assertTrue(monkCharacter.applyEffect(parametersMap));
        numberOfStudentsAfter = island.getStudents().get(availableStudent);
        assertEquals(numberOfStudentsBefore + 1, numberOfStudentsAfter,
                "The student number of color " + availableStudent + " must be increased (but isn't).");
    }

    @Test
    public void testApplyEffectWithWrongColor() throws NoSuchStudentException, TooManyStudentsException {
        ExpertIsland testIsland = (ExpertIsland) board.getIslandList().get(1);
        board.extract4CharacterTesting(1);
        Generic monkCharacter
                = board.getAvailableCharacterCards().get(1);
        ExpertIsland island
                = (ExpertIsland) board.getIslandList().get(0);

        List<Color> availableStudents
                = ((List<Color>) board
                .getAvailableCharacterCards()
                .get(1)
                .getEffect()
                .get(Parameters.STUDENTLIST)); //gets all the available student that "the character can place"
        Color notAvailableStudent = null;
        for (Color c: Color.values()) {
            if(!availableStudents.contains(c))
                notAvailableStudent = c;}
        if(notAvailableStudent == null) return;
        int numberOfStudentsBefore; //can be either zero or one
        int numberOfStudentsAfter; //can be either one or two
        Map<Parameters, Object> parametersMap
                = new HashMap<>(Map.of(
                Parameters.STUDENTLIST, List.of(notAvailableStudent),
                Parameters.ISLAND, island)
        );
        numberOfStudentsBefore = island.getStudents().get(notAvailableStudent);
        //In this test I call directly the Character's method
        assertFalse(monkCharacter.applyEffect(parametersMap));
        numberOfStudentsAfter = island.getStudents().get(notAvailableStudent);
        assertEquals(numberOfStudentsBefore, numberOfStudentsAfter,
                "The student number of color " + notAvailableStudent + " must be increased.");
    }

    @Test
    public void testPlayExpertCards4MONK() throws NoSuchStudentException, TooManyStudentsException {
        Generic monkChar;
        List<Color> availableStudent;
        ExpertIsland island = ((ExpertIsland) board.getIslandList().get(1));
        Map<Color, Integer> presentStudents = island.getStudents();
        board.extract4CharacterTesting(1);
        monkChar = board.getAvailableCharacterCards().get(1);
        availableStudent = ((List<Color>) monkChar.getEffect().get(Parameters.STUDENTLIST));
        //note: I pass 4 colors and the MONK adds only the first
        //the effect should be applied only for the fist time (1 coin is present)
        assertTrue(board.playExpertCard(1, island, availableStudent), "Assertion playExpertCardFailed #1");
        assertEquals(0, ((ExpertCastle) board.getCastle(board.getCurrentPlayer())).getCoins(), "Error: coins");
        assertFalse(board.playExpertCard(1, island, availableStudent), "Assertion playExpertCardFailed #2");
        assertEquals(0, ((ExpertCastle) board.getCastle(board.getCurrentPlayer())).getCoins(), "Error: coins");
        //check if it works as expected
        for (Color c: presentStudents.keySet()){
            if(c == availableStudent.get(0))
                assertEquals(presentStudents.get(c) + 1 , island.getStudents().get(c), "Assertion color "+ c +" failed");
            else
                assertEquals(presentStudents.get(c), island.getStudents().get(c),"Assertion color "+ c +" failed");
        }
    }
}