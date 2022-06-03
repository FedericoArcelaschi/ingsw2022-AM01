package it.polimi.ingsw.server.model.expertLogic.influence;

import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.baseLogic.interfaces.InterfaceAdapter;
import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ProfessorsComputingExpert;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ExpertProfessors;
import it.polimi.ingsw.server.model.baseLogic.influence.Influence;

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

    public void decorateInfluence(InfluenceComputingExpert<PossibleParameters> function,
                                  PossibleParameters var) {
        this.function = function;
        this.var = var;
    }

    //for ExpertMode - Professors
    public void decorateProfessors(ProfessorsComputingExpert<PossibleParameters> function, PossibleParameters x) {
        professors.decorate(function, Optional.of(x));
    }

    /**
     * End of turn: function reset method.
     */
    public void reset() {
        professors.reset();
        function = InterfaceAdapter.adaptExpertInfluence(defaultFunction);
    }

    @Override
    public Map<Team, Integer> getInfluenceMap(Island island) {
        return function.computeInfluenceMap(island, getProfessorsMap(), var);
    }
}
