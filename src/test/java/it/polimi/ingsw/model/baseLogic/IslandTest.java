package it.polimi.ingsw.model.baseLogic;

import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.baseLogic.influence.Influence;
import it.polimi.ingsw.model.baseLogic.influence.Professors;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class IslandTest {
    @Test
    public void testAddStudent() {
        Island i = new Island(StudentColor.YELLOW);
        i.addStudent(StudentColor.YELLOW);
        int nYellow = i.getStudents().get(StudentColor.YELLOW);
        assertEquals(2, nYellow);
    }

    @Test
    public void testAddStudentMap() {
        Island i = new Island();
        Map<StudentColor, Integer> students = new HashMap<>();
        for (StudentColor c : StudentColor.values())
            students.put(c, 2);
        i.addStudent(students);
        assertEquals(students, i.getStudents());
    }

    @Test
    public void testCalculateInfluence() {
        Island island = new Island();
        Castle c1 = new Castle(Team.WHITE, 2, new ArrayList<>());
        Castle c2 = new Castle(Team.BLACK, 2, new ArrayList<>());
        Professors professors = new Professors(
                Map.of( "pippo", c1,
                        "pluto", c2));
        Influence influence = new Influence(professors);

        try {
            //4 professors to White Team
            c1.addStudentInDiningRoom(StudentColor.YELLOW);
            c1.addStudentInDiningRoom(StudentColor.GREEN);
            c1.addStudentInDiningRoom(StudentColor.PINK);
            c1.addStudentInDiningRoom(StudentColor.BLUE);
            //1 professor to Black Team
            c2.addStudentInDiningRoom(StudentColor.RED);
        } catch (TooManyStudentsException e) {
            fail();
        }
        professors.updateProfessorsAssigning();

        Map<StudentColor, Integer> students = new HashMap<>();
        for (StudentColor c : StudentColor.values()) {
            students.put(c, 2);  //add 2 student per color
        }
        island.addStudent(students); //put the students on the island

        Map<Team, Integer> influenceMap
                = influence.getInfluenceMap(island);

        Map<Team, Integer> influenceForComparison
                = Map.of(Team.WHITE, 8,
                            Team.BLACK, 2,
                            Team.GREY, 0);

        assertEquals(influenceForComparison, influenceMap);
    }
}