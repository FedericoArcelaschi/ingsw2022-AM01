package it.polimi.ingsw.model.expert.influence.professor;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;

import java.util.Map;

public interface ProfessorsComputingExpert<T> {
    Map<Color, Team> computeProfessorsMap(Map<String, Castle> castle, Map<Color, Team> professorsMap, T t);
}
