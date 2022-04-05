package it.polimi.ingsw.model.expert.Characters;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;

import java.util.HashMap;
import java.util.Map;

public class Influence extends Generic {

    public Influence(int idChar){
        super(idChar);
    }



    /**
     * FARMER: even in case of tie students in the castle, assignes the relative professors
     * to the player that uses this effect
     * @param payedToken
     * @param PlayerID
     * @return characterToken
     */
    public boolean applyEffect(boolean payedToken, String PlayerID) {
        if(payedToken){

        }
        return false;
    }
    private Map<Color, Castle> modify(String PlayerId, Map<Color, Castle> professorMap){//TODO
        Map<Color, Castle> newProfessorMap = new HashMap<Color, Castle>();
        return newProfessorMap;
    }

    @Override
    public boolean applyEffect() {
        return false;
    }
}