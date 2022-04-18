package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.expert.ExpertIsland;

import java.util.Map;

public class Action extends Generic {

    public Action(int idChar){
        super(idChar);
    }

    /**
     * Method for GUARD: the given island could be conquered.
     * Method for MAILMAN: increases the MN move range.
     * @return true if move is increased by 2.
     * @return true if the given island was conquered
     */
    @Override
    public boolean applyEffect(Map<Parameters, Object> ParametersMap) throws IllegalArgumentException{
        if(idChar==3) {
            ExpertIsland island;
            Map<Color, Team> professorsMap;
            Map<Team, Integer> influenceMap;
            island = (ExpertIsland) ParametersMap.get(Parameters.ISLAND);
            professorsMap = (Map<Color, Team>) ParametersMap.get(Parameters.PROFESSORSMAP);
            influenceMap = island.calculateInfluence(professorsMap);
            Team teamOwner = null;
            int maxInfluence = 0;
            for (Team t : Team.values()) {
                if (influenceMap.get(t) > maxInfluence) {
                    teamOwner = t;
                    maxInfluence = influenceMap.get(t);
                } else if (influenceMap.get(t) == maxInfluence)
                    teamOwner = island.getOwnership(); //owner doesn't change
            }
            island.setOwnership(teamOwner);
            cost = characterName.getCost() +1;
            return true;
        }
        if (idChar == 4) {
            Integer move;
            move = (Integer) (ParametersMap.get(Parameters.MOVE)) + 2;
            ParametersMap.replace(Parameters.MOVE, move);
            cost = characterName.getCost() +1;
            return true;
        }
        throw new IllegalArgumentException("Wrong character summoned: should be either 3 or 4");
    }

    @Override
    public Map<Parameters, Object> getEffect() {
        return null;
    }

}
