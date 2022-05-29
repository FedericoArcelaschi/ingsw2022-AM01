package it.polimi.ingsw.model.baseLogic.interfaces;

import it.polimi.ingsw.model.baseLogic.Team;
import java.util.Map;
import java.util.function.Function;

/**
 * Interface that contain this static method that we can reuse to find the
 */
public interface GreaterTeam extends Function<Map<Team, Integer>, Team> {

    static Team findGreaterTeam(Map<Team, Integer> teamIntegerMap){
        int max
                = teamIntegerMap.get(Team.WHITE);
        Team winner
                = Team.WHITE;
        if(teamIntegerMap.get(Team.BLACK) > max) {
            max = teamIntegerMap.get(Team.BLACK);
            winner = Team.BLACK;
        } else if (teamIntegerMap.get(Team.BLACK) == max)
            winner = null;

        if(teamIntegerMap.get(Team.GREY) > max) {
            winner = Team.GREY;
        }else if(teamIntegerMap.get(Team.GREY) == max)
            winner = null;
        return winner;
    }

}
