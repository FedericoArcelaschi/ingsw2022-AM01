package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.expert.ExpertBoard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TavernTest{
    private static Turn t;
    private static ExpertBoard board;
    private static Tavern tavern;

    @BeforeAll
    static void beforeAll() {
        t = new Turn(Arrays.asList("a","b"));
        board = new ExpertBoard("a", "b", t);
        tavern = new Tavern(board.getBag());
    }

    @Test
    public void testExtraction() {
        List<Generic> expCards = new ArrayList<>();
        expCards = tavern.extract();
        board.getBag().extract();
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
        System.out.println(expCards);
    }

    @Test
    public void testExtract4Testing(){
        List<Generic> expertModeCharacters = new ArrayList<>();
        List<CharactersList> expertModeCharactersList4Comparison = new ArrayList<>();

        for (int i = 1; i < 13; i++) {
            System.out.println(tavern.extract4testing(i));
            expertModeCharacters.add(tavern.extract4testing(i));
        }
        expertModeCharactersList4Comparison.addAll(Arrays.asList(CharactersList.MONK));

    }
}