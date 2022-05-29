package it.polimi.ingsw.model.expertLogic.influence;

import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.model.baseLogic.Island;
import it.polimi.ingsw.model.baseLogic.Team;

import java.util.Map;

@FunctionalInterface
public interface InfluenceComputingExpert<T> {
    Map<Team, Integer> computeInfluenceMap(Island island,
                                           Map<StudentColor, Team> professorsMap,
                                           T t);
}
