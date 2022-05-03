package it.polimi.ingsw.model.Functionalnterfaces;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;

import java.util.Map;
import java.util.function.BiFunction;


public interface InfluenceComputing {
    Map<Team, Integer> computeInfluenceMap(Island island, Map<Color, Team> professorsMap, Color studentColor);
}
