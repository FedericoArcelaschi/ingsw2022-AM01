package it.polimi.ingsw.model.expert.Characters;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class _CharactersListTest {
    @Test
    public void testGetCost() {
        assertEquals(CharactersList.values()[0].getCost(), 1);
    }
    @Test
    public void testValues() {
        assertEquals(CharactersList.values()[0], CharactersList.MONK);
    }
    @Test
    public void testValueOf() {
        assertEquals(CharactersList.valueOf("WITCH"), CharactersList.WITCH);
    }
    @Test
    public void testName(){assertEquals("centaur", CharactersList.CENTAUR.name().toLowerCase(),
            "test of ENUM .name() method");}
}