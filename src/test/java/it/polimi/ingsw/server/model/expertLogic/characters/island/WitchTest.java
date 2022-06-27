package it.polimi.ingsw.server.model.expertLogic.characters.island;

import it.polimi.ingsw.server.model.baseLogic.Card;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.BlockedIsland;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoardStub;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterExplanation;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import it.polimi.ingsw.server.model.expertLogic.character.specializedCharacters.charTypes.BlockingCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class WitchTest { //5° character

    CharacterExplanation explanation = CharacterExplanation.WITCH;

    private final String player1 = "Amico";
    private final String player2 = "Frizz";
    private ExpertBoard expertBoard;

    @BeforeEach
    void setUp() {
       expertBoard = new ExpertBoardStub(player1, player2, CharacterUtility.WITCH);
        playPlanningPhaseFirstPlayer1();
        moveStudentsToWaitingRoom();
    }

    private void moveStudentsToWaitingRoom() {
        List<StudentColor> studentsToAdd = expertBoard.getCastle(player1).getWaitingRoom();
        try {
            expertBoard.moveStudentsToDiningRoom(player1, studentsToAdd);
            //gains 2 coins because of the stub.
        } catch (NoSuchStudentException | PhaseNotRightException | TooManyStudentsException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(3, expertBoard.getCastle(player1).getCoins());
        } catch (WrongGameModeException e) {
            e.printStackTrace();
            fail();
        }
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
        } catch (PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        expertBoard.changePhase();
        try {
            expertBoard.chooseCloud(player1, 1);
        } catch (TooManyStudentsException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        expertBoard.changePhase();
        //player 2, student
        try {
            expertBoard.moveStudentsToDiningRoom(player2, List.of(StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN));
        } catch (NoSuchStudentException | TooManyStudentsException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        //the stub gives me for sure those 6 students.
        try {
            assertEquals(3, expertBoard.getCastle(player2).getCoins());
        } catch (WrongGameModeException e) {
            throw new RuntimeException(e);
        }
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
        //here is in student phase - player 1
    }
}
