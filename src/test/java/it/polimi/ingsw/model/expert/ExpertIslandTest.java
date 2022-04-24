package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExpertIslandTest {

    private ExpertIsland i;

    @BeforeEach
    void setUp() {
        i = new ExpertIsland();
    }

    @Test
    void testCalculateInfluece(){
        Map<Color, Team> professorsMap = new HashMap<>();
        i.setOwnership(Team.WHITE);
        assertEquals(1, i.calculateInfluence(professorsMap).get(Team.WHITE),
                "just one of influence given by the tower");
        i.setOwnership(null);
        assertEquals(0, i.calculateInfluence(professorsMap).get(Team.WHITE),
                "just one of influence given by the tower");
    }
    @Test
    void testCalculateInfluenceNoTowers() {
        Map<Color, Team> professorsMap
                = new HashMap<>(
                        Map.of(Color.YELLOW, Team.WHITE, //piero's team
                                Color.GREEN, Team.BLACK));//angela's team
        i.setOwnership(Team.WHITE);
        i.addStudent(
                Map.of(Color.YELLOW, 2,
                        Color.GREEN, 3,
                        Color.RED, 10
                ));
        Map<Team, Integer> influence;
        influence = i.calculateInfluence(professorsMap);
        assertEquals(3, influence.get(Team.WHITE),
                "2 yellow and a tower");
        assertEquals(3, influence.get(Team.BLACK));
        influence = i.calculateInfluenceNoTowers(professorsMap);
        assertEquals(2, influence.get(Team.WHITE),
                "only the two yellow count");


    }
}