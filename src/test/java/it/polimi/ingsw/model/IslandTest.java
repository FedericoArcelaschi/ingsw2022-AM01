package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IslandTest{
    @Test
    public void testAddStudent() {
        Island i = new Island(Color.YELLOW);
        i.addStudent(Color.YELLOW);
        int nYellow = i.getStudents().get(Color.YELLOW);
        assertEquals(2,nYellow);
    }
    @Test
    public void testAddStudentMap() {
        Island i = new Island();
        Map<Color, Integer> students= new HashMap<>();
        for(Color c : Color.values())
            students.put(c, 2);
        i.addStudent(students);
        assertEquals(students,i.getStudents());
    }
    @Test
    public void testCalculateInfluence(){
        Island i = new Island();
        Map<Color, Castle> professors = new HashMap<>();
        Map<Team, Integer> influence = new HashMap<>();
        Map<Color, Integer> students = new HashMap<>();

        Castle c1 = new Castle("a", Team.WHITE, 2, new ArrayList<>());
        Castle c2 = new Castle("b", Team.BLACK, 2, new ArrayList<>());

        for(Color c : Color.values()){
            professors.put(c,c1);
            } //aggiungo i professori al bianco

        professors.put(Color.BLUE,c2); //aggiungo i professori al blu

        for(Color c : Color.values()){
            students.put(c,2);  //AGGIUNGO 2 STUDENTI PER COLORE ALL'ISOLA
        }
        i.addStudent(students); //aggiungo gli studenti all'isola al blu

        influence.put(Team.WHITE, 8);
        influence.put(Team.BLACK, 2);
        influence.put(Team.GREY, 0);

        assertEquals(influence, i.calculateInfluence(professors));
    }
}