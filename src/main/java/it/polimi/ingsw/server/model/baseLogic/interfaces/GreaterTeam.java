package it.polimi.ingsw.server.model.baseLogic.interfaces;

import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.exceptions.DrawException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Interface that contain this static method that we can reuse to find the
 */
public interface GreaterTeam extends Function<Map<Team, Integer>, Team> {

    static Team findGreaterTeam(Map<Team, Integer> teamIntegerMap) {
        Team max = null;
        int maxScore = 0;
        for (Team t1 : Team.values()) {
            if(teamIntegerMap.get(t1) > maxScore) {
                max = t1;
                maxScore = teamIntegerMap.get(max);
            } else if(teamIntegerMap.get(t1) == maxScore && t1 != max) {
                max = null;
            }
        }
        return max;
    }

}
