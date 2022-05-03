package it.polimi.ingsw.model.expert.characters;

import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.expert.characters.CharactersList.getChar;
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
    public void testName() {
        assertEquals("centaur", CharactersList.CENTAUR.name().toLowerCase(),
                "test of ENUM .name() method");
    }

    @Test
    void testGetChar() {
        assertEquals(CharactersList.MONK, getChar(1));
        assertEquals(CharactersList.CENTAUR, getChar(6));
    }
}