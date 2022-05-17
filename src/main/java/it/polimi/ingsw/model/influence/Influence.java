package it.polimi.ingsw.model.influence;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.influence.functionalInterfaces.InfluenceComputing;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to encapsulate the logic for Influence computing and the Data Type.
 * Is also useful to implement new features with inheritance
 */
public class Influence implements InfluenceComputing {
    protected Map<Team, Integer> influenceMap = new HashMap<>();
    protected Professors professorsMap;


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
        influenceMap = computeInfluenceMap(island, professorsMap.getProfessorsAssigning());    //there uses the default computeInfluenceMap method.
        return influenceMap;
    }

    /**
     * "Outside" of this class there is no reference to the professors map,
     * therefore there is the need to recall the method from this class.
     */
    public void updateProfessors() {
        professorsMap.updateProfessorsAssigning();
    }

    @Contract(pure = true)
    public Map<Color, Team> getProfessorsMap() {
        return professorsMap.getProfessorsAssigning();
    }

    //FOR EXPERT BOARD INHERITANCE
    public Influence() {

    }
}
