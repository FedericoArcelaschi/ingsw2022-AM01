package it.polimi.ingsw.model.expert.characters;

import it.polimi.ingsw.model.BoardFactory;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertCastle;
import it.polimi.ingsw.model.expert.ExpertIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test for the first character.
 */
public class StudentTestMonk {

    private static Turn t;
    private static ExpertBoard board;

    @BeforeEach
    void setUp() {
        t = new Turn(Arrays.asList("Lorenzo", "Giovanni"));
        board = (ExpertBoard) BoardFactory.getBoard(Arrays.asList("Lorenzo", "Giovanni"), true, t);
    }

    /**
     * Tests adding a student to the island.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testApplyEffectWithGetEffect() throws StudentException {
        board.extract4CharacterTesting(1);
        MasterCharacter monkCharacter
                = board.getAvailableCharacterCards().get(1);
        ExpertIsland island
                = (ExpertIsland) board.getIslandList().get(3);

        int numberOfStudentsBefore; //can be either zero or one
        int numberOfStudentsAfter; //can be either one or two

        List<Color> studentsOnIsland = new ArrayList<>();
        for (Color student : Color.values()) {
            if (island.getStudents().get(student) != 0)
                studentsOnIsland.add(student);
        }

        List<Color> availableStudent
                = (List<Color>) board
                .getAvailableCharacterCards()
                .get(1)
                .getEffect()
                .get(PossibleParameter.STUDENTLIST);//gets the students that "the character can place"
        Map<PossibleParameter, Object> parametersMap
                = new HashMap<>(Map.of(
                PossibleParameter.STUDENTLIST, availableStudent,
                PossibleParameter.ISLAND, island));

        numberOfStudentsBefore = island.getStudents().get(availableStudent.get(0));
        studentsOnIsland.add(availableStudent.get(0));
        //In this test I invoke directly the Character's method
        monkCharacter.applyEffect(parametersMap);
        numberOfStudentsAfter = island.getStudents().get(availableStudent.get(0));
        assertEquals(numberOfStudentsBefore + 1, numberOfStudentsAfter,
                "The student number of color " + availableStudent + " must be increased");
        for (Color c : Color.values()) {
            for (int i = 0; i < island.getStudents().get(c); i++) {
                studentsOnIsland.remove(c);
            }
        }
        assertTrue(studentsOnIsland.isEmpty(),
                "the expected students on island are the old ones & the one added.");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testApplyEffectWithWrongColor() {
        ExpertIsland testIsland = (ExpertIsland) board.getIslandList().get(1);
        board.extract4CharacterTesting(1);
        MasterCharacter monkCharacter
                = board.getAvailableCharacterCards().get(1);
        ExpertIsland island
                = (ExpertIsland) board.getIslandList().get(0);

        List<Color> availableStudents
                = ((List<Color>) board
                .getAvailableCharacterCards()
                .get(1)
                .getEffect()
                .get(PossibleParameter.STUDENTLIST)); //gets all the available student that "the character can place"
        Color notAvailableStudent = null;
        for (Color c: Color.values()) {
            if(!availableStudents.contains(c))
                notAvailableStudent = c;}
        if (notAvailableStudent == null) return;
        int numberOfStudentsBefore; //can be either zero or one
        int numberOfStudentsAfter; //can be either one or two
        Map<PossibleParameter, Object> parametersMap
                = new HashMap<>(Map.of(
                PossibleParameter.STUDENTLIST, List.of(notAvailableStudent),
                PossibleParameter.ISLAND, island)
        );
        numberOfStudentsBefore = island.getStudents().get(notAvailableStudent);
        //In this test I call directly the Character's method
        assertThrowsExactly(NoSuchStudentException.class, () -> monkCharacter.applyEffect(parametersMap),
                "with the wrong colors no student is moved");

        numberOfStudentsAfter = island.getStudents().get(notAvailableStudent);
        assertEquals(numberOfStudentsBefore, numberOfStudentsAfter,
                "The student number of color " + numberOfStudentsAfter + " must not be increased.");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPlayExpertCards4MONK() throws Exception {
        MasterCharacter monkChar;
        List<Color> availableStudent;
        ExpertIsland island = ((ExpertIsland) board.getIslandList().get(1));
        Map<Color, Integer> presentStudents = island.getStudents();
        board.extract4CharacterTesting(1);
        monkChar = board.getAvailableCharacterCards().get(1);
        availableStudent = ((List<Color>) monkChar.getEffect().get(PossibleParameter.STUDENTLIST));

        //the effect should be applied only for the fist time (1 coin is present)
                                                                //>passes only one student as list.
        board.playExpertCard(1, 1, availableStudent.subList(0, 1));

        assertEquals(0, ((ExpertCastle) board.getCastle(board.getCurrentPlayer())).getCoins(), "Error: coins");

        assertThrows(Exception.class, () -> board.playExpertCard(1, 1, availableStudent),
                "Monk was played once and now the player is out of money.");

        assertEquals(0, ((ExpertCastle) board.getCastle(board.getCurrentPlayer())).getCoins(), "Error: coins");

        //check if it works as expected
        for (Color c : presentStudents.keySet()) {
            if (c == availableStudent.get(0))
                assertEquals(presentStudents.get(c) + 1, island.getStudents().get(c), "Assertion color " + c + " failed");
            else
                assertEquals(presentStudents.get(c), island.getStudents().get(c), "Assertion color " + c + " failed");
        }

    }
}