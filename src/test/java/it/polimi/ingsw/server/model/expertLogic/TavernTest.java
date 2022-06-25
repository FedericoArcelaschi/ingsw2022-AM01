package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.Bag;
import it.polimi.ingsw.server.model.baseLogic.Board;
import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.*;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class TavernTest{
    private static Turn t;
    private static Board board;
    private static Tavern tavern;

    @BeforeAll
    static void beforeAll() {
        t = new Turn(Arrays.asList("a","b"));
        board = BoardFactory.getBoard(Arrays.asList("a","b"), true);
        tavern = new Tavern(new Bag(20, 1));
    }

    @Test
    public void testExtraction() {
        List<StandardCharacter> expCards;
        expCards = tavern.extract().values().stream().toList();
        board.getBag().extract();
        assertEquals(3, expCards.size());
        for (StandardCharacter sc : expCards) {
            assertNotNull(sc);
        }
    }
}