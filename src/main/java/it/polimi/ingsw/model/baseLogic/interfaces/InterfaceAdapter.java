package it.polimi.ingsw.model.baseLogic.interfaces;

import it.polimi.ingsw.model.baseLogic.Castle;
import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.model.baseLogic.Island;
import it.polimi.ingsw.model.baseLogic.Team;
import it.polimi.ingsw.model.baseLogic.influence.functionalInterfaces.InfluenceComputing;
import it.polimi.ingsw.model.baseLogic.influence.functionalInterfaces.ProfessorComputing;
import it.polimi.ingsw.model.expertLogic.influence.InfluenceComputingExpert;
import it.polimi.ingsw.model.expertLogic.influence.professor.ProfessorsComputingExpert;

import java.util.Map;

public abstract class InterfaceAdapter {

//For Influence:
    public static <T> InfluenceComputingExpert<T> adaptExpertInfluence(InfluenceComputing function){
        InfluenceComputingExpert<T> functionTri
                = (Island island, Map<StudentColor, Team> influenceMap, T c) ->
                function.computeInfluenceMap(island, influenceMap);
        return functionTri;
    }

//For Professor:
    public static ProfessorsComputingExpert<PossibleParameters> adaptProfessorsExpert(ProfessorComputing biFunction){
        ProfessorsComputingExpert<PossibleParameters> functionTri
                = (Map<String, Castle > castle, Map<StudentColor, Team> professorsMap, PossibleParameters var) ->
                biFunction.computeProfessorsMap(castle, professorsMap);
        return functionTri;
    }
}
