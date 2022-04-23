package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.CoinException;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.Characters.CharactersList;
import it.polimi.ingsw.model.expert.Characters.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class ExpertBoardTest{

    private static ExpertBoard board;

    @BeforeEach
    public void setUp() throws Exception {
        Turn t = new Turn(Arrays.asList("Lorenzo", "Federico", "Giovanni"));
        board = new ExpertBoard("Lorenzo", "Federico", "Giovanni", t);
        board.playCard("Lorenzo", 3);
    }

    @Test
    public void testSetup4CharacterTesting() {
        board.extract4CharacterTesting(1);
        assertTrue(board.getAvailableCharacterCards()
                        .contains(
                        new Student(1, board.getBag())
                        ));
    }

    /**
     * Tries the easiest implementation of the method - Deeper testing in each Character test class
     *
     * @throws NoSuchStudentException
     * @throws TooManyStudentsException
     */
    //TODO: test exceptions
    @Test
    public void testPlayExpertCard() {
        if (board.getAvailableCharacterCards().get(4) == null) {
            assertThrows(IllegalArgumentException.class, () -> board.playExpertCard(4),
                    "Mailman not in extracted");
            board.extract4CharacterTesting(4);
        }
        int expectedPossibleMovingSteps = board.getPossibleMovingSteps() + 2;
        try {
            board.playExpertCard(4);
        } catch (Exception e) {
            fail("playExpertCard() method throw exception " + e);
        }
        assertEquals(expectedPossibleMovingSteps, board.getPossibleMovingSteps());
        assertEquals(CharactersList.MAILMAN, board.getActiveChar());
    }

    @Test
    void testPlayExpertCardException1(){

        board.extract4CharacterTesting(4);

        try{
            board.playExpertCard(4);
        } catch (Exception error){
            System.out.println(error.getMessage());
            fail();
        }

        try{
            board.playExpertCard(4);
        } catch (IllegalStateException e) {
            assertEquals("MAILMAN is already active in this turn.",
                    e.getMessage());
        } catch (Exception e){
            fail();
        }

    }

    @Test
    @SuppressWarnings("empty")
    void testPlayExpertCardException2(){
        int i;
        for (i = 1; i < 13; i++) {
            if (board.getAvailableCharacterCards().get(i) == null)
                try {
                    board.playExpertCard(i, Arrays.asList());
                }catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }catch (Exception others){
                    fail("only the first exception should be called.");
                }

        }

    }

    @Test
    @SuppressWarnings("empty")
    void testPlayExpertCardException3(){
        board.extract4CharacterTesting(4);
        try {
            board.playExpertCard(4);
        }catch (Exception e){
            System.out.println(e.getMessage());
            fail();
        }

        try {
            board.playExpertCard(4);
        } catch (IllegalStateException | StudentException e) {
            assertEquals("MAILMAN is already active in this turn.", e.getMessage());
        }

        for (int i = 1; i < 13; i++) {
            if( i == 4 ) i++;//skipping the MailMan
            if(board.getAvailableCharacterCards().get(i) == null);
                try {
                    board.playExpertCard(i, Arrays.asList());
                } catch (IllegalStateException e){
                    assertEquals("Not possible to play " + CharactersList.getChar(i) + " card. During this turn MAILMAN is already active.",
                                /*actual*/e.getMessage());
                } catch (CoinException | StudentException e){
                    fail();
                }
        }
    }

    @Test
    @SuppressWarnings("empty")
    void testPlayExpertCardException4() {
        ExpertCastle currentPlayerCastle = (ExpertCastle) board.getCastle(board.getCurrentPlayer());
        currentPlayerCastle.payCharacter(1);
        board.extract4CharacterTesting(4);
        try {
            board.playExpertCard(4);
            fail();
        } catch (CoinException e) {
            assertEquals("You had only 0 coins, while 1 coin was needed.",
                    e.getMessage());
        } catch (Exception e) {
            fail();
        }
        board.extract4CharacterTesting(3);
        try {
            board.playExpertCard(3, Arrays.asList());
            fail();
        } catch (CoinException e) {
            assertEquals("You had only 0 coins, while 3 coins were needed.",
                    e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}