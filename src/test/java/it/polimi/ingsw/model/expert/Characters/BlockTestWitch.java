package it.polimi.ingsw.model.expert.characters;

import it.polimi.ingsw.model.BoardFactory;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.BlockedIsland;
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

    private MasterCharacter witchChar;

    @BeforeEach
    void setUp() {
        Tavern t = new Tavern();
        witchChar = t.extract4testing(5);
    }

    @Test
    void testApplyEffect() throws StudentException {
        ExpertIsland island = new ExpertIsland();
        assertFalse(island.isBlocked());
        Map<PossibleParameter, Object> parameterMap
                = new HashMap<>(
                Map.of(PossibleParameter.ISLAND, island));
        witchChar.applyEffect(parameterMap);
        island = (ExpertIsland) parameterMap.get(PossibleParameter.ISLAND);
        assertTrue(island.isBlocked());
    }

    @Test
    void testApplyEffectErrorOutOfBlockTiles() throws NoSuchStudentException, TooManyStudentsException {
        ExpertBoard board = (ExpertBoard) BoardFactory.getBoard(Arrays.asList("giovanni", "lorenzo"), true, new Turn(Arrays.asList("giovanni", "lorenzo")));
        Map<PossibleParameter, Object> parameterMap = new HashMap<>();
        ExpertIsland island;
        for (int i = 0; i < 4; i++) {
            island = (ExpertIsland) board.getIslandList().get(i);
            parameterMap.put(PossibleParameter.ISLAND, island);
            witchChar.applyEffect(parameterMap);
            island = (ExpertIsland) parameterMap.get(PossibleParameter.ISLAND);
            assertTrue(island.isBlocked());
        }
        island = (ExpertIsland) board.getIslandList().get(4);
        parameterMap.put(PossibleParameter.ISLAND, island);
        try {
            witchChar.applyEffect(parameterMap);
        } catch (IllegalArgumentException e) {
            assertEquals("4 islands are already blocked", e.getMessage());
        } catch (Exception other) {
            fail();
        }
        assertFalse(island.isBlocked());
    }

    @Test
    void testApplyEffectErrorAlreadyBlocked() {
        ExpertIsland island = new BlockedIsland(new ExpertIsland(), (Block) witchChar);
        try {
            witchChar.applyEffect(Map.of(PossibleParameter.ISLAND, island));
        } catch (IllegalArgumentException e) {
            assertEquals("Island is already blocked", e.getMessage());
        } catch (Exception e){
            fail();
        }
        System.out.println(
        assertThrowsExactly(IllegalArgumentException.class,
                () -> witchChar.applyEffect(Map.of(PossibleParameter.ISLAND, island))).getMessage() +
                ": OK!");
    }
}