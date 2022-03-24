package it.polimi.ingsw.model;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class IslandTest extends TestCase {

    public void testAddStudent() {
        Island i = new Island(Color.YELLOW);
        i.addStudent(Color.YELLOW);
        int nYellow = i.getStudents().get(Color.YELLOW);
        assertEquals(2,nYellow);
    }

    public void testAddStudentMap() {
        Island i = new Island();
        Map<Color, Integer> students= new HashMap<>();
        for(Color c : Color.values())
            students.put(c, 2);
        i.addStudent(students);
        assertEquals(students,i.getStudents());
    }

    public void testCalculateInfluence(){
        Island i = new Island();
        Map<Color, Castle> professors = new HashMap<>();
        Map<Team, Integer> influence = new HashMap<>();
        Map<Color, Integer> students = new HashMap<>();

        Castle c1 = new Castle("a", Team.WHITE);
        Castle c2 = new Castle("b", Team.BLACK);

        for(Color c : Color.values()){
            professors.put(c,c1);
            students.put(c,2);
        }

        i.addStudent(students);
        professors.put(Color.BLUE,c2);

        influence.put(Team.WHITE, 8);
        influence.put(Team.BLACK, 2);
        influence.put(Team.GREY, 0);

        assertEquals(influence, i.calculateInfluence(professors));
    }
}