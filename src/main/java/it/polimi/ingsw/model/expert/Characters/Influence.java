package it.polimi.ingsw.model.expert.Characters;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.expert.ExpertIsland;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Influence extends Generic {

    public Influence(int idChar){
        super(idChar);
    }

    /**
     * FARMER: even in case of tie students in the castle, assignes the relative professors
     * to the player that uses this effect
     * CENTAUR: during the influence calculation doesn't count the towers.
     * KNIGHT: the given PLayer has 2 points of influence more than the opponents
     */
    @Override
    public boolean applyEffect(ExpertIsland island, String player, Castle castle, Map<String, Color> professorMap, boolean payedToken, int move, List<Color> students) {
        Map<Color, Castle> newProfessorMap = new HashMap<>();
        //needs all castles.
        return false;
    }
}