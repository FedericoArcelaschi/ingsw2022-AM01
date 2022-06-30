package it.polimi.ingsw.server.model.baseLogic.influence;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.baseLogic.influence.functionalInterfaces.InfluenceComputing;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * Default (Base-Game-Mode) influence
 * This class is used to encapsulate the logic for Influence computing and the Data Type.
 * Is also useful to implement new features with inheritance
 */
public class Influence {

    private final Professors professorsMap;

    public Influence(Professors professorsMap) {
        this.professorsMap = professorsMap;
    }

    /**
     * Method computes with the right function the influence
     * @param island to compute the influence onto
     * @return map team -> influence
     */
    @Contract(pure = true)
    public Map<Team, Integer> getInfluenceMap(Island island) {
        return defaultFunction.computeInfluenceMap(island, professorsMap.getProfessorsAssigning());
    }

    /**
     * "Outside" of this class there is no reference to the professors map,
     * therefore there is the need to call the method from this class.
     */
    public void updateProfessors() {
        professorsMap.updateProfessorsAssigning();
    }

    @Contract(pure = true)
    public Map<StudentColor, Team> getProfessorsMap() {
        return professorsMap.getProfessorsAssigning();
    }

    public void reset() {}

    protected final InfluenceComputing defaultFunction =
    /**
     * Default function to compute influence in the base game mode or in the expertLogic game without special effects.
     * @param island        the island to compute the influence on
     * @param professorsMap the professors map used to compute the influence
     * @return the influenceMap that contains the winning team
     */
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
                influenceMap.replace(oldOwner, influence);
            }
            return influenceMap;
        };

}
