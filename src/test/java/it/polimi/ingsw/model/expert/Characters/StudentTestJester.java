package it.polimi.ingsw.model.expert.Characters;
//TODO: ignore warnings

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertCastle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


/**
 * test for the seventh character.
 */
class StudentTestJester {
    private ExpertBoard board;
    private Generic jester;

    @BeforeEach
    void setUp() {
        Turn t = new Turn(List.of("Lollo99", "FedericaPellegrini"));
        board = new ExpertBoard("Lollo99", "FedericaPellegrini", t);
        if (board.getAvailableCharacterCards().get(7) == null) board.extract4CharacterTesting(7);
        jester = board.getAvailableCharacterCards().get(7);
    }

    @Test
    @SuppressWarnings("unchecked")
    void applyEffectJESTER() throws Exception {
        List<Color> availableStudents
                = ((List<Color>) jester
                .getEffect()
                .get(Parameters.STUDENTLIST));
        assertEquals(6, availableStudents.size(),
                "Jester must contain only 6 students");
        String currentPlayer
                = board.getCurrentPlayer();
        ExpertCastle castle
                = (ExpertCastle) board.getCastleMap()
                .get(currentPlayer);
        List<Color> studentsInWaitingRoom = castle.getWaitingRoom();
        List<Color> studentListForJester
                = new ArrayList<>(Arrays.asList(
                availableStudents.get(0),
                availableStudents.get(1),
                availableStudents.get(2),
                studentsInWaitingRoom.get(0),
                studentsInWaitingRoom.get(1),
                studentsInWaitingRoom.get(2)));
        System.out.println("Jester Effect:\nbefore: availableStudents" + availableStudents);
        System.out.println("before: waitingroom" + studentsInWaitingRoom);

        board.playExpertCard(7, studentListForJester);

        System.out.println("after: availableStudents" + jester
                .getEffect()
                .get(Parameters.STUDENTLIST));
        System.out.println("after: waitingroom" + castle.getWaitingRoom());

        assertEquals(studentsInWaitingRoom.subList(3, 7), castle.getWaitingRoom().subList(0, 4),
                "wrong students remaining");
        assertEquals(availableStudents.subList(0, 3), castle.getWaitingRoom().subList(4, 7),
                "wrong students added");
        availableStudents = (List<Color>) jester
                .getEffect()
                .get(Parameters.STUDENTLIST);
        assertEquals(6, availableStudents.size(),
                "all the students on the card should be back in place");
    }

    @Test
    void applyEffectJesterError1() { //no students list
        Map<Parameters, Object> parametersMap
                = Map.of();
        assertThrowsExactly(IllegalArgumentException.class, () -> jester.applyEffect(parametersMap),
                "IllegalArgumentException not thrown");
        try {
            jester.applyEffect(parametersMap);
        } catch (IllegalArgumentException | StudentException e) {
            assertEquals("no students list", e.getMessage());

        }

    }

    @Test
    void applyEffectJesterError2() { //wrong sized list
        Map<Parameters, Object> parametersMap
                = Map.of(Parameters.STUDENTLIST, List.of(Color.YELLOW));
        assertThrowsExactly(IllegalArgumentException.class, () -> jester.applyEffect(parametersMap),
                "StudentException not thrown");
        try {
            jester.applyEffect(parametersMap);
        } catch (IllegalArgumentException e) {
            assertEquals("should receive a list of 2, 4 or 6 students", e.getMessage());
        } catch (StudentException e) {
            e.printStackTrace();
            fail("wrong exception");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void applyEffectJesterError3() { //Wrong color list (castle)
        List<Color> availableStudents
                = ((List<Color>) jester
                .getEffect()
                .get(Parameters.STUDENTLIST));
        List<Color> notAvailableStudents = availableStudents.subList(0, 3);

        String currentPlayer = "Lollo99";
        ExpertCastle castle = new ExpertCastle(Team.WHITE, 2,
                Arrays.asList(Color.GREEN, Color.GREEN, Color.GREEN));
        notAvailableStudents.addAll(Arrays.asList(Color.PINK, Color.PINK, Color.PINK));

        Map<Parameters, Object> parametersMap
                = Map.of(Parameters.STUDENTLIST, notAvailableStudents,
                Parameters.CASTLEMAP, Map.of(currentPlayer, castle),
                Parameters.PLAYERID, currentPlayer);

        assertThrowsExactly(NoSuchStudentException.class, () -> jester.applyEffect(parametersMap),
                "NoSuchStudentException not thrown");
        try {
            jester.applyEffect(parametersMap);
        } catch (StudentException e) {
            e.printStackTrace();
            assertEquals("Student PINK not in the WaitingRoom", e.getMessage());
        } catch (IllegalArgumentException e) {
            fail("The method shouldn't throw this exception: " + e.getMessage());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void applyEffectJesterError4() { //Wrong color list (jester)
        List<Color> availableStudents
                = ((List<Color>) jester
                .getEffect()
                .get(Parameters.STUDENTLIST));
        assertEquals(6, availableStudents.size(),
                "Jester must contain only 6 students");
        //in maniera stocastica a volte ne ha di più (6, 10, 14, 16, 32, 96)
        String currentPlayer
                = board.getCurrentPlayer();

        List<Color> notAvailableStudents = new ArrayList<>();
        //I add 2 students at the time to skip the "parity check"
        for (Color c : Color.values()) {
            if (!availableStudents.contains(c)) notAvailableStudents.addAll(Arrays.asList(c, c));
        }
        if (notAvailableStudents.isEmpty())
            notAvailableStudents.addAll(
                    Arrays.asList(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE));
        Map<Parameters, Object> parametersMap
                = Map.of(   Parameters.PLAYERID, board.getCurrentPlayer(),
                            Parameters.CASTLEMAP, board.getCastleMap(),
                            Parameters.STUDENTLIST, notAvailableStudents);
        try {
            jester.applyEffect(parametersMap);
        } catch (NoSuchStudentException e) {
            System.out.println("jester doesn't contain a " + e.getColor() + " student");
            assertEquals(notAvailableStudents.get(0), e.getColor(),
                    "jester doesn't contain " + e.getColor() + " student");
        } catch (IllegalArgumentException | TooManyStudentsException e) {
            e.printStackTrace();
            fail("should not arrive here");
        }
    }
}