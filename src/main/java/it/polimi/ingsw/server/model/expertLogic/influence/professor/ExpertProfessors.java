package it.polimi.ingsw.server.model.expertLogic.influence.professor;

import it.polimi.ingsw.server.model.baseLogic.Castle;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;
import it.polimi.ingsw.server.model.baseLogic.interfaces.InterfaceAdapter;
import it.polimi.ingsw.server.model.baseLogic.influence.Professors;

import java.util.Map;
import java.util.Optional;

/**
 * ExpertModeProfessor. Can implement multiple computing functions
 */
public class ExpertProfessors extends Professors {

    private PossibleParameters var;
    private ProfessorsComputingExpert<PossibleParameters> function;

    public ExpertProfessors(Map<String, Castle> castleMap) {
        super(castleMap);
        this.var = null;
        this.function = InterfaceAdapter.adaptProfessorsExpert(defaultFunction);
    }

    @Override
    public void updateProfessorsAssigning() {
       function.computeProfessorsMap(castleMap, professorsMap, var);
    }

    @Override
    public Map<StudentColor, Team> getProfessorsAssigning() {
        updateProfessorsAssigning();
        return professorsMap;
    }


    public void decorate(ProfessorsComputingExpert<PossibleParameters> function, Optional<PossibleParameters> x) {
        this.function = function;
        var = x.orElse(null);
    }


    public void reset(){
        function = InterfaceAdapter.adaptProfessorsExpert(defaultFunction);
    }

}
