package it.polimi.ingsw.model.influence.functionalInterfaces;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;

import java.util.HashMap;
import java.util.Map;


public interface InfluenceComputing {
    default Map<Team, Integer> computeInfluenceMap(Island island, Map<Color, Team> professorsMap){
        Map<Team, Integer> influenceMap = new HashMap<>();
        //counts the students
        for (Team t : Team.values()) {
            int influence = 0;
            for (Color c : Color.values()) {
                if (professorsMap.get(c) == t)
                    influence += island.getStudents().get(c);
            }
            influenceMap.put(t, influence);
        }
        //counts the towers
        Team oldOwner = island.getOwnership();
        if(oldOwner != null) {
            int influence = influenceMap.get(oldOwner) + island.getIslandNumber();
            influenceMap.put(oldOwner, influence);
        }
        return influenceMap;
    }
}
