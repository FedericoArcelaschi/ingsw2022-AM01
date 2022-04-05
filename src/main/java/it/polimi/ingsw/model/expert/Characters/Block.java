package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.expert.ExpertIsland;

public class Block extends Generic {
    private int availableBlockTile;
    public Block(int idChar) {
        super(idChar);
        availableBlockTile = 4;
    }

    @Override
    public boolean applyEffect() {
        return false;
    }

    public boolean applyEffect(boolean payedToken, ExpertIsland island) { // for 5th character

        if (availableBlockTile > 0 && payedToken) {
            return island.blockIsland();
        }
        return false;
    }
}
