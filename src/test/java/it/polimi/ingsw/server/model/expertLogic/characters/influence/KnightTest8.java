package it.polimi.ingsw.server.model.expertLogic.characters.influence;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoardStub;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.server.model.baseLogic.Team.BLACK;
import static it.polimi.ingsw.server.model.baseLogic.Team.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class KnightTest8 {
    CharacterExplanation explanation = CharacterExplanation.KNIGHT;
    String playerID1 = "pablo", playerID2 = "";
    private ExpertBoardStub expertBoard;

    @Test
    void blackWinsAllConfrontations() throws TooManyStudentsException {
        for (int i = 1; i < 6; i++) {
            setUpLoose();
            playExpertCharacterAndWinOwnerShip(i);
        }
    }

    @Test
    void blackTiesAllConfrontationsWithoutStudentsInTheCastle() throws TooManyStudentsException {
        for (int i = 1; i < 6; i++) {
            setUpLoose();
            playExpertCharacterAndTieOwnerShip(i);
        }
    }

    void setUpLoose() throws TooManyStudentsException {
        expertBoard = new ExpertBoardStub(playerID1, playerID2, CharacterUtility.KNIGHT);
        expertBoard.playPlanningPhaseFirstPlayer1();

        expertBoard.easyMoveStudentsToDiningRoom(playerID1);
        expertBoard.getCastle(playerID1).addStudentsInDiningRoom(List.of(StudentColor.GREEN,StudentColor.RED, StudentColor.PINK, StudentColor.YELLOW, StudentColor.BLUE));

        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.GREEN)); //player1
        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.RED)); //player1
        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.PINK)); //player1
        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.YELLOW)); //player1
        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.BLUE)); //player1

        expertBoard.changePhase();
        expertBoard.changePhase();
        expertBoard.changePhase();
        //Student phase - ""
    }

    void playExpertCharacterAndWinOwnerShip(int islandIndex) {
        expertBoard.add1Coin("");
        try {
            expertBoard.playExpertCard(8, null, null);
        } catch (StudentException | CoinException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }

        expertBoard.changePhase();
        try {
            expertBoard.moveMotherNature(islandIndex);
        } catch (PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        assertEquals(BLACK, expertBoard.getIslandList().get(islandIndex).getOwnership());
    }

    void setUpTie() throws TooManyStudentsException {
        expertBoard = new ExpertBoardStub(playerID1, playerID2, CharacterUtility.KNIGHT);
        expertBoard.playPlanningPhaseFirstPlayer1();

        expertBoard.easyMoveStudentsToDiningRoom(playerID1);
        expertBoard.getCastle(playerID1).addStudentsInDiningRoom(List.of(StudentColor.GREEN, StudentColor.RED, StudentColor.PINK, StudentColor.YELLOW, StudentColor.BLUE));

        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.GREEN)); //player1
        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.RED)); //player1
        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.PINK)); //player1
        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.YELLOW)); //player1
        assertEquals(WHITE, expertBoard.getProfessorsMap().get(StudentColor.BLUE)); //player1

        expertBoard.changePhase();
        expertBoard.changePhase();
        expertBoard.changePhase();
        //Student phase - ""
    }

    void playExpertCharacterAndTieOwnerShip(int islandIndex) {
        expertBoard.add1Coin("");
        try {
            expertBoard.playExpertCard(8, null, null);
        } catch (StudentException | CoinException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }

        try {
            expertBoard.moveStudentToIsland("", islandIndex, List.of(StudentColor.YELLOW));
            expertBoard.changePhase();
            expertBoard.moveMotherNature(islandIndex);
        } catch (PhaseNotRightException | NoSuchStudentException e) {
            throw new RuntimeException(e);
        }
        assertNull(expertBoard.getIslandList().get(islandIndex).getOwnership());
    }
}
