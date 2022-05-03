package it.polimi.ingsw.model.expert.characters;

import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.expert.characters.CharactersInfo.getChar;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class _CharactersInfoTest {
    @Test
    public void testGetCost() {
        assertEquals(CharactersInfo.values()[0].getCost(), 1);
    }

    @Test
    public void testValues() {
        assertEquals(CharactersInfo.values()[0], CharactersInfo.MONK);
    }

    @Test
    public void testValueOf() {
        assertEquals(CharactersInfo.valueOf("WITCH"), CharactersInfo.WITCH);
    }

    @Test
    public void testName() {
        assertEquals("centaur", CharactersInfo.CENTAUR.name().toLowerCase(),
                "test of ENUM .name() method");
    }

    @Test
    void testGetChar() {
        assertEquals(CharactersInfo.MONK, getChar(1));
        assertEquals(CharactersInfo.CENTAUR, getChar(6));
    }
}