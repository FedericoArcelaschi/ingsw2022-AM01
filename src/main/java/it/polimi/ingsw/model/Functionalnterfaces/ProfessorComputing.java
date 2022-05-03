package it.polimi.ingsw.model.Functionalnterfaces;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;

import java.util.Map;

public interface ProfessorComputing {
    public Map<Color, Team> computeProfessorsMap(Map<String, Castle> castle, Map<Color, Team> professorsMap, Team currentTeam);
}
