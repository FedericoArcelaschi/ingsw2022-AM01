package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.server.model.baseLogic.Card;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.exceptions.*;
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
    private final String player1 = "Lorenzo";
    private final String player2 = "Federico";
    private final String player3 = "Giovanni";

    @BeforeEach
    public void setUp() {
        Turn t = new Turn(Arrays.asList(player1, player2, player3));
        expertBoard = (ExpertBoard) BoardFactory.getBoard(Arrays.asList(player1, player2, player3), true, RandomGenerator.getDefault().nextLong());
        try {
            expertBoard.playCard(player1, 5);
            expertBoard.changePhase();
            expertBoard.playCard(player2, 7);
            expertBoard.changePhase();
            expertBoard.playCard(player3, 2);
        } catch (PhaseNotRightException e) {
            e.printStackTrace();
            fail();
        }
        expertBoard.getTurn().addCard(player1, new Card(5));
        expertBoard.getTurn().addCard(player2, new Card(7));
        expertBoard.getTurn().addCard(player3, new Card(2));
        expertBoard.getTurn().changePhase();
    }

    @Test
    public void testSetup4CharacterTesting() {
        if(!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.FARMER)){
            setUp();
            testSetup4CharacterTesting();
            return;
        }
        assertTrue(expertBoard.getAvailableCharacters().containsKey(CharacterUtility.FARMER));
    }

    /**
     * Tries the easiest implementation of the method -
     * Deeper testing in each Character test class
     */
    @Test
    public void testPlayExpertCard() {
        if (!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.MAILMAN)) {
            assertThrowsExactly(IllegalArgumentException.class,
                    () -> expertBoard.playExpertCard(4, 0, null),
                    "Mailman not in extracted");
            setUp();
            testPlayExpertCard();
            return;
        }
        try {
            expertBoard.playExpertCard(4, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail(" playExpertCard() method throw exception " + e);
        }
        assertEquals(CharacterUtility.MAILMAN.name(),
                expertBoard.getAvailableCharacters().get(CharacterUtility.MAILMAN).getName());
    }

    @Test
    void testPlayExpertCardException1() {
        if (!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.MAILMAN)) {
            setUp();
            testPlayExpertCardException1();
            return;
        }
        try{
            expertBoard.playExpertCard(4, 0, null);
        } catch (Exception error){
            System.err.println(error.getMessage());
            fail();
        }

        try{
            expertBoard.playExpertCard(4, 0, null);
        } catch (IllegalStateException e) {
            assertEquals("MAILMAN is already active during this turn.",
                    e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @SuppressWarnings("empty")
    void testPlayExpertCardException3() {
        if (!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.MAILMAN)) {
            setUp();
            testPlayExpertCardException3();
            return;
        }

        try {
            expertBoard.playExpertCard(4, 0, null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }

        try {
            expertBoard.playExpertCard(4, 0, null);
        } catch (IllegalStateException e) {
            assertEquals("MAILMAN is already active during this turn.", e.getMessage());
        } catch (Exception e) {
            fail();
        }

        for (int i = 1; i < 13; i++) {
            if (i == 4) i++;//skipping the MailMan
            if (expertBoard.getAvailableCharacters().get(CharacterUtility.values()[i-1]) != null) {
                try {
                    expertBoard.playExpertCard(i, 0, null);
                } catch (IllegalStateException e) {
                    assertEquals("Not possible to play " + CharacterUtility.getChar(i) + " card. During this turn MAILMAN is already active.",
                            /*actual*/e.getMessage());
                } catch (CoinException | StudentException | PhaseNotRightException e) {
                    fail();
                }
            }
        }
    }

    @Test
    void testPlayExpertCardException4() {
        if (!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.MAILMAN)) {
            setUp();
            testPlayExpertCardException4();
            return;
        }
        ExpertCastle currentPlayerCastle = (ExpertCastle) expertBoard.getCastle(expertBoard.getCurrentPlayer());
        try {
            currentPlayerCastle.payCharacter(1);
        } catch (CoinException e) {
            fail(e.getMessage());
        }
        assertThrowsExactly(CoinException.class,
                () -> expertBoard.playExpertCard(4, null, null)
        );
        if (!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.WITCH)) {
            setUp();
            testPlayExpertCardException4();
            return;
        }
        assertEquals(
                "You had only 0 coins, while 2 coins were needed.",
                assertThrowsExactly(CoinException.class,
                    () -> expertBoard.playExpertCard(5, null, null),
                    "expected CoinException, actual: ").getMessage()
        );
    }

    @Test
    void testPlayExpertCardException5() {
        if (!expertBoard.getAvailableCharacters().containsKey(CharacterUtility.GUARD)) {
            setUp();
            testPlayExpertCardException5();
            return;
        }
        assertThrowsExactly(CoinException.class,
                () -> expertBoard.playExpertCard(3, null, null),
                "Not enough coins simulations");
    }

}