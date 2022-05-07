package it.polimi.ingsw.model.expert.influence.professor;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.influence.functionalInterfaces.ProfessorComputing;

import java.util.Map;


public enum ProfessorsMapComputingFunction {

    /**
     * FARMER EFFECT:
     * Prefers current player's team when the score is tie.
     */
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

    private final ProfessorsComputingExpert<?> function;

    ProfessorsMapComputingFunction(ProfessorsComputingExpert<?> function) {
        this.function = function;
    }

    public ProfessorsComputingExpert<?> getFunction() {
        return this.function;
    }
}
