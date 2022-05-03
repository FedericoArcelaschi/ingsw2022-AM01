package it.polimi.ingsw.model.Functionalnterfaces;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;

import java.util.Map;

public enum ProfessorsMapComputingFunction {
    DEFAULT(
        (Map<String, Castle> castle, Map<Color, Team> professorsMap, Team currentTeam) -> {
            for (Color c:Color.values()) {
                Team teamMax = professorsMap.get(c);
                int max = 0;
                for (String player: castle.keySet()) {
                    if (castle.get(player).getTeam() == teamMax && castle.get(player).getDiningRoom().get(c) > max)
                        max = castle.get(player).getDiningRoom().get(c);
                    else  if( castle.get(player).getDiningRoom().get(c) > max) {
                        max = castle.get(player).getDiningRoom().get(c);
                        teamMax = castle.get(player).getTeam();
                    }
                }
                professorsMap.replace(c, teamMax);
            }
            return professorsMap;
        }
    ),
    FARMER(
        (Map<String, Castle> castle, Map<Color, Team> professorsMap, Team currentTeam) -> {
            for (Color  c: Color.values()) {
                Team teamMax = currentTeam;
                int max = 0;
                for (String player: castle.keySet()) {
                    if (castle.get(player).getTeam() == teamMax
                            && castle.get(player).getDiningRoom().get(c) > max)
                        max = castle.get(player).getDiningRoom().get(c);
                    else if(castle.get(player).getDiningRoom().get(c) > max) {
                        max = castle.get(player).getDiningRoom().get(c);
                        teamMax = castle.get(player).getTeam();
                        professorsMap.replace(c, teamMax);
                    }
                }
            }
            return professorsMap;
        }
    );

    private final ProfessorComputing function;

    ProfessorsMapComputingFunction(ProfessorComputing function) {
        this.function = function;
    }

    public ProfessorComputing getFunction() {
        return function;
    }
}
