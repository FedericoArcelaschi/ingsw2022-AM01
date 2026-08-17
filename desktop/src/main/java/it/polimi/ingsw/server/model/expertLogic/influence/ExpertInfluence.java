package it.polimi.ingsw.server.model.expertLogic.influence;

import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;
import it.polimi.ingsw.server.model.baseLogic.interfaces.InterfaceAdapter;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ProfessorsComputingExpert;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ExpertProfessors;
import it.polimi.ingsw.server.model.baseLogic.influence.Influence;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

/**
 * Expert mode influence. Can use multiple functions to compute the influenceMap.
 */
public class ExpertInfluence extends Influence {

    private final ExpertProfessors professors;
    private InfluenceComputingExpert<PossibleParameters> function;
    private PossibleParameters var;

    public ExpertInfluence(ExpertProfessors professors) {
        super(professors);
        this.professors = professors;
        function = InterfaceAdapter.adaptExpertInfluence(defaultFunction);
        var = null;
    }

    /**
     * @param function custom function that will be used in the next turn's influence computing
     * @param var a parameter that may be used to modify the influence computing
     */
    public void decorateInfluence(InfluenceComputingExpert<PossibleParameters> function, @Nullable PossibleParameters var) {
        this.function = function;
        this.var = var;
    }

    /**
     * @param function custom function that will be used in the next turn professors assigning
     * @param var a parameter that may be used to modify the professor location.
     */
    public void decorateProfessors(ProfessorsComputingExpert<PossibleParameters> function, @Nullable PossibleParameters var) {
        professors.decorate(function, var);
    }

    /**
     * End of turn: function reset method.
     */
    @Override
    public void reset() {
        professors.reset();
        function = InterfaceAdapter.adaptExpertInfluence(defaultFunction);
        var = null;
    }

    @Override
    public Map<Team, Integer> getInfluenceMap(Island island) {
        return function.computeInfluenceMap(island, getProfessorsMap(), var);
    }
}
