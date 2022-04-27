package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.expert.BlockedIsland;
import it.polimi.ingsw.model.expert.ExpertIsland;

import javax.management.ObjectInstance;
import java.util.Map;

public class Block extends Generic {

    /**
     * must be 0 ≤ x ≤ 4
     */
    private int availableBlockTile;

    public Block(int idChar) {
        super(idChar);
        availableBlockTile = 4;
    }

    /**
     * WITCH character: blocks islands
     * @param parameterMap contains the island to block
     */
    @Override
    public void applyEffect(Map<Parameters, Object> parameterMap) {
        if (availableBlockTile == 0)
            throw new IllegalArgumentException("4 islands are already blocked");
        ExpertIsland island = (ExpertIsland) parameterMap.get(Parameters.ISLAND);
        if(island.isBlocked())
            throw new IllegalArgumentException("Island is already blocked");
        island = new BlockedIsland(island, this);
        parameterMap.replace(Parameters.ISLAND, island);
        availableBlockTile--;
        cost = characterName.getCost() + 1;
    }

    @Override
    public Map<Parameters, Object> getEffect() {
        return Map.of(Parameters.AVAILABLEBLOCKTILES, availableBlockTile);
    }

    public void addBlockTile(){
        availableBlockTile++;
    }
}
