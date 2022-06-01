package it.polimi.ingsw.model.expertLogic.characters;

import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility.getChar;
import static org.junit.jupiter.api.Assertions.*;

class CharactersInfoTest {

    @Test
    public void testGetCost() {
        assertEquals(1, CharacterUtility.values()[0].getCost());
    }

    @Test
    public void testValues() {
        assertEquals(CharacterUtility.values()[0], CharacterUtility.MONK);
    }

    @Test
    public void testValueOf() {
        assertEquals(CharacterUtility.valueOf("WITCH"), CharacterUtility.WITCH);
    }

    @Test
    public void testName() {
        assertEquals("centaur", CharacterUtility.CENTAUR.name().toLowerCase(),
                "test of ENUM .name() method");
    }

    @Test
    void testGetChar() {
        assertEquals(CharacterUtility.MONK, getChar(1));
        assertEquals(CharacterUtility.CENTAUR, getChar(6));
    }

}