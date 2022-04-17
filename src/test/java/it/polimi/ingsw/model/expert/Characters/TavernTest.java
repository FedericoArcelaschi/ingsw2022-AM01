package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.expert.ExpertBoard;
import org.junit.jupiter.api.BeforeAll;
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
    }

    
    @Test
    public void testExtract4Testing(){
        List<CharactersList> expertModeCharacters = new ArrayList<>();
        List<CharactersList> expertModeCharactersList4Comparison = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            expertModeCharacters.add(tavern.extract4testing(i).getCharacterName());
        }
        expertModeCharactersList4Comparison
            .addAll(Arrays.asList(
                CharactersList.MONK,
                CharactersList.FARMER,
                CharactersList.GUARD,
                CharactersList.MAILMAN,
                CharactersList.WITCH,
                CharactersList.CENTAUR,
                CharactersList.JESTER,
                CharactersList.KNIGHT,
                CharactersList.COOK,
                CharactersList.STORYTELLER,
                CharactersList.QUEEN,
                CharactersList.TAXMAN));
        assertEquals(expertModeCharactersList4Comparison, expertModeCharacters,
                "both lists should contain all characters names");//TODO: understand why lc is null (in debugging isn't)
    }
}