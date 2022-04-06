package it.polimi.ingsw.model.expert.Characters;
import it.polimi.ingsw.model.*;

import java.util.HashMap;
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
     * @param ParameterMap
     */
    @Override
    public boolean applyEffect(Map<Parameters, Object> ParameterMap) {

        Map<Color, Castle> newProfessorMap = new HashMap<>();
        //needs all castles.
        return false;
    }
}