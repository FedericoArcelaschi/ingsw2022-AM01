package it.polimi.ingsw.server.model.expertLogic.characters.island;

import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.exceptions.CoinException;
import it.polimi.ingsw.server.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class WitchTest { //5° character

    CharacterExplanation explanation = CharacterExplanation.WITCH;

    private String player1 = "Amico", player2 = "Frizz";
    private ExpertBoard expertBoard;

    @Test
    void playExpertCardTest() {
        expertBoard = new ExpertBoard(player1, player2, new Turn(List.of(player1, player2)), RandomGenerator.getDefault().nextLong());
        expertBoard.extract4CharacterTesting(5);

        int islandIndex = 2;

        try {
            expertBoard.playCard(player1, 5);
        } catch (NotYourTurnException e) { fail(e.getCause()); }

        try {
            expertBoard.moveStudentsToDiningRoom(player1, expertBoard.getCastle(player1).getWaitingRoom());
        } catch (Exception e) { fail(e.getCause()); }

        try {
            expertBoard.playExpertCard(5, islandIndex, List.of());
        } catch (StudentException e) {
            fail(e.getCause());
        } catch (CoinException e) {
            playExpertCardTest();
            return;
        }
        ExpertIsland blockedIsland = (ExpertIsland) expertBoard.getIslandList().get(islandIndex);
        assertTrue(blockedIsland.isBlocked());
    }

}
