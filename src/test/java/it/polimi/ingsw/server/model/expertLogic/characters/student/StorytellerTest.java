package it.polimi.ingsw.server.model.expertLogic.characters.student;

import it.polimi.ingsw.server.model.baseLogic.Card;
import it.polimi.ingsw.server.model.baseLogic.Castle;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.baseLogic.interfaces.MapToList;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class StorytellerTest {// 10° character

    private final CharacterExplanation characterExplanation = CharacterExplanation.STORYTELLER;
    private ExpertBoard expertBoard;
    private final String player1 = "Piro";
    private final String player2 = "Pinoli";
    private Castle currentPlayerCastle;

    @BeforeEach
    void setUp() {
        expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        if(!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.STORYTELLER)) {
            setUp();
            return;
        }
        playPlanningPhaseFirstPlayer1();
        currentPlayerCastle = expertBoard.getCastle(expertBoard.getCurrentPlayer());
    }

    @Test
    void playCharacterTest() {

        var storyTeller = expertBoard.getAvailableCharacters().get(CharacterUtility.STORYTELLER);
        //ColorsToMove
        var studentsToMove = currentPlayerCastle.getWaitingRoom().subList(0, 2);
        var studentsInWaitingRoom = currentPlayerCastle.getWaitingRoom().subList(2, 7);

        try {
            expertBoard.moveStudentsToDiningRoom("Piro", studentsToMove);
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
            expertBoard.playExpertCard(10, 0, new ArrayList<>(studentsForCharacter));
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
        //Castle to work on:
        ExpertCastle castle = (ExpertCastle) expertBoard.getCastle("Piro");
        //ColorsToMove
        List<StudentColor> studentsToMove = castle.getWaitingRoom();

        try {
            expertBoard.moveStudentsToDiningRoom("Piro", studentsToMove); //all students are moved to the dining room.
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
                ()->expertBoard.playExpertCard(10, 0, studentsForCharacter),
                "there are no students in the Waiting room, therefore the NoSuchStudentException should be thrown!");

        try {
            castle.addStudentsInWaitingRoom(studentsToMove);
        } catch (TooManyStudentsException e) {throw new RuntimeException(e);}

        try {
            expertBoard.playExpertCard(10, 0, studentsForCharacter);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void EmptyListError1() {
        assertThrows(IllegalArgumentException.class,
                () -> expertBoard.playExpertCard(10, 0, List.of()));
    }

    @Test
    void EmptyListError() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> expertBoard.playExpertCard(10, 0, List.of()),
                "no student will give an error");
    }

    void playPlanningPhaseFirstPlayer1() {
        try {
            expertBoard.playCard(player1, 5);
            expertBoard.changePhase();
            expertBoard.playCard(player2, 8);
        } catch (PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        expertBoard.getTurn().addCard(player1, new Card(5));
        expertBoard.getTurn().addCard(player2, new Card(8));
        expertBoard.getTurn().changePhase();
        //here is in student phase
    }
}