package it.polimi.ingsw.model.expert.influence;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.expert.influence.professor.ProfessorsComputingExpert;
import it.polimi.ingsw.model.influence.Influence;
import it.polimi.ingsw.model.influence.Professors;

import java.util.Map;

/**
 * Decorator for the Influence. Needed to assign a parameter and an implementation of InfluenceComputingFunction
 * @param <T> is to assign from the Character Function. Is needed to "change" the right value in the <Code>InfluenceComputingFunction</Code>
 */

public class InfluenceDecorated<T> extends InfluenceExpert {
    private final InfluenceExpert influence;
    private final InfluenceComputingExpert<T> function;
    private final T var;


    public InfluenceDecorated(InfluenceExpert influence, InfluenceComputingExpert<T> function, T var) {
        super();
        this.influence = influence;
        this.function = function;
        this.var = var;
    }

    //End of turn
    @Override
    public Influence undecorate() {
        return influence;
    }

    /**
     * Computes the InfluenceMap with the right function.
     * @param island to compute the influence onto
     * @return influenceMap a map containing the sum of all influence for each player
     */

    @Override
    public Map<Team, Integer> getInfluenceMap(Island island) {
        influenceMap = function.computeInfluenceMap(island, getProfessorsMap(), var);
        return influence.getInfluenceMap(island);
    }

    /**
     * Method that works as in base mode game.
     */
    @Override
    public void updateProfessors() {
        influence.updateProfessors();
    }

    /**
     * Method that works as in base mode game.
     */
    @Override
    public Map<Color, Team> getProfessorsMap() {
        return influence.getProfessorsMap();
    }

    /**
     * Methods for Professors - Expert Mode
     * Not really needed in the decorator but is a good practice to include them to prevent from future bugs.
     * As of right now only one between the professors or the influence can be decorated at the same time.
     */
    @Override
    public void decorateProfessors(ProfessorsComputingExpert<Team> function, Team var) {
        influence.decorateProfessors(function, var);
    }

    /**
     * Methods for Professors - Expert Mode
     * Not really needed in the decorator but is a good practice to include them to prevent from future bugs.
     * As of right now only one between the professors or the influence can be decorated at the same time.
     */
    @Override
    public void unDecorateProfessors() {
        influence.unDecorateProfessors();
    }
}
