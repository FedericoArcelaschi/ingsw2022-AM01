package it.polimi.ingsw.model.expert.influence;

import com.sun.jdi.Type;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Contains all the possible implementations of <code>InfluenceComputingExpert</code> that are used
 * in the decorator of InfluenceExpert
 */
public enum InfluenceComputingFunction {
    KNIGHT( //+2 of influence to the team who played paid the Knight
        (Island island, Map<Color, Team> professorsMap, Team currTeam) -> {
            Map<Team, Integer> influenceMap = new HashMap<>();
            //counts the students
            for (Team t:Team.values()) {
                int influence = 0;
                for (Color c : Color.values()) {
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
            //KNIGHT actual effect ->
            int influence = influenceMap.get(currTeam) + 2;
            influenceMap.put(oldOwner, influence);
            return influenceMap;
        }
    ),
    CENTAUR( //doesn't count the towers
        (Island island, Map<Color, Team> professorsMap, Color student) -> {
            Map<Team, Integer> influenceMap = new HashMap<>();
            //counts the students
            for (Team t : Team.values()) {
                int influence = 0;
                for (Color c : Color.values()) {
                    if (professorsMap.get(c) == t)
                        influence += island.getStudents().get(c);
                }
                influenceMap.put(t, influence);
            }
            return influenceMap;
        }
    ),
    COOK( //doesn't count the students of the given color
        (Island island, Map<Color, Team> professorsMap, Color student) -> {
            Map<Team, Integer> influenceMap = new HashMap<>();
            //counts the students
            for (Team t : Team.values()) {
                int influence = 0;
                for (Color c : Color.values()) {
                    if(c != student)
                        if (professorsMap.get(c) == t)
                            influence += island.getStudents().get(c);
                }
                influenceMap.put(t, influence);
            }
            return influenceMap;
        }
    );

    private final InfluenceComputingExpert<?> function;

    InfluenceComputingFunction(InfluenceComputingExpert<? extends PossibleParameters> function) {
        this.function = function;
    }
    public InfluenceComputingExpert<?> getFunction(){
        return function;
    }
}

