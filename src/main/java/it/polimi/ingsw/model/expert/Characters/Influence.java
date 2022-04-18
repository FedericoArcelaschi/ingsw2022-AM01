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
     * @param parameterMap
     * @return true if the effect was correctly applied (right parameters)
     */
    @Override
    public boolean applyEffect(Map<Parameters, Object> parameterMap) {
        switch (idChar){
            case 2:
                String currPlayer = (String) parameterMap.get(Parameters.PLAYERID);
                Map<String, Castle> castleMap = (HashMap<String, Castle>) parameterMap.get(Parameters.CASTLEMAP);
                Map<Color, Team> newProfessorMap = new HashMap<>();
                int currentPlayerStudentsColor = 0;
                int opponentPlayerStudentsColor = 0;
                for(Color c: Color.values()) {
                    newProfessorMap.put(c, castleMap.get(currPlayer).getTeam());
                }
                for(String player: castleMap.keySet()) {
                    if(!player.equals(currPlayer)){
                        for (Color c : Color.values()) {
                            currentPlayerStudentsColor = castleMap.get(currPlayer).getDiningRoom().get(c);
                            opponentPlayerStudentsColor = castleMap.get(player).getDiningRoom().get(c);
                            if (opponentPlayerStudentsColor > currentPlayerStudentsColor)
                                newProfessorMap.put(c, castleMap.get(player).getTeam());
                        }
                    }
                }
                parameterMap.put(Parameters.PROFESSORSMAP, newProfessorMap);
                cost = characterName.getCost() +1;
                return true;
            case 6:
                cost = characterName.getCost() +1;
                return true;
            case 8:
                cost = characterName.getCost() +1;
                return true;
            case 9:
                cost = characterName.getCost() +1;
                return true;
            case 12:
                cost = characterName.getCost() +1;
                return true;
        }
        return false;
    }

    @Override
    public Map<Parameters, Object> getEffect() {
        return null;
    }
}