package it.polimi.ingsw.server.model.expertLogic.characters.student;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoardStub;
import it.polimi.ingsw.server.model.expertLogic.character.specializedCharacters.StudentCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class QueenTest11 {

    private final CharacterExplanation characterExplanation = CharacterExplanation.QUEEN;
    private final String player1 = "Laura";
    private final String player2 = "Niccolò";
    private ExpertBoardStub expertBoard;

    @BeforeEach
    void setUp() {
        expertBoard = new ExpertBoardStub(player1, player2, CharacterUtility.QUEEN);
        expertBoard.playPlanningPhaseFirstPlayer1();
        expertBoard.easyMoveStudentsToDiningRoom();
    }

    @Test
    void testPLayExpertCharacter() {
        StudentCharacter queen
                = (StudentCharacter)     expertBoard
                                        .getAvailableCharacters()
                                        .get(CharacterUtility.QUEEN);
        StudentColor studentToMove = queen.getAvailableStudents().get(0);

        Map<StudentColor, Integer> oldDiningRoom = expertBoard.getCastle(player1).getDiningRoom();

        try {
            expertBoard.playExpertCard(11, 0, List.of(studentToMove));
        } catch (CoinException | StudentException | PhaseNotRightException e) {
            e.printStackTrace();
            fail();
        }
        Map<StudentColor, Integer> expectedDiningRoom = new EnumMap<>(oldDiningRoom);
        expectedDiningRoom.put(studentToMove, oldDiningRoom.get(studentToMove) + 1);
        assertEquals(expectedDiningRoom, expertBoard.getCastle(player1).getDiningRoom());
    }



    @Test
    void playCharacterTestErrorWrongInput() {
        var oldDiningRoom = expertBoard.getCastle(player1).getDiningRoom();
        var studentCharacter = expertBoard.getAvailableCharacters().get(CharacterUtility.QUEEN);
        var availableStudents = studentCharacter.getAvailableStudents();
        for (StudentColor c : StudentColor.values())
            if(!availableStudents.contains(c))
                assertThrowsExactly(StudentException.class,
                        ()-> expertBoard.playExpertCard(11, null, List.of(c)),
                        "Student not available on queen");

        assertEquals(oldDiningRoom, expertBoard.getCastle(player1).getDiningRoom());
    }

    @Test
    void playCharacterTestErrorNoInput() {
        assertThrowsExactly(IllegalArgumentException.class,
            ()-> expertBoard.playExpertCard(11, null, null),
            "No given student to the character!");

    }
}
