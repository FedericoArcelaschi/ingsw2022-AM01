package it.polimi.ingsw.model.expertLogic.characters.student;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.baseLogic.interfaces.MapToList;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class StorytellerTest {// 10° character

    private CharacterExplanation characterExplanation = CharacterExplanation.STORYTELLER;
    private ExpertBoard expertBoard;
    private String player1 = "Piro", player2 = "Pinoli";

    @Test
    void playCharacterTest() {


        //SetUp
        expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        expertBoard.extract4CharacterTesting(10);

        StandardCharacter storyTeller
                = expertBoard.getAvailableCharacterCards().get(10);

        //Castle to work on:
        ExpertCastle castle = (ExpertCastle) expertBoard.getCastle("Piro");
        //ColorsToMove
        List<StudentColor> studentsToMove = castle.getWaitingRoom().subList(0, 2);
        List<StudentColor> studentsInWaitingRoom = castle.getWaitingRoom().subList(2, 7);

        try {
            expertBoard.moveStudentsToDiningRoom("Piro", studentsToMove);
        } catch (NoSuchStudentException | NotYourTurnException | TooManyStudentsException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }

        assertEquals(studentsInWaitingRoom, castle.getWaitingRoom(),
                "initial waiting room is this sublist.");
        assertEquals(studentsToMove.stream().sorted().toList(),
                MapToList.apply(castle.getDiningRoom()).stream().sorted().toList(),
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

        List<StudentColor> expectedWaitingRoom = new ArrayList<>(studentsInWaitingRoom.subList(2, 5));
        expectedWaitingRoom.addAll(studentsToMove);

        assertEquals(studentsInWaitingRoom.subList(0, 2).stream().sorted().toList(),
                MapToList.apply(castle.getDiningRoom()).stream().sorted().toList(),
                "students were correctly moved from the waiting room to the dining room and replaced");
        assertEquals(expectedWaitingRoom.stream().sorted().toList(),
                castle.getWaitingRoom().stream().sorted().toList(),
                "students were correctly moved from the dining room to the waiting room and replaced.");
    }

    @Test
    void applyEffectErrorTest() {
        expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        expertBoard.extract4CharacterTesting(10);

        StandardCharacter storyTeller
                = expertBoard.getAvailableCharacterCards().get(10);
        //Castle to work on:
        ExpertCastle castle = (ExpertCastle) expertBoard.getCastle("Piro");
        //ColorsToMove
        List<StudentColor> studentsToMove = castle.getWaitingRoom();

        try {
            expertBoard.moveStudentsToDiningRoom("Piro", studentsToMove); //all students are in the diningroom.
        } catch (NoSuchStudentException | NotYourTurnException | TooManyStudentsException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }

        List<StudentColor> studentsForCharacter = new ArrayList<>(studentsToMove.subList(0, 2));
        //first two students in waiting room will be moved to the dining room
        studentsForCharacter.addAll(studentsToMove.subList(0, 2));
        //first two students in dining room will be moved to the waiting room

        assertThrowsExactly(StudentException.class,
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
}