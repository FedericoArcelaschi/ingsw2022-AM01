package it.polimi.ingsw.server.model.expertLogic.characters.student;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;

import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StudentCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

import static it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility.QUEEN;
import static org.junit.jupiter.api.Assertions.*;


public class QueenTest { //11° character
    private CharacterExplanation characterExplanation = CharacterExplanation.QUEEN;
    private final String player1 = "Laura";
    private String player2 = "Niccolò";

    @Test
    void playCharacterTest() {
        ExpertBoard expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player2, player1)), RandomGenerator.getDefault().nextLong());
        assertEquals(player2, expertBoard.getCurrentPlayer());
        Castle currentPlayerCastle = expertBoard.getCastle(expertBoard.getCurrentPlayer());

        try {
            expertBoard.moveStudentsToDiningRoom(player2, currentPlayerCastle.getWaitingRoom());
        } catch (NoSuchStudentException | NotYourTurnException | TooManyStudentsException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        Map<StudentColor, Integer> diningRoom = new HashMap<>(currentPlayerCastle.getDiningRoom());
        List<StandardCharacter> standardCharacters = expertBoard.getAvailableCharacters().values().stream().toList();
        int indexStudentToMove = 0;
        if(standardCharacters.stream().noneMatch(s->s.getCharacterUtility() == QUEEN)) {
            playCharacterTest();
            return;
        }
        StudentCharacter studentCharacter = (StudentCharacter) standardCharacters.stream().filter(s->s.getCharacterUtility() == QUEEN).findAny().get();
        StudentColor studentToMove = studentCharacter.getAvailableStudents().get(0);
        try {
            expertBoard.playExpertCard(11, 0, List.of(studentToMove));
        } catch (CoinException | PhaseNotRightException e) {
            playCharacterTest();
            return;
        } catch (StudentException e) {
            throw new RuntimeException(e);
        }
        diningRoom.put(studentToMove, diningRoom.get(studentToMove) + 1);
        assertEquals(diningRoom, currentPlayerCastle.getDiningRoom());
    }

    void playCharacterTestError() throws StudentException, WrongGameModeException {
        ExpertBoard expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player2, player1)), RandomGenerator.getDefault().nextLong());
        assertEquals(player2, expertBoard.getCurrentPlayer());
        Castle currentPlayerCastle = expertBoard.getCastle(expertBoard.getCurrentPlayer());

        try {
            expertBoard.moveStudentsToDiningRoom(player2, currentPlayerCastle.getWaitingRoom());
        } catch (NoSuchStudentException | NotYourTurnException | TooManyStudentsException | PhaseNotRightException e) {
            fail(e.getCause());
        }
        Map<StudentColor, Integer> diningRoom = new HashMap<>(currentPlayerCastle.getDiningRoom());
        StudentCharacter studentCharacter = (StudentCharacter) expertBoard.getAvailableCharacters().get(QUEEN);
        int indexStudentToMove = 0;
        List<StudentColor> availableStudents = studentCharacter.getAvailableStudents();
        StudentColor studentToMove = StudentColor.YELLOW;
        for (StudentColor c : StudentColor.values())
            if(!availableStudents.contains(c))
                studentToMove = c; //this isn't available!
        try {
            expertBoard.playExpertCard(11, 0, List.of(studentToMove));
        } catch (CoinException e) {
            playCharacterTestError();
            return;
        } catch (NoSuchStudentException e) {
            throw new NoSuchStudentException(e.getMessage());
        } catch (StudentException | PhaseNotRightException e) {
            throw new StudentException(e);
        }
        diningRoom.put(studentToMove, diningRoom.get(studentToMove)+1);
        assertEquals(diningRoom, currentPlayerCastle.getDiningRoom());
    }

    @Test
    void weirdTestEncapsulation() {
        assertThrowsExactly(StudentException.class,
                this::playCharacterTestError,
                "Queen doesn't have the color i asked for.");
    }
}
