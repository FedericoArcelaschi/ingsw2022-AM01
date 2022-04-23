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
     */
    @Override
    @SuppressWarnings("unchecked")
    public void applyEffect(Map<Parameters, Object> parametersMap) {
        switch (idChar) {
            case 3 -> {
                ExpertIsland island;
                Map<Color, Team> professorsMap;
                Map<Team, Integer> influenceMap;
                island = (ExpertIsland) parametersMap.get(Parameters.ISLAND);
                professorsMap = (Map<Color, Team>) parametersMap.get(Parameters.PROFESSORSMAP);
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
                cost = characterName.getCost() + 1;
            }
            case 4 -> {
                Integer possibleMovingSteps = (Integer) parametersMap.get(Parameters.STEPS);
                if (possibleMovingSteps == null)
                    throw new IllegalArgumentException("steps is somehow null");
                possibleMovingSteps += 2;
                parametersMap.replace(Parameters.STEPS, possibleMovingSteps);
                System.out.println("new possible moving steps: " + parametersMap.get(Parameters.STEPS));
                cost = characterName.getCost() + 1;
            }
            default -> throw new IllegalArgumentException("Wrong character summoned: should either be 3 or 4");
        }
    }

    @Override
    public Map<Parameters, Object> getEffect() {
        return null;
    }

}
