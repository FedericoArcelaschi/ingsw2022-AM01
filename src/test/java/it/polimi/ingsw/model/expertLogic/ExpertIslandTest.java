package it.polimi.ingsw.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.interfaces.InterfaceAdapter;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.expertLogic.influence.InfluenceComputingExpert;
import it.polimi.ingsw.server.model.baseLogic.influence.Influence;
import it.polimi.ingsw.server.model.baseLogic.influence.Professors;
import it.polimi.ingsw.server.model.expertLogic.influence.InfluenceComputingFunction;
import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.baseLogic.influence.functionalInterfaces.InfluenceComputing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExpertIslandTest {

    private ExpertIsland island;
    private final InfluenceComputing defaultFunction =
        (Island island, Map<StudentColor, Team> professorsMap) -> {
            Map< Team, Integer> influenceMap = new HashMap<>();
            //counts the students
            for (Team t : Team.values()) {
                int influence = 0;
                for (StudentColor c : StudentColor.values()) {
                    if (professorsMap.get(c) == t)
                        influence += island.getStudents().get(c);
                }
                influenceMap.put(t, influence);
            }
            //counts the towers
            Team oldOwner = island.getOwnership();
            if(oldOwner != null) {
                int influence = influenceMap.get(oldOwner) + island.getIslandNumber();
                influenceMap.put(oldOwner, influence);
            }
            return influenceMap;
        };


    @BeforeEach
    void setUp() {
        island = new ExpertIsland(new Island());
    }

    @Test
    void testCalculateInfluence(){
        Professors professors = new Professors(Map.of());
        Influence influence = new Influence(professors);
        island.setOwnership(Team.WHITE);
        assertEquals(1, influence.getInfluenceMap(island).get(Team.WHITE),
                "just one of influence given by the tower");
        island.setOwnership(null);
        assertEquals(0, influence.getInfluenceMap(island).get(Team.WHITE),
                "just one of influence given by the tower");
    }
    @Test
    void testCalculateInfluenceNoTowers() {
        Map<StudentColor, Team> professorsMap
                = new HashMap<>(
                        Map.of(StudentColor.YELLOW, Team.WHITE, //piero's team
                                StudentColor.GREEN, Team.BLACK));//angela's team
        island.setOwnership(Team.WHITE);
        EnumMap<StudentColor, Integer> students = new EnumMap<>(StudentColor.class);
        students.put(StudentColor.YELLOW, 2);
        students.put(StudentColor.GREEN, 3);
        students.put(StudentColor.RED, 10);
        island.addStudent(students);
        InfluenceComputingExpert<?> influenceFunction = InterfaceAdapter.adaptExpertInfluence(defaultFunction);
        Map<Team, Integer> influence = influenceFunction.computeInfluenceMap(island, professorsMap, null);

        assertEquals(3, influence.get(Team.WHITE),
                "2 yellow and no tower");
        assertEquals(3, influence.get(Team.BLACK));
        influenceFunction = InfluenceComputingFunction.CENTAUR.getFunction();
        influence = influenceFunction.computeInfluenceMap(island, professorsMap, null);
        assertEquals(2, influence.get(Team.WHITE),
                "only the two yellow count");


    }
}