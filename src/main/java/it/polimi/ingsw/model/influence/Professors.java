package it.polimi.ingsw.model.influence;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.influence.functionalInterfaces.ProfessorComputing;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is useful to encapsulate the Professors implementation and logic.
 * Is also useful to implement new features.
 */
public class Professors implements ProfessorComputing {

    protected Map<Color, Team> professorsMap; //TODO: try to make this final.
    protected final Map<String, Castle> castleMap;

    public Professors(Map<String, Castle> castleMap) {
        this.castleMap = castleMap;
        this.professorsMap = new HashMap<>();
        for (Color c: Color.values()) {
            professorsMap.put(c, null);
        }
    }

    /**
     * Method needed to update the professorsMap when a student is added to the Dining Room
     */
    public void updateProfessorsAssigning() {
        professorsMap = computeProfessorsMap(castleMap, professorsMap);
    }

    @Contract(pure = true)
    public Map<Color, Team> getProfessorsAssigning() {
        this.updateProfessorsAssigning();
        return professorsMap;
    }

    /**
     *Method for inheritance
     */
    public Professors() {
        castleMap = null;
    }

    //TODO: computeProfessorsMap should be protected


    @Override
    public final Map<Color, Team> computeProfessorsMap(Map<String, Castle> castle, Map<Color, Team> professorsMap) {
        return ProfessorComputing.super.computeProfessorsMap(castle, professorsMap);
    }

    public Professors undecorate(){//terrific patter. FIXME
        return this;
    }
}
