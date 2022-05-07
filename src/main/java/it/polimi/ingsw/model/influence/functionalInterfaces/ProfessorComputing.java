package it.polimi.ingsw.model.influence.functionalInterfaces;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;

import java.util.Map;

public interface ProfessorComputing {
     default Map<Color, Team> computeProfessorsMap(Map<String, Castle> castle, Map<Color, Team> professorsMap){
        for (Color c : Color.values()) {
            Team teamMax = professorsMap.get(c);
            int max = 0;
            for (String player: castle.keySet()) {
                //the professor's owner changes only if the new player has strictly more students that the old one.
                int numberOfStudentsNew
                        = castle.get(player).getDiningRoom().get(c);
                Team teamNew
                        = castle.get(player).getTeam();
                if(numberOfStudentsNew > max) {
                    max = numberOfStudentsNew;
                    teamMax = teamNew;
                }
            }
            professorsMap.replace(c, teamMax);
        }
        return professorsMap;
    }
}
