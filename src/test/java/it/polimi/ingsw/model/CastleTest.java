package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CastleTest{
    @Test
    public void testAddStudentWR() throws TooManyStudentsException, NoSuchStudentException {
        Bag b = new Bag(24);
        Castle c = new Castle("Lorenzo", Team.BLACK, 2, b.multipleExtract(9));
        List<Color> s = new ArrayList<>();
        List<Color> newStudents = new ArrayList<>();
        s.add(c.getWaitingRoom().get(0));
        s.add(c.getWaitingRoom().get(1));
        s.add(c.getWaitingRoom().get(2));
        c.removeStudentsFromWaitingRoom(s);
        newStudents.add(Color.BLUE);
        newStudents.add(Color.GREEN);
        newStudents.add(Color.RED);
        assertTrue(c.addStudentsInWaitingRoom(newStudents));
    }

    @Test
    public void testNoSuchStudentException(){
        Bag b = new Bag(24);
        Castle c = new Castle("Lorenzo", Team.BLACK, 1, b.multipleExtract(9));
        List<Color> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(Color.YELLOW);
        }
        assertThrows(NoSuchStudentException.class, () -> c.removeStudentsFromWaitingRoom(students), "");
    }

    @Test
    public void testTooManyStudentException(){
        Bag b = new Bag(24);
        Castle c = new Castle("Lorenzo", Team.BLACK, 2, b.multipleExtract(9));
        //test adding student in a full waiting room
        List<Color> newStudents = new ArrayList<>();
        newStudents.add(Color.BLUE);
        newStudents.add(Color.GREEN);
        newStudents.add(Color.RED);
        assertThrows(TooManyStudentsException.class, () -> c.addStudentsInWaitingRoom(newStudents), "");
    }

    @Test
    public void testAddStudentDR(){
        Bag b = new Bag(24);
        Castle c = new Castle("Lorenzo", Team.BLACK, 2, b.multipleExtract(9));
        List<Color> s = new ArrayList<>();
        Map<Color, Integer> oldList = c.getDiningRoom();
        s.add(Color.BLUE);
        s.add(Color.GREEN);
        s.add(Color.RED);
        oldList.replace(Color.BLUE, oldList.get(Color.BLUE) + 1);
        oldList.replace(Color.GREEN, oldList.get(Color.GREEN) + 1);
        oldList.replace(Color.RED, oldList.get(Color.RED) + 1);
        c.addStudentsInDiningRoom(s);
        assertEquals(oldList, c.getDiningRoom());
    }
    @Test
    public void testRemoveWR() throws NoSuchStudentException {
        Bag b = new Bag(24);
        List<Color> students = b.multipleExtract(9);
        Castle c = new Castle("Lorenzo", Team.BLACK, 2, students);
        List<Color> rm = new ArrayList<>();

        Color remove = students.get(0);
        students.remove(remove);
        rm.add(remove);
        c.removeStudentsFromWaitingRoom(rm);
        assertEquals(students, c.getWaitingRoom());
    }
    @Test
    public void testPlayCard(){
        Bag b = new Bag(24);
        Castle c = new Castle("Lorenzo", Team.BLACK, 2, b.multipleExtract(9));
        assertTrue(c.playCard(3));
        assertEquals(c.getCards().get(3-1), c.getLastCardPlayed());
        assertFalse(c.playCard(3));
    }
}
