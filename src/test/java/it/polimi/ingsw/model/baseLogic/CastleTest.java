package it.polimi.ingsw.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CastleTest {
    @Test
    public void testAddStudentWR() {
        try {
            Bag b = new Bag(24);
            Castle c = new Castle(Team.BLACK, 2, b.multipleExtract(7));
            List<StudentColor> s = new ArrayList<>();
            List<StudentColor> newStudents = new ArrayList<>();
            s.add(c.getWaitingRoom().get(0));
            s.add(c.getWaitingRoom().get(1));
            s.add(c.getWaitingRoom().get(2));
            c.removeStudentsFromWaitingRoom(s);
            newStudents.add(StudentColor.BLUE);
            newStudents.add(StudentColor.GREEN);
            newStudents.add(StudentColor.RED);
            assertTrue(c.addStudentsInWaitingRoom(newStudents));
        }catch (StudentException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testNoSuchStudentException(){
        Bag b = new Bag(24);
        Castle c = new Castle(Team.BLACK, 1, b.multipleExtract(9));
        List<StudentColor> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(StudentColor.YELLOW);
        }
        assertThrows(NoSuchStudentException.class, () -> c.removeStudentsFromWaitingRoom(students),
                "10 yellow students can't be present in the waitingroom. (size=9)");
        // FIXME: org.opentest4j.AssertionFailedError: 10 yellow students can't be present in the waitingroom.
        // (size=9) ==> Unexpected exception charTypes thrown ==>
        // expected: <NoSuchStudentException>
        // but was: <TooManyStudentsException>
    }

    @Test
    public void testTooManyStudentException(){
        Bag b = new Bag(24);
        Castle c = new Castle(Team.BLACK, 2, b.multipleExtract(9));
        //test adding student in a full waiting room
        List<StudentColor> newStudents = new ArrayList<>();
        newStudents.add(StudentColor.BLUE);
        newStudents.add(StudentColor.GREEN);
        newStudents.add(StudentColor.RED);
        assertThrows(TooManyStudentsException.class, () -> c.addStudentsInWaitingRoom(newStudents), "");


    }

    @Test
    public void testAddStudentDR() throws TooManyStudentsException {
        Bag b = new Bag(24);
        Castle c = new Castle(Team.BLACK, 2, b.multipleExtract(9));
        List<StudentColor> s = new ArrayList<>();
        Map<StudentColor, Integer> oldList = c.getDiningRoom();
        s.add(StudentColor.BLUE);
        s.add(StudentColor.GREEN);
        s.add(StudentColor.RED);
        oldList.replace(StudentColor.BLUE, oldList.get(StudentColor.BLUE) + 1);
        oldList.replace(StudentColor.GREEN, oldList.get(StudentColor.GREEN) + 1);
        oldList.replace(StudentColor.RED, oldList.get(StudentColor.RED) + 1);
        c.addStudentsInDiningRoom(s);
        assertEquals(oldList, c.getDiningRoom());
    }
    @Test
    public void testRemoveWR() throws NoSuchStudentException, TooManyStudentsException {
        Bag b = new Bag(24);
        List<StudentColor> students = b.multipleExtract(9);
        Castle c = new Castle(Team.BLACK, 2, students);
        List<StudentColor> rm = new ArrayList<>();

        StudentColor remove = students.get(0);
        students.remove(remove);
        rm.add(remove);
        c.removeStudentsFromWaitingRoom(rm);
        assertEquals(students, c.getWaitingRoom());
    }
    @Test
    public void testPlayCard(){
        Bag b = new Bag(24);
        Castle c = new Castle(Team.BLACK, 2, b.multipleExtract(9));
        assertTrue(c.playCard(3));
        //check priority of last card played
        assertEquals(new Card(3,2,false), c.getLastCardPlayed());
        assertFalse (c.playCard(3));
    }
}
