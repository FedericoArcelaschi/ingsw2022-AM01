package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.CoinException;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertCastle;
import it.polimi.ingsw.model.expert.ExpertIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 *test for eighth character
 */
class InfluenceTestKnight {

    private ExpertBoard board;
    @BeforeEach
    void setUp() {
        Turn t = new Turn(Arrays.asList("lorenzo", "federico"));
        board = new ExpertBoard("lorenzo", "federico", t);
        board.extract4CharacterTesting(8);
    }

    @Test
    void testApplyEffect() throws StudentException, CoinException {
        board.getCastleMap().get("lorenzo").addStudentsInDiningRoom(Arrays.asList(Color.PINK, Color.PINK, Color.PINK));
        assertEquals(2,((ExpertCastle) board.getCastleMap().get("lorenzo")).getCoins());
        ExpertIsland island = (ExpertIsland) board.getIslandList().get(7);
        island.addStudent(
                Map.of(Color.PINK,5,
                Color.GREEN, 2));
        Map<Color, Team> professorsMap = new HashMap<>();
        assertEquals(0, island.calculateInfluence(professorsMap).get(Team.WHITE));
        professorsMap.put(Color.PINK, Team.WHITE);
        professorsMap.put(Color.GREEN, Team.BLACK);
        assertEquals(5, island.calculateInfluence(professorsMap).get(Team.WHITE));
        assertEquals(2, island.calculateInfluence(professorsMap).get(Team.BLACK));
        island.setOwnership(Team.BLACK);
        board.playExpertCard(8);
        assertEquals(3, island.calculateInfluence(professorsMap).get(Team.BLACK));
        assertEquals(2, island.calculateInfluenceNoTowers(professorsMap).get(Team.BLACK));
    }
}