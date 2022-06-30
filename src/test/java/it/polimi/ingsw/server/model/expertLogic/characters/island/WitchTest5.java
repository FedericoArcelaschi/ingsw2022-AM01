package it.polimi.ingsw.server.model.expertLogic.characters.island;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoardStub;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WitchTest5 {

    CharacterExplanation explanation = CharacterExplanation.WITCH;

    private final String player1 = "Amico";
    private final String player2 = "Frizz";
    private ExpertBoardStub expertBoard;

    @BeforeEach
    void setUp() {
        expertBoard = new ExpertBoardStub(player1, player2, CharacterUtility.WITCH);
        expertBoard.playPlanningPhaseFirstPlayer1();
        expertBoard.easyMoveStudentsToDiningRoom();
    }

        @Test
    void playExpertCardTest() throws WrongGameModeException, CoinException, StudentException, PhaseNotRightException {
        final int islandIndex = 2;
        expertBoard.playExpertCard(
                CharacterUtility.WITCH.getId(),
                islandIndex,
                List.of());
        ExpertIsland blockedIsland = (ExpertIsland) expertBoard.getIslandList().get(islandIndex);
        assertTrue(blockedIsland.isBlocked());
        assertEquals(3, expertBoard.getAvailableCharacters().get(CharacterUtility.WITCH).getCost());
        assertEquals(1, expertBoard.getCastle(player1).getCoins());
    }

    @Test
    void errorNoInput() {
        assertThrowsExactly(IllegalArgumentException.class,
                () ->
                        expertBoard.playExpertCard(
                                CharacterUtility.WITCH.getId(),
                                null,
                                List.of()),
                "Illegal argument if no island index is given to the witch");
    }

    @Test
    void errorWrongInput() {
        assertThrowsExactly(IllegalArgumentException.class,
                ()->
                        expertBoard.playExpertCard(
                                CharacterUtility.WITCH.getId(),
                                15,
                                List.of()),
                "Illegal argument if a island number too big is given to the island");
        assertThrowsExactly(IllegalArgumentException.class,
                ()->
                expertBoard.playExpertCard(
                        CharacterUtility.WITCH.getId(),
                        -2,
                        List.of()),
                "Illegal argument if a island number too small (negative) is given to the island");
    }

    @Test
    void alreadyBlockedIsland() throws CoinException, StudentException, PhaseNotRightException, WrongGameModeException {
        //player1 plays this card
        expertBoard.playExpertCard(
                CharacterUtility.WITCH.getId(),
                0,
                List.of());

        playTillNextTurn();

        assertEquals("Island is already blocked",
            assertThrowsExactly(IllegalArgumentException.class,
                    ()->
                            expertBoard.playExpertCard(
                                    CharacterUtility.WITCH.getId(),
                                    0,
                                    List.of()),
                    "Illegal argument because the given island is already blocked").getMessage());
    }

    private void playTillNextTurn() {
        expertBoard.changePhase();
        try {
        expertBoard.moveMotherNature(1);
        expertBoard.changePhase();
        expertBoard.getCastle(player1).removeStudentsFromWaitingRoom(List.of(StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN));
        expertBoard.chooseCloud(player1, 1);
        expertBoard.changePhase();
        //player 2, student
        expertBoard.moveStudentsToDiningRoom(player2, List.of(StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN));
        //the stub gives me for sure those 6 students.
        assertEquals(3, expertBoard.getCastle(player2).getCoins());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


}
