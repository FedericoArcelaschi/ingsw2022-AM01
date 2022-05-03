package it.polimi.ingsw.model.Functionalnterfaces;

import it.polimi.ingsw.model.Team;

import javax.management.ObjectInstance;
import java.util.Map;
import java.util.function.Function;

public interface GreaterTeam extends Function<Map, Team> {
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
