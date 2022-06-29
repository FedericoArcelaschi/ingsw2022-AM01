package it.polimi.ingsw.server.model.expertLogic.characters.student;

import it.polimi.ingsw.server.model.baseLogic.Castle;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.baseLogic.interfaces.MapToList;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoardStub;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class StorytellerTest10 {

    private final CharacterExplanation characterExplanation = CharacterExplanation.STORYTELLER;
    private ExpertBoardStub board;
    private final String player1 = "Piro";
    private final String player2 = "Pinoli";
    private Castle currentPlayerCastle;

    @BeforeEach
    void setUp() {
        board = new ExpertBoardStub(player1, player2, CharacterUtility.STORYTELLER);
        if(!board.getAvailableCharacters().containsKey(CharacterUtility.STORYTELLER)) {
            setUp();
            return;
        }
        board.playPlanningPhaseFirstPlayer1();
        currentPlayerCastle = board.getCastle(board.getCurrentPlayer());
    }

    @Test
    void playCharacterTest() {
        //ColorsToMove
        var studentsToMove = currentPlayerCastle.getWaitingRoom().subList(0, 2);
        var studentsInWaitingRoom = currentPlayerCastle.getWaitingRoom().subList(2, 7);

        try {
            board.moveStudentsToDiningRoom("Piro", studentsToMove);
        } catch (NoSuchStudentException | TooManyStudentsException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }

        assertEquals(   studentsInWaitingRoom,
                        currentPlayerCastle.getWaitingRoom(),
                        "initial waiting room is this sublist.");
        assertEquals(   studentsToMove.stream().sorted().toList(),
                        MapToList.apply(currentPlayerCastle.getDiningRoom()).stream().sorted().toList(),
                        "Initial dining room corresponds to the students I want to remove from there.");

        List<StudentColor> studentsForCharacter = new ArrayList<>(studentsInWaitingRoom.subList(0, 2));
        //first two students in waiting room will be moved to the dining room
        studentsForCharacter.addAll(studentsToMove.subList(0, 2));
        //first two students in dining room will be moved to the waiting room

        //actual playExpertCard
        try {
            board.playExpertCard(10, 0, new ArrayList<>(studentsForCharacter));
        } catch (StudentException | CoinException | PhaseNotRightException e) {
            fail(e.getCause());
        }

        var expectedWaitingRoom
                = new ArrayList<>(studentsInWaitingRoom.subList(2, 5));
        expectedWaitingRoom.addAll(studentsToMove);

        assertEquals(   studentsInWaitingRoom.subList(0, 2).stream().sorted().toList(),
                        MapToList.apply(currentPlayerCastle.getDiningRoom()).stream().sorted().toList(),
                        "students were correctly moved from the waiting room to the dining room and replaced");
        assertEquals(   expectedWaitingRoom.stream().sorted().toList(),
                        currentPlayerCastle.getWaitingRoom().stream().sorted().toList(),
                        "students were correctly moved from the dining room to the waiting room and replaced.");
    }

    @Test
    void applyEffectErrorTest() {
        ExpertCastle currPlayerCastle = (ExpertCastle) board.getCastle("Piro");
        List<StudentColor> studentsToMove = currPlayerCastle.getWaitingRoom();

        try {
            board.moveStudentsToDiningRoom("Piro", studentsToMove); //all students are moved to the dining room.
        } catch (NoSuchStudentException | TooManyStudentsException | PhaseNotRightException e) {
            e.printStackTrace();
            fail();
        }

        List<StudentColor> studentsForCharacter = new ArrayList<>(studentsToMove.subList(0, 2));
        //first two students in waiting room will be moved to the dining room
        studentsForCharacter.addAll(studentsToMove.subList(0, 2));
        //first two students in dining room will be moved to the waiting room
        assertThrowsExactly(
                StudentException.class,
                ()-> board.playExpertCard(10, 0, studentsForCharacter),
                "there are no students in the Waiting room, therefore the NoSuchStudentException should be thrown!");
        try {
            currPlayerCastle.addStudentsInWaitingRoom(studentsToMove);
        } catch (TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        try {
            board.playExpertCard(10, 0, studentsForCharacter);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void OddListError() {
        assertThrows(IllegalArgumentException.class,
                () -> board.playExpertCard(10, 0, List.of(StudentColor.YELLOW)),
                "Storyteller needs an odd number of students as an input");
        assertThrows(IllegalArgumentException.class,
                () -> board.playExpertCard(10, 0, List.of(StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.YELLOW)));
        assertThrows(IllegalArgumentException.class,
                () -> board.playExpertCard(10, 0, List.of(StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.YELLOW, StudentColor.YELLOW)));
    }

    @Test
    void EmptyListError() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> board.playExpertCard(10, 0, List.of()),
                "no student will give an error");
    }
}