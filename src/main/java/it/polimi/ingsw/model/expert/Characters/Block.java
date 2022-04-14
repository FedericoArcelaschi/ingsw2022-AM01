package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.expert.ExpertBoard;
import it.polimi.ingsw.model.expert.ExpertIsland;

import java.util.Map;

public class Block extends Generic {
    private int availableBlockTile;

    public Block(int idChar) {
        super(idChar);
        availableBlockTile = 4;
    }

    @Override
    public boolean applyEffect(Map<Parameters, Object> parameterMap) {
        if (availableBlockTile > 0) {
            ExpertIsland island = (ExpertIsland) parameterMap.get(Parameters.ISLAND);
            return island.blockIsland();
        }
        return false;
    }

    @Override
    public Map<Parameters, Object> getEffect() {
        return null;
    }
}
