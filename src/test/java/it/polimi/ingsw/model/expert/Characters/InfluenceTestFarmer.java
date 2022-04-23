package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertCastle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the second character.
 */
class InfluenceTestFarmer {
    private final Turn t = new Turn(Arrays.asList("Lorenzo", "Giovanni2069"));
    private ExpertBoard board;
    private Map<Color, Team> professorsMap;
    private ExpertCastle castleA, castleB;
    private Generic charInfl;


    @BeforeEach
    void setUp() {
        board = new ExpertBoard("Lorenzo", "Giovanni2069", t);
        board.extract4CharacterTesting(2);
        castleA = (ExpertCastle) board.getCastle("Lorenzo"); //WHITE
        castleB = (ExpertCastle) board.getCastle("Giovanni2069");//BLACK
        charInfl = board.getAvailableCharacterCards().get(2);

    }

    @Test
    void applyEffect() throws Exception { //Lorenzo's turn
        castleB.addStudentsInDiningRoom( //BLACK
                Arrays.asList(
                        Color.YELLOW,
                        Color.YELLOW,
                        Color.YELLOW,
                        Color.RED,
                        Color.PINK,
                        Color.GREEN));
        board.updateProfessorsOwners();
        castleA.addStudentsInDiningRoom( //WHITE - //Lorenzo has 2 coins.
                Arrays.asList(
                        Color.YELLOW,
                        Color.YELLOW,
                        Color.YELLOW,
                        Color.BLUE,
                        Color.GREEN,
                        Color.GREEN));
        board.updateProfessorsOwners();
        professorsMap = board.getProfessorsMap();
        Map<Color, Team> expectedProfessorsMap
                = new HashMap<>(Map.of(
                Color.YELLOW, Team.BLACK,
                Color.GREEN, Team.WHITE,
                Color.PINK, Team.BLACK,
                Color.BLUE, Team.WHITE,
                Color.RED, Team.BLACK));
        assertEquals(expectedProfessorsMap, professorsMap,
                "before applyEffect() use. Initialization check.");
        board.playExpertCard(2);
        professorsMap = board.getProfessorsMap();
        expectedProfessorsMap = Map.of(
                Color.YELLOW, Team.WHITE,
                Color.GREEN, Team.WHITE,
                Color.PINK, Team.BLACK,
                Color.BLUE, Team.WHITE,
                Color.RED, Team.BLACK);
        assertEquals(expectedProfessorsMap, professorsMap,
                "after applyEffect() application.");
    }

}