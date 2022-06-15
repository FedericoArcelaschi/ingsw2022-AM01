package it.polimi.ingsw.server.model.expertLogic;


import it.polimi.ingsw.server.model.baseLogic.Bag;
import it.polimi.ingsw.server.model.baseLogic.BoardFactory;
import it.polimi.ingsw.server.model.baseLogic.Turn;
import it.polimi.ingsw.server.model.baseLogic.Board;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.BlockCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StudentCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.Tavern;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TavernTest{
    private static Turn t;
    private static Board board;
    private static Tavern tavern;

    @BeforeAll
    static void beforeAll() {
        t = new Turn(Arrays.asList("a","b"));
        board = BoardFactory.getBoard(List.of("a","b"), true, RandomGenerator.getDefault().nextLong());
        tavern = new Tavern(new Bag(20, 1));
    }

    @Test
    public void testExtraction() {
        Map<CharacterUtility, StandardCharacter> expCardMap;
        expCardMap = tavern.extract();
        List<StandardCharacter> expCards = expCardMap.values().stream().toList();
        assertEquals(3, expCards.size());
        assertTrue(expCards.contains(new StudentCharacter(1, new Bag(1,1)))
                || expCards.contains(new StudentCharacter(7, new Bag(1,1)))
                || expCards.contains(new StudentCharacter(10))
                || expCards.contains(new StudentCharacter(12))
                || expCards.contains(new StudentCharacter(11,new Bag(1,1)))
                || expCards.contains(new StandardCharacter(2))
                || expCards.contains(new StandardCharacter(6))
                || expCards.contains(new StandardCharacter(8))
                || expCards.contains(new StandardCharacter(9))
                || expCards.contains(new StandardCharacter(3))
                || expCards.contains(new StandardCharacter(4))
                || expCards.contains(new BlockCharacter(5)
                )
        );
        int i = 0;
        for (StandardCharacter a : expCards) {
            if (a != null)
                i++;
        }
        assertEquals(3, i);
    }


    @Test
    public void testExtract4Testing() {
        List<CharacterUtility> expertModeCharacters = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            expertModeCharacters.add(tavern.extract4testing(i).getCharacterUtility());
        }
        //System.out.println(tavern.extract4testing(3).getCharacterName().name());
        List<CharacterUtility> expertModeCharactersInfo4Comparison
                = new ArrayList<>(
                        Arrays.asList(
                                CharacterUtility.MONK,
                                CharacterUtility.FARMER,
                                CharacterUtility.GUARD,
                                CharacterUtility.MAILMAN,
                                CharacterUtility.WITCH,
                                CharacterUtility.CENTAUR,
                                CharacterUtility.JESTER,
                                CharacterUtility.KNIGHT,
                                CharacterUtility.COOK,
                                CharacterUtility.STORYTELLER,
                                CharacterUtility.QUEEN,
                                CharacterUtility.TAXMAN));
        assertEquals(expertModeCharactersInfo4Comparison, expertModeCharacters,
                "both CharacterList-lists should contain all characters names");
    }
}