package it.polimi.ingsw.server.model.expertLogic.influence;
import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;
import it.polimi.ingsw.server.model.expertLogic.ExpertCastle;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ExpertProfessors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class influenceTesting {

    private Castle castle1, castle2;
    private String player1 = "Guido", player2 = "Benz";
    private ExpertInfluence influence;
    private Island island;
    private Map<String, Castle> castleMap;
    @BeforeEach
    void setUp() {
        castle1 = new ExpertCastle(Team.WHITE, 2, Bag.extractMany(7));
        castle2 = new ExpertCastle(Team.BLACK, 2, Bag.extractMany(7));
        castleMap = new HashMap<>(
                Map.of(player1, castle1, player2, castle2));
        influence = new ExpertInfluence(new ExpertProfessors(castleMap));

    }

    @Test
    void decorateTest() {
        island = new ExpertIsland(new Island());
        Map<Team, Integer> teamIntegerMap = influence.getInfluenceMap(island);
        for (Team team : Team.values()) {
            assertEquals(0, teamIntegerMap.get(team));
        }
        decorateKnightTest();
        resetTest();
    }

    void decorateKnightTest() {
        InfluenceComputingExpert<PossibleParameters> function
                = InfluenceComputingFunction.KNIGHT.getFunction();
        influence.decorateInfluence(function, Team.WHITE);

        Map<Team, Integer> teamIntegerMap = influence.getInfluenceMap(island);
        assertEquals(2, teamIntegerMap.get(Team.WHITE));
    }

    void resetTest() {
        influence.reset();
        Map<Team, Integer> teamIntegerMap = influence.getInfluenceMap(island);
        for (Team team : Team.values()) {
            assertEquals(0, teamIntegerMap.get(team));
        }
    }
}
