package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.expert.ExpertIsland;

import java.util.List;
import java.util.Map;

public class Block extends Generic {
    private int availableBlockTile;

    public Block(int idChar) {
        super(idChar);
        availableBlockTile = 4;
    }

    @Override
    public boolean applyEffect(ExpertIsland island, String player, Castle castle, Map<String, Color> professorMap, boolean payedToken, int move, List<Color> students) {
        if (availableBlockTile > 0 && payedToken) {
            return island.blockIsland();
        }
        return false;
    }
}
