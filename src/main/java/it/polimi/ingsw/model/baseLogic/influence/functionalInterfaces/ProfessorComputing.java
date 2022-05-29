package it.polimi.ingsw.model.baseLogic.influence.functionalInterfaces;

import it.polimi.ingsw.model.baseLogic.Castle;
import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.model.baseLogic.Team;

import java.util.Map;

public interface ProfessorComputing {
     void computeProfessorsMap(Map<String, Castle> castle, Map<StudentColor, Team> professorsMap);
}
