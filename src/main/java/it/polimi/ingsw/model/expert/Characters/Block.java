package it.polimi.ingsw.model.expert.Characters;

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
     *
     * @param parameterMap contains the island to block
     */
    @Override
    public void applyEffect(Map<Parameters, Object> parameterMap) {
        boolean result = false;
        if (availableBlockTile > 0) {
            ExpertIsland island = (ExpertIsland) parameterMap.get(Parameters.ISLAND);
            if (island.blockIsland()) {
                availableBlockTile--;
                cost = characterName.getCost() + 1;
            } else
                throw new IllegalArgumentException("Island is already blocked");
        } else
            throw new IllegalArgumentException("4 islands are already blocked");
    }

    @Override
    public Map<Parameters, Object> getEffect() {
        return null;
    }
}
