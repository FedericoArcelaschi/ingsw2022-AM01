package it.polimi.ingsw.server.model.expertLogic.characters.island;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.baseLogic.interfaces.MapToList;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoardStub;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static it.polimi.ingsw.server.model.baseLogic.StudentColor.*;
import static it.polimi.ingsw.server.model.baseLogic.Team.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class GuardTest { //3° Character

    CharacterExplanation explanation = CharacterExplanation.GUARD;
    private ExpertBoardStub board;
    private final String player1 = "Gianpaolo", player2 = "Cugola";

    @Test
    void playExpertCharacter10times() {
        for (int i = 0; i < 12; i++) {
            playExpertCharacter(i);
            playExpertCharacterButNotConquer(i);
        }
    }

    void setUp() {
        board = new ExpertBoardStub(player1, player2, CharacterUtility.GUARD);
        board.playPlanningPhaseFirstPlayer1();
        board.easyMoveStudentsToDiningRoom();
        board.changePhase();
        board.changePhase();
        //student phase "Gianpaolo"
    }

    void playExpertCharacter(int islandIndex) {
        setUp();
        board.getIslandList().get(islandIndex).addStudent(StudentColor.YELLOW);
        assertNull(board.getIslandList().get(islandIndex).getOwnership());
        try{
            board.playExpertCard(3, islandIndex, List.of());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Team gianpaolosTeam = board.getCastle(player1).getTeam();
        assertEquals(gianpaolosTeam, board.getIslandList().get(islandIndex).getOwnership(),
                "the island should be now conquered by Gianpaolo");
    }

    @Test
    void playExpertCharacterButNotConquer(int islandIndex) {
        setUp();
        if(MapToList.apply(board.getIslandList().get(islandIndex).getStudents()).contains(YELLOW) ||
                MapToList.apply(board.getIslandList().get(islandIndex).getStudents()).contains(GREEN))
            return;
        assertNull(board.getIslandList().get(islandIndex).getOwnership());
        try{
            board.playExpertCard(3, islandIndex, List.of());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Team gianpaolosTeam = board.getCastle(player1).getTeam();
        assertNull(board.getIslandList().get(islandIndex).getOwnership(),
                "the island should be now conquered by Gianpaolo");
    }
}
