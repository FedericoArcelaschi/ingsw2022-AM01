package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ActionTestMailMan {
    private ExpertBoard board;
    private Generic mailmanChar;

    @BeforeEach
    void setUp() {
        Turn t = new Turn(Arrays.asList("LolloBenzo", "GiaCarte"));
        board = new ExpertBoard("LolloBenzo", "GiaCarte", t);
        mailmanChar = board.extract4CharacterTesting(4);
    }

    @Test
    void applyEffectTest() throws NoSuchStudentException, TooManyStudentsException {
        Map<Parameters, Object> parameterMap =
                new HashMap<>(Map.of(
                        Parameters.MOVE, 10));
        mailmanChar.applyEffect(parameterMap);
        assertEquals(12, parameterMap.get(Parameters.MOVE));
    }

    @Test
    void applyEffectThroughBoard() throws NotYourTurnException, NoSuchStudentException, TooManyStudentsException {
        board.playCard("LolloBenzo", 4);
        int possibleSteps = board.getPossibleMovingSteps();
        assertEquals(2, possibleSteps);
        board.playExpertCard(4);
        assertEquals(possibleSteps + 2, board.getPossibleMovingSteps());
    }

}