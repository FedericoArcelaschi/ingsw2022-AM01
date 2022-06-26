package it.polimi.ingsw.server.model.expertLogic.influence;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains all the possible implementations of <code>InfluenceComputingExpert</code> that are used
 * in the decorator of ExpertInfluence
 */
public enum InfluenceComputingFunction {
    /**
     * The knight gives +2 of influence to the team who played paid the Knight
     */
    KNIGHT(
        (Island island, Map<StudentColor, Team> professorsMap, Team currTeam) -> {
            //TODO: check for valid inputs!
            Map<Team, Integer> influenceMap = new HashMap<>();
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
            //KNIGHT actual effect ->
            int influence = influenceMap.get(currTeam) + 2;
            influenceMap.put(currTeam, influence);
            return influenceMap;
        }
    ),
    /**
     * The centaur doesn't count the towers in the influence
     * -> counts only the students on the island
     */
    CENTAUR(
        (Island island, Map<StudentColor, Team> professorsMap, PossibleParameters ignored) -> {
            Map<Team, Integer> influenceMap = new HashMap<>();
            //counts the students
            for (Team t : Team.values()) {
                int influence = 0;
                for (StudentColor c : StudentColor.values()) {
                    if (professorsMap.get(c) == t)
                        influence += island.getStudents().get(c);
                }
                influenceMap.put(t, influence);
            }
            return influenceMap;
        }
    ),
    /**
     * The cook doesn't count the students of the given color
     */
    COOK(
        (Island island, Map<StudentColor, Team> professorsMap, StudentColor student) -> {
            Map<Team, Integer> influenceMap = new HashMap<>();
            //counts the students
            for (Team t : Team.values()) {
                int influence = 0;
                for (StudentColor c : StudentColor.values())
                    if(c != student)
                        if (professorsMap.get(c) == t)
                            influence += island.getStudents().get(c);
                influenceMap.put(t, influence);
            }
            return influenceMap;
        }
    );

    private final InfluenceComputingExpert<PossibleParameters> function;

    InfluenceComputingFunction(InfluenceComputingExpert<? extends PossibleParameters> function) {
        this.function = (InfluenceComputingExpert<PossibleParameters>) function;
    }
    public  InfluenceComputingExpert<PossibleParameters> getFunction(){
        return function;
    }
}

