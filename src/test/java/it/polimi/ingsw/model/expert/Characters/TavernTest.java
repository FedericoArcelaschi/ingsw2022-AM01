package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Board;
import junit.framework.TestCase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TavernTest extends TestCase {
    Board board = new Board("a", "b");
    private Tavern tavern = new Tavern(board);

    public void testExtraction() {
        List<Generic> expCards = new ArrayList<>();
        expCards = tavern.extract();
        assertEquals(expCards.size(), 3);
    }
}