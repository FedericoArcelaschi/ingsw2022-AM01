package it.polimi.ingsw.server.model.baseLogic.influence;

import it.polimi.ingsw.server.model.baseLogic.Castle;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.baseLogic.influence.functionalInterfaces.ProfessorComputing;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is useful to encapsulate the Professors implementation and logic.
 * Is also useful to implement new features.
 */
public class Professors {

    protected final Map<StudentColor, Team> professorsMap;
    protected final Map<String, Castle> castleMap;

    public Professors(Map<String, Castle> castleMap) {
        this.castleMap = castleMap;
        this.professorsMap = new HashMap<>();
        for (StudentColor c: StudentColor.values()) {
            professorsMap.put(c, null);
        }
    }

    /**
     * Method needed to update the professorsMap when a student is added to the Dining Room
     */
    public void updateProfessorsAssigning() {
        defaultFunction.computeProfessorsMap(castleMap, professorsMap);
    }

    @Contract(pure = true)
    public Map<StudentColor, Team> getProfessorsAssigning() {
        this.updateProfessorsAssigning();
        return professorsMap;
    }

    protected final ProfessorComputing defaultFunction =
            (Map<String, Castle> castle, Map<StudentColor, Team> professorsMap) -> {
                for (StudentColor c : StudentColor.values()) {
                    Team teamMax = professorsMap.get(c);
                    int max = 0;
                    for (String player: castle.keySet()) {
                        if(castle.get(player).getTeam() == teamMax)
                            if(castle.get(player).getDiningRoom().get(c) > max)
                                max = castle.get(player).getDiningRoom().get(c);
                    } //actual max.
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
            };
}
