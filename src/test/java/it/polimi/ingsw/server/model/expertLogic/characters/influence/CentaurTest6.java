package it.polimi.ingsw.server.model.expertLogic.characters.influence;

import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoardStub;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.server.model.baseLogic.Team.BLACK;
import static it.polimi.ingsw.server.model.baseLogic.Team.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CentaurTest6 {

    CharacterExplanation explanation = CharacterExplanation.CENTAUR;
    ExpertBoardStub expertBoard;
    private String player1 = "giorgio", player2 = "moroder";

    @BeforeEach
    void setUp() throws CoinException, StudentException, PhaseNotRightException {
        expertBoard = new ExpertBoardStub(player1, player2, CharacterUtility.CENTAUR);
        expertBoard.playPlanningPhaseFirstPlayer1();
        expertBoard.easyMoveStudentsToDiningRoom();
        firstRound();

    }

    @Test
    void playExpertCardTest() throws CoinException, StudentException, PhaseNotRightException {
        expertBoard.playExpertCard(6, null, null);
        expertBoard.moveMotherNature(2);
        assertEquals(BLACK,
                expertBoard.getIslandList().get(5).getOwnership());
    }
    @Test
    void doNOTplayExpertCardTest() throws PhaseNotRightException {
        expertBoard.moveMotherNature(2);
        assertEquals(null,
                expertBoard.getIslandList().get(5).getOwnership());
    }

    void firstRound() throws StudentException, PhaseNotRightException, CoinException {
        Island island = expertBoard.getIslandList().get(5);
        StudentColor studentOnIsland =
                island
                        .getStudents()
                        .entrySet()
                        .stream()
                        .filter((entry -> entry.getValue()>0))
                        .findFirst()
                        .get()
                        .getKey();
        if(studentOnIsland == StudentColor.RED) {
            setUp();
            return;
        }
        island.addStudent(StudentColor.GREEN);
        expertBoard.changePhase();
        expertBoard.moveMotherNature(5);
        assertEquals(WHITE, expertBoard.getIslandList().get(5).getOwnership());
        expertBoard.changePhase();
        expertBoard.changePhase();
        //student phase player 2
        expertBoard.getCastle(player2).removeStudentsFromWaitingRoom(List.of(StudentColor.GREEN));
        expertBoard.getCastle(player2).addStudentsInWaitingRoom(List.of(StudentColor.RED));
        expertBoard.moveStudentsToDiningRoom(player2, List.of(StudentColor.RED));
        expertBoard.changePhase();
        expertBoard.moveMotherNature(5);
        assertEquals(10, expertBoard.getMotherNaturePosition());
        expertBoard.changePhase();
        expertBoard.changePhase();

        expertBoard.playPlanningPhaseFirstPlayer2();
        expertBoard.changePhase();
        expertBoard.moveMotherNature(5);
        assertEquals(3, expertBoard.getMotherNaturePosition());
        expertBoard.changePhase();
        expertBoard.changePhase();
        //player 1 - student
        //if player1 has 3 of influence on the 5th island:
        if(studentOnIsland == StudentColor.GREEN || studentOnIsland == StudentColor.YELLOW) {
            island.addStudent(StudentColor.RED);
        }
        island.addStudent(StudentColor.RED);
        island.addStudent(StudentColor.RED);
        expertBoard.changePhase();
    }
}