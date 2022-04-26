package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.BoardFactory;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * test for the fourth character.
 */

class ActionTestMailMan {
    private ExpertBoard board;

    @BeforeEach
    void setUp() {
        Turn t = new Turn(Arrays.asList("LolloBenzo", "GiaCarte"));
        board = (ExpertBoard) BoardFactory.getBoard(Arrays.asList("LolloBenzo", "GiaCarte"), true, t);
        board.extract4CharacterTesting(4);
    }

    @Test
    void testApplyEffect() {
        try {
            board.playCard("LolloBenzo", 4);
        } catch (NotYourTurnException e) {
            System.out.println("exception:" + e.getMessage());
            fail();
        }
        int expectedPossibleMovingDistance = board.getPossibleMovingSteps() + 2;
        try {
            board.playExpertCard(4);
        } catch (Exception wrongException) {
            System.out.println(wrongException.getMessage());
            fail();
        }
        //--FIXME--
        assertEquals(expectedPossibleMovingDistance, board.getPossibleMovingSteps(),
                "moving distance must be increased by two");
    }

}