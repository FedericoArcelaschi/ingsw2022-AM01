package it.polimi.ingsw.server.model.expertLogic.characters.student;

import it.polimi.ingsw.server.model.baseLogic.Card;
import it.polimi.ingsw.server.model.baseLogic.Castle;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StudentCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class QueenTest { //11° character
    private CharacterExplanation characterExplanation = CharacterExplanation.QUEEN;
    private String player1 = "Laura", player2 = "Niccolò";
    private ExpertBoard expertBoard;
    private Castle currentPlayerCastle;

    @BeforeEach
    void setUp() {
        expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player2, player1)), RandomGenerator.getDefault().nextLong());
        if(!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.QUEEN)) {
            setUp();
            return;
        }
        playPlanningPhaseFirstPlayer1();
        currentPlayerCastle = expertBoard.getCastle(expertBoard.getCurrentPlayer());
        try {
            expertBoard.moveStudentsToDiningRoom(player1, currentPlayerCastle.getWaitingRoom());
        } catch (NoSuchStudentException | TooManyStudentsException | PhaseNotRightException e) {
            e.printStackTrace();
            fail();
        }
        try {
            if(currentPlayerCastle.getCoins() < 2)
                setUp();
        } catch (WrongGameModeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void playCharacterTest() {
        StudentCharacter queen = (StudentCharacter) expertBoard.getAvailableCharacters()
                                                                .get(CharacterUtility.QUEEN);
        int indexStudentToMove = 0;
        StudentColor studentToMove = queen.getAvailableStudents().get(indexStudentToMove);

        Map<StudentColor, Integer> diningRoom = currentPlayerCastle.getDiningRoom();
        try {
            expertBoard.playExpertCard(11, 0, List.of(studentToMove));
        } catch (CoinException | StudentException | PhaseNotRightException e) {
            e.printStackTrace();
            fail();
        }
        diningRoom.put(studentToMove, diningRoom.get(studentToMove) + 1); //queen effect
        assertEquals(diningRoom, currentPlayerCastle.getDiningRoom());
    }

    @Test
    void playCharacterTestError() throws StudentException {
        try {
            expertBoard.moveStudentsToDiningRoom(player2, currentPlayerCastle.getWaitingRoom());
        } catch (NoSuchStudentException | TooManyStudentsException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        var diningRoom = new HashMap<>(currentPlayerCastle.getDiningRoom());
        var studentCharacter = expertBoard.getAvailableCharacters().get(CharacterUtility.QUEEN);

        var availableStudents = studentCharacter.getAvailableStudents();
        var studentToMove = StudentColor.YELLOW;
        for (StudentColor c : StudentColor.values())
            if(!availableStudents.contains(c))
                studentToMove = c; //this isn't available!
        StudentColor finalStudentToMove = studentToMove;
        assertThrowsExactly(StudentException.class,
                ()-> expertBoard.playExpertCard(11, 0, List.of(finalStudentToMove)),
                "Student not available on queen");
        assertEquals(diningRoom, currentPlayerCastle.getDiningRoom());
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
