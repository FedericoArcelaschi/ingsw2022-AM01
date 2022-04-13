package it.polimi.ingsw.model.expert.Characters;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharactersListTest{
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
}