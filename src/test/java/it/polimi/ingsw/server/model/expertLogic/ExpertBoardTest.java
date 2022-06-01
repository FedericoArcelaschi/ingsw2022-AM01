package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.baseLogic.TurnPhase;
import it.polimi.ingsw.server.model.exceptions.CoinException;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.expertLogic.ExpertBoard;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;


public class ExpertBoardTest{

    private static ExpertBoard expertBoard;

    @BeforeEach
    public void setUp() throws Exception {
        Turn t = new Turn(Arrays.asList("Lorenzo", "Federico", "Giovanni"));
        expertBoard = (ExpertBoard) BoardFactory.getBoard(Arrays.asList("Lorenzo", "Federico", "Giovanni"), true, t, RandomGenerator.getDefault().nextLong());
        expertBoard.playCard("Lorenzo", 3);
    }

    @Test
    public void testSetup4CharacterTesting() {
        expertBoard.extract4CharacterTesting(1);
        assertNotNull(expertBoard.getAvailableCharacterCards().get(1));
    }

    /**
     * Tries the easiest implementation of the method - Deeper testing in each Character test class
     */
    //TODO: test exceptions
    @Test
    public void testPlayExpertCard() {
        if (expertBoard.getAvailableCharacterCards().get(4) == null) {
            expertBoard.setCurrentPhase(TurnPhase.STUDENTS);
            assertThrows(IllegalArgumentException.class, () -> expertBoard.playExpertCard(4, null, null),
                    "Mailman not in extracted");
            expertBoard.extract4CharacterTesting(4);
        }
        int expectedPossibleMovingSteps = expertBoard.getPossibleMovingSteps() + 2;
        try {
            expertBoard.playExpertCard(4, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail(" playExpertCard() method throw exceptions " + e);
        }
        assertEquals(expectedPossibleMovingSteps, expertBoard.getPossibleMovingSteps());
        assertEquals(CharacterUtility.MAILMAN.name(), expertBoard.getAvailableCharacterCards().get(4).getName());
    }

    @Test
    void testPlayExpertCardException1(){

        expertBoard.extract4CharacterTesting(4);

        try{
            expertBoard.playExpertCard(4, null, null);
        } catch (Exception error){
            System.out.println(error.getMessage());
            fail();
        }

        try{
            expertBoard.playExpertCard(4, null, null);
        } catch (IllegalStateException e) {
            assertEquals("MAILMAN is already active in this turn.",
                    e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testPlayExpertCardException2() {
        int i;
        try {
            expertBoard.extract4CharacterTesting(4);
            expertBoard.playExpertCard(4, null, null);
        } catch (StudentException | CoinException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
        for (i = 1; i < 13; i++) {
            if (expertBoard.getAvailableCharacterCards().get(i) == null)
                expertBoard.extract4CharacterTesting(i);
            try {
                expertBoard.playExpertCard(i, null, null);
            } catch (IllegalStateException ignored) {

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                fail("only the first exceptions should be called.");
            } catch (Exception others) {
                others.printStackTrace();
                fail("only the first exceptions should be called.");
            }
        }
    }

    @Test
    @SuppressWarnings("empty")
    void testPlayExpertCardException3() {
        expertBoard.extract4CharacterTesting(4);
        try {
            expertBoard.playExpertCard(4, null, null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }


        try {
            expertBoard.playExpertCard(4, null, null);
        } catch (IllegalStateException e) {
            assertEquals("MAILMAN is already active in this turn.", e.getMessage());
        } catch (Exception e) {
            fail();
        }

        for (int i = 1; i < 13; i++) {
            if (i == 4) i++;//skipping the MailMan
            if (expertBoard.getAvailableCharacterCards().get(i) == null) ;
            try {
                expertBoard.playExpertCard(i, null, null);
            } catch (IllegalStateException e) {
                assertEquals("Not possible to play " + CharacterUtility.getChar(i) + " card. During this turn MAILMAN is already active.",
                        /*actual*/e.getMessage());
            } catch (CoinException | StudentException | PhaseNotRightException e) {
                fail();
            }
        }
    }

    @Test
    void testPlayExpertCardException4() {
        ExpertCastle currentPlayerCastle = (ExpertCastle) expertBoard.getCastle(expertBoard.getCurrentPlayer());
        try {
            currentPlayerCastle.payCharacter(1);
        } catch (CoinException e) {
            fail(e.getMessage());
        }
        expertBoard.extract4CharacterTesting(4);
        assertThrowsExactly(CoinException.class,
                () -> expertBoard.playExpertCard(4, null, null)
        );
        expertBoard.extract4CharacterTesting(5);
        assertEquals(
                "You had only 0 coins, while 2 coins were needed.",
                assertThrowsExactly(CoinException.class,
                    () -> expertBoard.playExpertCard(5, null, null),
                    "expected CoinException, actual: ").getMessage()
        );
    }

    @Test
    void testPlayExpertCardException5() {
        expertBoard.extract4CharacterTesting(3);
        assertThrowsExactly(CoinException.class,
                () -> expertBoard.playExpertCard(3, null, null),
                "Not enough coins simulations");
    }
}