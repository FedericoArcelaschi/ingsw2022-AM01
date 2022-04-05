package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Board;
import junit.framework.TestCase;

import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;
import java.util.List;

public class TavernTest extends TestCase {
    Board board = new Board("a", "b");
    private Tavern tavern = new Tavern(board.getBag());

    public void testExtract() {
        List<ExpertCharacter> expCards = new ArrayList<>();
        expCards = tavern.extract();
        assertEquals(expCards.size(), 3);
    }
}