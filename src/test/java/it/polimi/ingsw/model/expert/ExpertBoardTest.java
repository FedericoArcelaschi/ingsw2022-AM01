package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.BoardFactory;
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
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;


public class ExpertBoardTest{

    private static Board board;
    private static ExpertBoard expertBoard;

    @BeforeEach
    public void setUp() throws Exception {
        Turn t = new Turn(Arrays.asList("Lorenzo", "Federico", "Giovanni"));
        board = BoardFactory.getBoard(Arrays.asList("Lorenzo", "Federico", "Giovanni"), true, t);
        board.playCard("Lorenzo", 3);
        expertBoard = (ExpertBoard) board;
    }

    @Test
    public void testSetup4CharacterTesting() {
        expertBoard.extract4CharacterTesting(1);
        assertTrue(expertBoard.getAvailableCharacterCards()
                        .contains(
                        new Student(1, expertBoard.getBag())
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
        if (expertBoard.getAvailableCharacterCards().get(4) == null) {
            assertThrows(IllegalArgumentException.class, () -> expertBoard.playExpertCard(4),
                    "Mailman not in extracted");
            expertBoard.extract4CharacterTesting(4);
        }
        int expectedPossibleMovingSteps = expertBoard.getPossibleMovingSteps() + 2;
        try {
            expertBoard.playExpertCard(4);
        } catch (Exception e) {
            fail("playExpertCard() method throw exception " + e);
        }
        assertEquals(expectedPossibleMovingSteps, expertBoard.getPossibleMovingSteps());
        assertEquals(CharactersList.MAILMAN, expertBoard.getActiveChar());
    }

    @Test
    void testPlayExpertCardException1(){

        expertBoard.extract4CharacterTesting(4);

        try{
            expertBoard.playExpertCard(4);
        } catch (Exception error){
            System.out.println(error.getMessage());
            fail();
        }

        try{
            expertBoard.playExpertCard(4);
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
            if (expertBoard.getAvailableCharacterCards().get(i) == null)
                try {
                    expertBoard.playExpertCard(i, Arrays.asList());
                }catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }catch (Exception others){
                    fail("only the first exception should be called.");
                }

        }

    }

    @Test
    @SuppressWarnings("empty")
    void testPlayExpertCardException3() throws CoinException {
        expertBoard.extract4CharacterTesting(4);
        try {
            expertBoard.playExpertCard(4);
        }catch (Exception e){
            System.out.println(e.getMessage());
            fail();
        }

        try {
            expertBoard.playExpertCard(4);
        } catch (IllegalStateException | StudentException e) {
            assertEquals("MAILMAN is already active in this turn.", e.getMessage());
        } catch (CoinException e) {
            fail();
        }

        for (int i = 1; i < 13; i++) {
            if( i == 4 ) i++;//skipping the MailMan
            if(expertBoard.getAvailableCharacterCards().get(i) == null);
                try {
                    expertBoard.playExpertCard(i, Arrays.asList());
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
        ExpertCastle currentPlayerCastle = (ExpertCastle) expertBoard.getCastle(expertBoard.getCurrentPlayer());
        currentPlayerCastle.payCharacter(1);
        expertBoard.extract4CharacterTesting(4);
        try {
            expertBoard.playExpertCard(4);
            fail();
        } catch (CoinException e) {
            assertEquals("You had only 0 coins, while 1 coin was needed.",
                    e.getMessage());
        } catch (Exception e) {
            fail();
        }
        expertBoard.extract4CharacterTesting(3);
        try {
            expertBoard.playExpertCard(3, Arrays.asList());
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