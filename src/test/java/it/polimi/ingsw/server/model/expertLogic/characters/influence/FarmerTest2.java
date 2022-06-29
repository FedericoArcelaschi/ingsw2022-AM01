package it.polimi.ingsw.server.model.expertLogic.characters.influence;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoardStub;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.server.model.baseLogic.Team.BLACK;
import static it.polimi.ingsw.server.model.baseLogic.Team.WHITE;
import static org.junit.jupiter.api.Assertions.*;

class FarmerTest2 {

    CharacterExplanation explanation = CharacterExplanation.FARMER;
    String playerID1 = "£", playerID2 = "$";
    private ExpertBoardStub expertBoard;

    @BeforeEach
    void setUp() {
        expertBoard = new ExpertBoardStub(playerID1, playerID2, CharacterUtility.FARMER);
        expertBoard.playPlanningPhaseFirstPlayer1();

        expertBoard.easyMoveStudentsToDiningRoom(playerID1);
        expertBoard.easyMoveStudentsToDiningRoom(playerID2);

        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.GREEN)); //player1
        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.YELLOW)); //player1

        expertBoard.changePhase();
        expertBoard.changePhase();
        expertBoard.changePhase();
        //player2 - student
    }

    @Test
    void playExpertCharacterTest() {
        try {
            expertBoard.playExpertCard(2, null, null);
        } catch (CoinException | StudentException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        assertEquals(BLACK, expertBoard.getProfessorsMap().get(StudentColor.GREEN)); //player1
        assertEquals(BLACK, expertBoard.getProfessorsMap().get(StudentColor.YELLOW)); //player1
    }

    @Test
    void playExpertCharacterTestOtherInputs() {
        try {
            expertBoard.playExpertCard(2, 12, List.of(StudentColor.RED, StudentColor.GREEN));
        } catch (CoinException | StudentException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        assertEquals(BLACK, expertBoard.getProfessorsMap().get(StudentColor.GREEN)); //player1
        assertEquals(BLACK, expertBoard.getProfessorsMap().get(StudentColor.YELLOW)); //player1
    }

    @Test
    void doNOTPlayExpertCharacterTest() {
        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.GREEN)); //player1
        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.YELLOW)); //player1
    }


}