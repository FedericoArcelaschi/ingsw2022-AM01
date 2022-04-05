package it.polimi.ingsw.model.expert.Characters;

import junit.framework.TestCase;

import java.util.List;

public class CharactersListTest extends TestCase {

    public void testGetCost() {
        assertEquals(CharactersList.values()[0].getCost(), 1);
    }

    public void testValues() {
        assertEquals(CharactersList.values()[0], CharactersList.MONK);
    }

    public void testValueOf() {
        assertEquals(CharactersList.valueOf("WITCH"), CharactersList.WITCH);
    }
}