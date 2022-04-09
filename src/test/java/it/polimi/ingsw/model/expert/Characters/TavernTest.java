package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Board;
import junit.framework.TestCase;

import java.lang.reflect.Array;
import java.util.*;

public class TavernTest extends TestCase {
    Board board = new Board("a", "b");
    private Tavern tavern = new Tavern(board);

    public void testExtraction() {
        List<Generic> expCards = new ArrayList<>();
        expCards = tavern.extract();
        assertEquals(13, expCards.size());
        assertEquals(true,
                expCards.contains(new Student(1, board.getBag()))
                    ||expCards.contains(new Student(7, board.getBag()))
                    ||expCards.contains(new Student(10, board.getBag()))
                    ||expCards.contains(new Student(11, board.getBag()))
                    ||expCards.contains(new Influence(2))
                    ||expCards.contains(new Influence(6))
                    ||expCards.contains(new Influence(8))
                    ||expCards.contains(new Influence(9))
                    ||expCards.contains(new Action(3))
                    ||expCards.contains(new Action(4))
                    ||expCards.contains(new Block(5))
                );
        int i = 0;
        for (Generic a: expCards) {
            if (a != null)
                i++;
        }
        assertEquals(3,i);
    }
}