package it.polimi.ingsw.model.expert.influence;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.influence.functionalInterfaces.InfluenceComputing;

import java.util.Map;


public interface InfluenceComputingExpert<T> {
    Map<Team, Integer> computeInfluenceMap(Island island, Map<Color, Team> professorsMap, T t);
}
