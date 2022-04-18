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

    /**
     * WITCH character: blocks islands
     * @param parameterMap contains the island to block
     * @return
     */
    @Override
    public boolean applyEffect(Map<Parameters, Object> parameterMap) {
        boolean result = false;
        if (availableBlockTile > 0) {
            ExpertIsland island = (ExpertIsland) parameterMap.get(Parameters.ISLAND);
            result = island.blockIsland();
            if(result)
                cost = characterName.getCost() + 1;
        }
        return result;
    }

    @Override
    public Map<Parameters, Object> getEffect() {
        return null;
    }
}
