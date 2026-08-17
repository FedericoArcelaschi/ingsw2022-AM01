package it.polimi.ingsw.server.model.baseLogic.influence.functionalInterfaces;

import it.polimi.ingsw.server.model.baseLogic.Castle;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;

import java.util.Map;

public interface ProfessorComputing {
     void computeProfessorsMap(Map<String, Castle> castle, Map<StudentColor, Team> professorsMap);
}
