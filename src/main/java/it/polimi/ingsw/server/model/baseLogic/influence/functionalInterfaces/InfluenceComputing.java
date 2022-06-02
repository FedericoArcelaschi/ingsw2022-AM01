package it.polimi.ingsw.server.model.baseLogic.influence.functionalInterfaces;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.Team;

import java.util.Map;


public interface InfluenceComputing {
    Map<Team, Integer> computeInfluenceMap(Island island, Map<StudentColor, Team> professorsMap);
}
