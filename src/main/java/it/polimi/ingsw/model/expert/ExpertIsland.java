package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertIsland extends Island { //TODO: extend with interface

    public ExpertIsland(Color student) {
        super(student);
    }

    public ExpertIsland(){
        super();
    }

    /**
     * Calculates the influence on the island for each team
     * @param professorsMap map that contains the sum of influences per team
     * @return influenceMap map containing the sum of influences per team
     */
    public Map<Team, Integer> calculateInfluenceNoTowers(Map<Color, Team> professorsMap){
        Map<Team, Integer> influenceMap = new HashMap<>();
        for(Team t : Team.values())
            influenceMap.put(t, 0);
        super.studentInfluence(influenceMap, professorsMap);
        return influenceMap;
    }

    public boolean isBlocked() {
        return false;
    }

}
