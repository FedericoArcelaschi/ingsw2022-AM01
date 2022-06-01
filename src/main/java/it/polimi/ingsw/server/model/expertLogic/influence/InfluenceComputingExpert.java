package it.polimi.ingsw.server.model.expertLogic.influence;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.Team;

import java.util.Map;

@FunctionalInterface
public interface InfluenceComputingExpert<T> {
    Map<Team, Integer> computeInfluenceMap(Island island,
                                           Map<StudentColor, Team> professorsMap,
                                           T t);
}
