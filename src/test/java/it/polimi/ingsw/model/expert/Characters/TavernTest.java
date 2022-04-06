package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Board;
import junit.framework.TestCase;

import java.lang.reflect.Array;
import java.util.*;

public class TavernTest extends TestCase {
    Board board = new Board("a", "b");
    private Tavern tavern = new Tavern(board);

    public void testExtraction() {
        Map<Integer, Generic> expCards = new HashMap<>();
        expCards = tavern.extract();
        assertEquals(expCards.size(), 3);
    }
}