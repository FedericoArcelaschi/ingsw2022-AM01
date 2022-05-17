package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.influence.Influence;
import it.polimi.ingsw.model.influence.Professors;
import it.polimi.ingsw.model.influence.functionalInterfaces.InfluenceComputing;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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

        Castle c1 = new Castle(Team.WHITE, 2, new ArrayList<>());
        Castle c2 = new Castle(Team.BLACK, 2, new ArrayList<>());

        Influence influence = new Influence(new Professors(Map.of("gio", c1,"faderwcio", c2)));

        try {
            for (Color c : Color.values()) {
                c1.addStudentInDiningRoom(c);
            } //add professors to team white
        }
        catch (Exception e){
            fail();
        }

        try {
            c2.addStudentInDiningRoom(Color.BLUE);
            c2.addStudentInDiningRoom(Color.BLUE);
            c2.addStudentInDiningRoom(Color.BLUE);
        } catch (TooManyStudentsException e) {
            fail();
        }

        Map<Color, Integer> students = new HashMap<>();
        for (Color c : Color.values()) {
            students.put(c, 2);  //add 2 student per color
        }
        i.addStudent(students); //put the students on the island

        Map<Team, Integer> fakeInfluence = new HashMap<>();
        fakeInfluence.put(Team.WHITE, 8);
        fakeInfluence.put(Team.BLACK, 2);
        fakeInfluence.put(Team.GREY, 0);
        assertEquals(fakeInfluence, influence.getInfluenceMap(i));
    }
}