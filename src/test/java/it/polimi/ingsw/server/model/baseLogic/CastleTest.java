package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CastleTest {

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
        assertFalse(castle.isCardAvailable(1));
    }
}
