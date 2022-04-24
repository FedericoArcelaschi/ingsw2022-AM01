package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test for the fifth character.
 */
class BlockTestWitch {

    private Generic witchChar;

    @BeforeEach
    void setUp() {
        Tavern t = new Tavern();
        witchChar = t.extract4testing(5);
    }

    @Test
    void testApplyEffect() throws StudentException {
        ExpertIsland island = new ExpertIsland();
        assertFalse(island.isBLocked());
        Map<Parameters, Object> parameterMap
                = new HashMap<>(
                Map.of(Parameters.ISLAND, island));
        witchChar.applyEffect(parameterMap);
        assertTrue(island.isBLocked());
    }

    @Test
    void testApplyEffectErrorOutOfBlockTiles() throws NoSuchStudentException, TooManyStudentsException {
        ExpertBoard board = new ExpertBoard("giovanni", "lorenzo", new Turn(Arrays.asList("giovanni", "lorenzo")));
        Map<Parameters, Object> parameterMap = new HashMap<>();
        ExpertIsland island;
        for (int i = 0; i < 4; i++) {
            island = (ExpertIsland) board.getIslandList().get(i);
            parameterMap.put(Parameters.ISLAND, island);
            witchChar.applyEffect(parameterMap);
            assertTrue(island.isBLocked());
        }
        island = (ExpertIsland) board.getIslandList().get(4);
        parameterMap.put(Parameters.ISLAND, island);
        try {
            witchChar.applyEffect(parameterMap);
        } catch (IllegalArgumentException e) {
            assertEquals("4 islands are already blocked", e.getMessage());
        } catch (Exception other) {
            fail();
        }
        assertFalse(island.isBLocked());
    }

    @Test
    void testApplyEffectErrorAlreadyBlocked() throws NoSuchStudentException, TooManyStudentsException {
        ExpertIsland island = new ExpertIsland();
        witchChar.applyEffect(Map.of(Parameters.ISLAND, island));
        assertTrue(island.isBLocked());
        try {
            witchChar.applyEffect(Map.of(Parameters.ISLAND, island));
        } catch (IllegalArgumentException e) {
            assertEquals("Island is already blocked", e.getMessage());
        }
        System.out.println(
        assertThrowsExactly(IllegalArgumentException.class,
                () -> witchChar.applyEffect(Map.of(Parameters.ISLAND, island))).getMessage());
    }
}