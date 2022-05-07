package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IslandTest {
    @Test
    public void testAddStudent() {
        Island i = new Island(Color.YELLOW);
        i.addStudent(Color.YELLOW);
        int nYellow = i.getStudents().get(Color.YELLOW);
        assertEquals(2, nYellow);
    }

    @Test
    public void testAddStudentMap() {
        Island i = new Island();
        Map<Color, Integer> students = new HashMap<>();
        for (Color c : Color.values())
            students.put(c, 2);
        i.addStudent(students);
        assertEquals(students, i.getStudents());
    }

    @Test
    public void testCalculateInfluence() {
        Island i = new Island();
        Map<Color, Team> professors = new HashMap<>();
        Map<Team, Integer> influence = new HashMap<>();
        Map<Color, Integer> students = new HashMap<>();

        Castle c1 = new Castle(Team.WHITE, 2, new ArrayList<>());
        Castle c2 = new Castle(Team.BLACK, 2, new ArrayList<>());

        for (Color c : Color.values()) {
            professors.put(c, c1.getTeam());
        } //add professors to team white

        professors.put(Color.BLUE, c2.getTeam()); //add professor to team black

        for (Color c : Color.values()) {
            students.put(c, 2);  //add 2 student per color
        }
        i.addStudent(students); //put the students on the island

        influence.put(Team.WHITE, 8);
        influence.put(Team.BLACK, 2);
        influence.put(Team.GREY, 0);
        Map<Team, Integer> influence2 = computeInfluenceMap(i, professors, null);
        assertEquals(influence, influence2);
    }
}