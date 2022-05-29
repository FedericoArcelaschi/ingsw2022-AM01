package it.polimi.ingsw.model.expertLogic.characters.student;

import it.polimi.ingsw.model.baseLogic.Castle;
import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.model.baseLogic.Turn;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.model.expertLogic.character.charTypes.StudentCharacter;
import it.polimi.ingsw.model.expertLogic.character.costants.CharacterExplanation;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class QueenTest { //11° character
    private CharacterExplanation characterExplanation = CharacterExplanation.QUEEN;
    private String player1 = "Laura", player2 = "Niccolò";

    @Test
    void playCharacterTest() {
        ExpertBoard expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player2, player1)), RandomGenerator.getDefault().nextLong());
        assertEquals(player2, expertBoard.getCurrentPlayer());
        expertBoard.extract4CharacterTesting(11);
        Castle currentPlayerCastle = expertBoard.getCastle(expertBoard.getCurrentPlayer());

        try {
            expertBoard.moveStudentsToDiningRoom(player2, currentPlayerCastle.getWaitingRoom());
        } catch (NoSuchStudentException | NotYourTurnException | TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        Map<StudentColor, Integer> diningRoom = new HashMap<>(currentPlayerCastle.getDiningRoom());
        StudentCharacter studentCharacter = (StudentCharacter) expertBoard.getAvailableCharacterCards().get(11);
        int indexStudentToMove = 0;
        StudentColor studentToMove = studentCharacter.getAvailableStudents().get(indexStudentToMove);

        try {
            expertBoard.playExpertCard(11, 0, List.of(studentToMove));
        } catch (CoinException e) {
            playCharacterTest();
            return;
        } catch (StudentException e) {
            throw new RuntimeException(e);
        }
        diningRoom.put(studentToMove, diningRoom.get(studentToMove)+1);
        assertEquals(diningRoom, currentPlayerCastle.getDiningRoom());
    }

    void playCharacterTestError() throws StudentException {
        ExpertBoard expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player2, player1)), RandomGenerator.getDefault().nextLong());
        assertEquals(player2, expertBoard.getCurrentPlayer());
        expertBoard.extract4CharacterTesting(11);
        Castle currentPlayerCastle = expertBoard.getCastle(expertBoard.getCurrentPlayer());

        try {
            expertBoard.moveStudentsToDiningRoom(player2, currentPlayerCastle.getWaitingRoom());
        } catch (NoSuchStudentException | NotYourTurnException | TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        Map<StudentColor, Integer> diningRoom = new HashMap<>(currentPlayerCastle.getDiningRoom());
        StudentCharacter studentCharacter = (StudentCharacter) expertBoard.getAvailableCharacterCards().get(11);
        int indexStudentToMove = 0;
        List<StudentColor> availableStudents = studentCharacter.getAvailableStudents();
        StudentColor studentToMove = StudentColor.YELLOW;
        for (StudentColor c : StudentColor.values())
            if(!availableStudents.contains(c))
                studentToMove = c; //this isn't available!
        try {
            StudentColor finalStudentToMove = studentToMove;
            expertBoard.playExpertCard(11, 0, List.of(finalStudentToMove));
        } catch (CoinException e) {
            playCharacterTestError();
            return;
        } catch (NoSuchStudentException e) {
            throw new NoSuchStudentException(e.getMessage());
        } catch (StudentException e) {
            throw new StudentException(e);
        }
        diningRoom.put(studentToMove, diningRoom.get(studentToMove)+1);
        assertEquals(diningRoom, currentPlayerCastle.getDiningRoom());
    }

    @Test
    void weirdTestEncapsulation() {
        assertThrowsExactly(StudentException.class,
                () -> playCharacterTestError(),
                "Queen doesn't have the color i asked for.");
    }
}
