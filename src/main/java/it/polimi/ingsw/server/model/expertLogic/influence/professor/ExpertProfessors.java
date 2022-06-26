package it.polimi.ingsw.server.model.expertLogic.influence.professor;

import it.polimi.ingsw.server.model.baseLogic.Castle;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;
import it.polimi.ingsw.server.model.baseLogic.interfaces.InterfaceAdapter;
import it.polimi.ingsw.server.model.baseLogic.influence.Professors;
import org.sonatype.inject.Nullable;

import java.util.Map;
import java.util.Optional;

/**
 * ExpertModeProfessor. Can implement custom computing functions
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

    /**for ExpertMode - Professors
     * @param function custom function that will be used in the next turn
     * @param var a parameter that may be used to modify the professor location.
     */
    public void decorate(ProfessorsComputingExpert<PossibleParameters> function, @Nullable PossibleParameters var) {
        this.function = function;
        this.var = var;
    }


    public void reset(){
        function = InterfaceAdapter.adaptProfessorsExpert(defaultFunction);
        var = null;
    }

}
