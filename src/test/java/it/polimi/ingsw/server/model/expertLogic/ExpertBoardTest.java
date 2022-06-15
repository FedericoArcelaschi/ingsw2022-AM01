package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.exceptions.*;

import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;


public class ExpertBoardTest{

    private static ExpertBoard expertBoard;

    @BeforeEach
    public void setUp() {
        Turn t = new Turn(Arrays.asList("Lorenzo", "Federico", "Giovanni"));
        expertBoard = (ExpertBoard) BoardFactory.getBoard(Arrays.asList("Lorenzo", "Federico", "Giovanni"), true, RandomGenerator.getDefault().nextLong());
        try {
            expertBoard.playCard("Lorenzo", 3);
            expertBoard.playCard("Federico",2);
            expertBoard.playCard("Giovanni",6);
        } catch (NotYourTurnException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tries the easiest implementation of the method - Deeper testing in each Character test class
     */
    //TODO: test exceptions
    @Test
    public void testPlayExpertCard() throws WrongGameModeException {
        if (expertBoard.getAvailableCharacters().containsKey(CharacterUtility.MAILMAN)) {
            assertThrows(IllegalArgumentException.class, () -> expertBoard.playExpertCard(4, 0, null),
                    "Mailman not in extracted");
            setUp(); testPlayExpertCard(); return;
        }
        int expectedPossibleMovingSteps = expertBoard.getPossibleMovingSteps() + 2;
        try {
            expertBoard.playExpertCard(4, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail(" playExpertCard() method throw exception " + e);
        }
        assertEquals(expectedPossibleMovingSteps, expertBoard.getPossibleMovingSteps());
        assertTrue(expertBoard.getAvailableCharacters().containsKey(CharacterUtility.MAILMAN));
    }

    @Test
    void testPlayExpertCardException1(){
        try{
            expertBoard.playExpertCard(4, 0, null);
        } catch (Exception error) {
            testPlayExpertCardException1();
            System.out.println(error.getMessage());
            return;
        }

        try{
            expertBoard.playExpertCard(4, 0, null);
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
            expertBoard.playExpertCard(4, 0, null);
        } catch (StudentException | CoinException | PhaseNotRightException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            testPlayExpertCardException2();
            return;
        }
        for (i = 1; i < 13; i++) {
            if (!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.values()[i-1]))
            try {
                expertBoard.playExpertCard(i, 0, null);
            } catch (IllegalStateException ignored) {

            } catch (IllegalArgumentException e) {
                testPlayExpertCardException2();
                System.out.println(e.getMessage());
                fail("only the first exception should be called.");
            } catch (Exception others) {
                others.printStackTrace();
                fail("only the first exception should be called.");
            }
        }
    }

    @Test
    @SuppressWarnings("empty")
    void testPlayExpertCardException3() throws WrongGameModeException {
        try {
            expertBoard.playExpertCard(4, 0, null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }


        try {
            expertBoard.playExpertCard(4, 0, null);
        } catch (IllegalStateException e) {
            assertEquals("MAILMAN is already active in this turn.", e.getMessage());
        } catch (Exception e) {
            fail();
        }

        for (int i = 1; i < 13; i++) {
            if (i == 4) i++;//skipping the MailMan
            if (!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.values()[i-1])) {
                try {
                    expertBoard.playExpertCard(i, 0, null);
                } catch (IllegalStateException | PhaseNotRightException e) {
                    assertEquals("Not possible to play " + CharacterUtility.getChar(i) + " card. During this turn MAILMAN is already active.",
                            /*actual*/e.getMessage());
                } catch (CoinException | StudentException e) {
                    fail();
                }
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
        assertThrowsExactly(CoinException.class,
                () -> expertBoard.playExpertCard(4, 0, null)
        );
        assertEquals(
                "You had only 0 coins, while 2 coins were needed.",
                assertThrowsExactly(CoinException.class,
                    () -> expertBoard.playExpertCard(5, 0, null),
                    "expected CoinException, actual: ").getMessage()
        );
    }

    @Test
    void testPlayExpertCardException5() throws WrongGameModeException {
        if(!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.MAILMAN)){ setUp(); testPlayExpertCardException5(); return; }
        assertThrowsExactly(CoinException.class,
                () -> expertBoard.playExpertCard(3, 0, null),
                "Not enough coins simulations");
    }
}