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
     * @param move
     * @return move +2
     */
    @Override
    public boolean applyEffect(ExpertIsland island, String player, Castle castle, Map<String, Color> professorMap, boolean payedToken, int move, List<Color> students) {
        if(idChar==4)
            return true;
        /*if(idChar==3) {
            Map<Team, Integer> influenceMap = island.calculateInfluence(professorMap)
            Team team = null;
            int i=0;
            for (Team t : influenceMap) {
                if(t.getInfluence()>i)
                    team = t;
                    i=t.getInfluence();
                else if(t.getInfluence()==i)
                    team= null;
            }
            island.setOwnership(team);
        */
        return true;
    }
}
