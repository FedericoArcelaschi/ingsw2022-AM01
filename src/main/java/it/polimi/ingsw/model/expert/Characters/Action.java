package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.expert.ExpertIsland;

import java.util.List;
import java.util.Map;

public class Action extends Generic {

    public Action(int idChar){
        super(idChar);
    }

    /**
     * Method for MAILMAN. increases the MN move range.
     * Method for GUARD. the given island could conquered.
     * @return true if move is increased by 2.
     * @return true if the given island was conquered
     */
    @Override
    public boolean applyEffect(Map<Parameters, Object> ParameterMap) {
        Integer move;
        if (idChar == 4){
            move = (Integer) (ParameterMap.get(Parameters.MOVE));
        return true;
        }
        ExpertIsland island;
        Map<Color, Castle> professorMap;
        if(idChar==3) {
            island = (ExpertIsland) ParameterMap.get(Parameters.ISLAND);
            professorMap = (Map<Color, Castle>) ParameterMap.get(Parameters.PROFESSORMAP);
            Map<Team, Integer> influenceMap = island.calculateInfluence(professorMap);
            Team team = null;
            int maxInfluence = 0;
            for (Team t : Team.values()) {
                if (influenceMap.get(t) > maxInfluence) {
                    team = t;
                    maxInfluence = influenceMap.get(t);
                } else if (influenceMap.get(t) == maxInfluence)
                    team = island.getOwnership(); //owner stays the same
            }
            island.setOwnership(team);
            return true;
        }
        return false;
    }
}
