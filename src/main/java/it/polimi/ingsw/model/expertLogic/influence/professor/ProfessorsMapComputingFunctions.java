package it.polimi.ingsw.model.expertLogic.influence.professor;

import it.polimi.ingsw.model.baseLogic.Castle;
import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.model.baseLogic.Team;
import it.polimi.ingsw.model.baseLogic.interfaces.PossibleParameters;

import java.util.Map;


public enum ProfessorsMapComputingFunctions {
    /**
     * FARMER EFFECT:
     * Prefers current player's team when the score is tie.
     */
    FARMER(
        (Map<String, Castle> castle, Map<StudentColor, Team> professorsMap, Team currentTeam) -> {
            for (StudentColor c : StudentColor.values()) {
                Team teamMax = currentTeam;
                int max = 0;
                for (String player : castle.keySet())
                    if(castle.get(player).getTeam() == currentTeam)
                        if(castle.get(player).getDiningRoom().get(c) >= max) {
                            max = castle.get(player).getDiningRoom().get(c);
                            professorsMap.replace(c, currentTeam);
                        }
                for (String player : castle.keySet())
                    if(castle.get(player).getTeam() != currentTeam)
                        if (castle.get(player).getDiningRoom().get(c) > max) {
                            max = castle.get(player).getDiningRoom().get(c);
                            teamMax = castle.get(player).getTeam();
                            professorsMap.replace(c, teamMax);
                    }
            }
        }
    );

    private final ProfessorsComputingExpert<PossibleParameters> function;

    ProfessorsMapComputingFunctions(ProfessorsComputingExpert<? extends PossibleParameters> function) {
        this.function = (ProfessorsComputingExpert<PossibleParameters>) function;
    }

    public  ProfessorsComputingExpert<PossibleParameters> getFunction() {
            return this.function;
    }


}
