package it.polimi.ingsw.model.expert.influence;

import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.expert.influence.professor.ProfessorsComputingExpert;
import it.polimi.ingsw.model.expert.influence.professor.ProfessorsExpert;
import it.polimi.ingsw.model.influence.Influence;
import it.polimi.ingsw.model.influence.Professors;

import java.util.Optional;

/**
 * This class is needed to handle the Professor in ExpertMode
 * Not a decorator like ProfessorsExpert
 */
public class InfluenceExpert extends Influence {

    public InfluenceExpert(Professors professorsMap) {
        super(professorsMap);
    }


    //for ExpertMode - Influence
    public InfluenceExpert() {
        super();
    }
    public Influence undecorate() {//FIXME
        return this;
    }


    //for ExpertMode - Professors
    public void decorateProfessors(ProfessorsComputingExpert<Team> function, Team var) {
        professorsMap = new ProfessorsExpert<Team>(professorsMap, function, Optional.of(var));
    }

    public void unDecorateProfessors() {
        professorsMap = professorsMap.undecorate();
    }
}
