package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class CastleTest {

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

    @BeforeEach
    void setup() {
        Bag b = new Bag(10, 1);
        Castle castle = new Castle(Team.WHITE, 1, b.extractForCastleSetup(2));
    }

    @Test
    void addStudentsInWaitingRoomTest() {
        Bag b = new Bag(10, 1);
        Castle castle = new Castle(Team.WHITE, 1, new ArrayList<StudentColor>());
        List<StudentColor> expected = Arrays.asList(StudentColor.YELLOW, StudentColor.RED, StudentColor.BLUE);
        try {
            castle.addStudentsInWaitingRoom(expected);
        } catch (TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, castle.getWaitingRoom());
    }

    @Test
    void addStudentInDiningRoomTest() {
        Bag b = new Bag(10, 1);
        Castle castle = new Castle(Team.WHITE, 1, new ArrayList<>());
        try {
            castle.addStudentInDiningRoom(StudentColor.BLUE);
        } catch (TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        EnumMap<StudentColor, Integer> testMap = new EnumMap<>(StudentColor.class);
        testMap.put(StudentColor.BLUE, 1);
        assertEquals(testMap.get(StudentColor.BLUE), castle.getDiningRoom().get(StudentColor.BLUE));
    }

    @Test
    void addStudentsInDiningRoom() {
        Bag b = new Bag(10, 1);
        Castle castle = new Castle(Team.WHITE, 1, new ArrayList<StudentColor>());
        List<StudentColor> expected = Arrays.asList(StudentColor.YELLOW, StudentColor.RED, StudentColor.BLUE);
        try {
            castle.addStudentsInDiningRoom(expected);
        } catch (TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        EnumMap<StudentColor, Integer> testMap = new EnumMap<>(StudentColor.class);
        testMap.put(StudentColor.BLUE, 1);
        testMap.put(StudentColor.RED, 1);
        testMap.put(StudentColor.YELLOW, 1);
        assertEquals(testMap.get(StudentColor.BLUE), castle.getDiningRoom().get(StudentColor.BLUE));
        assertEquals(testMap.get(StudentColor.RED), castle.getDiningRoom().get(StudentColor.RED));
        assertEquals(testMap.get(StudentColor.YELLOW), castle.getDiningRoom().get(StudentColor.YELLOW));
    }

    @Test
    void removeStudentsFromWaitingRoom() {
        Bag b = new Bag(10, 1);
        List<StudentColor> expected = Arrays.asList(StudentColor.YELLOW, StudentColor.RED, StudentColor.BLUE);
        Castle castle = new Castle(Team.WHITE, 1, expected);
        try {
            castle.removeStudentsFromWaitingRoom(expected);
        } catch (NoSuchStudentException e) {
            throw new RuntimeException(e);
        }
        assertEquals(new ArrayList<>(), castle.getWaitingRoom());
    }

    @Test
    void playCard() {
        Bag b = new Bag(10, 1);
        List<StudentColor> expected = Arrays.asList(StudentColor.YELLOW, StudentColor.RED, StudentColor.BLUE);
        Castle castle = new Castle(Team.WHITE, 1, expected);
        assertTrue(castle.isCardAvailable(1));
        castle.playCard(1);
        assertEquals(false, castle.isCardAvailable(1));
    }
}
