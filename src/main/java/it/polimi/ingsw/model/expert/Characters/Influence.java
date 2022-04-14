package it.polimi.ingsw.model.expert.Characters;
import it.polimi.ingsw.model.*;

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
     * @param parameterMap
     * @return true if the effect was correctly applied (right parameters)
     */
    @Override
    public boolean applyEffect(Map<Parameters, Object> parameterMap) {
        switch (idChar){
            case 2:
                String currPlayer = (String) parameterMap.get(Parameters.PLAYERID);
                Map<String, Castle> castleMap = (HashMap<String, Castle>) parameterMap.get(Parameters.CASTLEMAP);
                Map<Color, Castle> newProfessorMap = new HashMap<>();

                for (Color c: newProfessorMap.keySet())
                        newProfessorMap.put(c, castleMap.get(currPlayer));

                for (String player: castleMap.keySet())
                    if(player != currPlayer)
                        for (Color c: newProfessorMap.keySet()) {
                            if(castleMap.get(player).getDiningRoom().get(c) > castleMap.get(currPlayer).getDiningRoom().get(c))
                                newProfessorMap.put(c, castleMap.get(player));
                        }
                parameterMap.put(Parameters.PROFESSORMAP, newProfessorMap);
                cost = CharactersList.FARMER.getCost()+1;
                return true;
            case 6:
                return true;
            case 8:
            case 9:
            case 12:
        }
        return false;
    }

    @Override
    public Map<Parameters, Object> getEffect() {
        return null;
    }
}