package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.expert.ExpertIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockTestWitch {
    private Generic witchChar;
    @BeforeEach
    void setUp() {
        Tavern t = new Tavern();
        witchChar = t.extract4testing(5);
    }

    @Test
    void applyEffect() throws StudentException {
        ExpertIsland eIsland = new ExpertIsland();
        assertFalse(eIsland.isBLocked());
        Map<Parameters, Object> parameterMap
                = new HashMap<>(
                Map.of(Parameters.ISLAND, eIsland));
        witchChar.applyEffect(parameterMap);
        assertTrue(eIsland.isBLocked());
    }
}