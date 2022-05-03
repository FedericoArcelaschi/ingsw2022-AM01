package it.polimi.ingsw.model.expert.characters;

import it.polimi.ingsw.model.expert.BlockedIsland;
import it.polimi.ingsw.model.expert.ExpertIsland;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Block extends MasterCharacter {
    //must be 0 ≤ x ≤ 4
    private int availableBlockTile;

    public Block(int idChar) {
        super(idChar);
        availableBlockTile = 4;
    }

    /**
     * WITCH character: blocks islands
     * @ param island contains the island to block
     */
    public void applyEffect() {
        if (availableBlockTile == 0)
            throw new IllegalArgumentException("4 islands are already blocked");
        if(island.isBlocked())
            throw new IllegalArgumentException("Island is already blocked");
        island = new BlockedIsland(island, this);
        availableBlockTile--;
        cost = characterName.getCost() + 1;
    }

    @Override
    public void getEffect() {}


    public void addBlockTile(){
        availableBlockTile++;
    }
}
