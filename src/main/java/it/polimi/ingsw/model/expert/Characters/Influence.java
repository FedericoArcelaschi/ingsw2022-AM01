package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.expert.ExpertIsland;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Influence extends Generic {

    public Influence(int idChar) {
        super(idChar);
    }

    /**
     * FARMER: even in case of tie students in the castle, assignes the relative professors
     * to the player that uses this effect
     * CENTAUR: during the influence calculation doesn't count the towers.
     * KNIGHT: the given PLayer has 2 points of influence more than the opponents
     */
    @Override
    @SuppressWarnings("unchecked")
    public void applyEffect(@NotNull Map<Parameters, Object> parameterMap) {
        String currPlayer = (String) parameterMap.get(Parameters.PLAYERID);
        Map<String, Castle> castleMap = (HashMap<String, Castle>) parameterMap.get(Parameters.CASTLEMAP);
        Map<Color, Team> newProfessorMap = (HashMap<Color, Team>) parameterMap.get(Parameters.PROFESSORSMAP);
        ExpertIsland island = (ExpertIsland) parameterMap.get(Parameters.ISLAND);
        Map<Color, Integer> students = new HashMap<>();
        switch (idChar) {
            case 2 -> {
                if (castleMap == null || newProfessorMap == null)
                    throw new IllegalArgumentException("Wrong parameters for FARMER: expected a Castle, ProfessorMap");
                int currentPlayerStudentsColor;
                int opponentPlayerStudentsColor;
                for (Color c : Color.values()) {//initializes all professors map to current player's team
                    newProfessorMap.put(c, castleMap.get(currPlayer).getTeam());
                }
                for (String player : castleMap.keySet()) {
                    if (!player.equals(currPlayer)) {
                        for (Color c : Color.values()) {
                            currentPlayerStudentsColor = castleMap.get(currPlayer).getDiningRoom().get(c);
                            opponentPlayerStudentsColor = castleMap.get(player).getDiningRoom().get(c);
                            if (opponentPlayerStudentsColor > currentPlayerStudentsColor) //strictly greater
                                newProfessorMap.put(c, castleMap.get(player).getTeam());
                        }
                    }
                }
                cost = characterName.getCost() + 1;
            }
            case 6 -> {
                cost = characterName.getCost() + 1;
            }
            case 8 -> {
                cost = characterName.getCost() + 1;
            }
            case 9 -> {
                cost = characterName.getCost() + 1;
            }
            case 12 -> {
                cost = characterName.getCost() + 1;
            }
        }
    }

    @Override
    public Map<Parameters, Object> getEffect() {
        return null;
    }
}