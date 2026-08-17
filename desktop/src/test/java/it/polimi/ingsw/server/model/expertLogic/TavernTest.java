package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.Bag;
import it.polimi.ingsw.server.model.baseLogic.Board;
import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.expertLogic.character.StandardCharacter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class TavernTest{

    @Test
    public void testExtraction() {
        Turn t = new Turn(Arrays.asList("a","b"));
        Board board = BoardFactory.getBoard(Arrays.asList("a","b"), true, RandomGenerator.getDefault().nextLong());
        Tavern tavern = new Tavern(new Bag(20, 1));

        List<StandardCharacter> expCards;
        expCards = tavern.extract().values().stream().toList();
        board.getBag().extract();
        assertEquals(3, expCards.size());
        for (StandardCharacter sc : expCards) {
            assertNotNull(sc);
        }
    }
}