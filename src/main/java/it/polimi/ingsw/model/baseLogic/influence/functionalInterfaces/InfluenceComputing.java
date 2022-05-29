package it.polimi.ingsw.model.baseLogic.influence.functionalInterfaces;

import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.model.baseLogic.Island;
import it.polimi.ingsw.model.baseLogic.Team;

import java.util.Map;


public interface InfluenceComputing {
    Map<Team, Integer> computeInfluenceMap(Island island, Map<StudentColor, Team> professorsMap);
}
