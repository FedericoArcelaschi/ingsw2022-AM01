package it.polimi.ingsw.model.Functionalnterfaces;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;

import java.util.Map;

public enum InfluenceMapCalculationFunctions {

    DEFAULT(
    (Island island, Map<Color, Team> professorsMap, Color student) -> {
        Map<Team, Integer> influenceMap = island.calculateInfluence(professorsMap);
        //counts the students
        for (Team t:Team.values()) {
            int influence = 0;
            for (Color c : Color.values()) {
                if (professorsMap.get(c) == t)
                    influence += island.getStudents().get(c);
            }
            influenceMap.put(t, influence);
        }
        //counts the towers
        Team oldOwner = island.getOwnership();
        if(oldOwner != null)
            influenceMap.put(oldOwner, island.getIslandNumber());
        return influenceMap;
    });


    //GUARD();

    private final InfluenceComputing function;

    InfluenceMapCalculationFunctions(InfluenceComputing function) {
        this.function = function;
    }

    public InfluenceComputing getFunction(){
        return function;
    }
}

