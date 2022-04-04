package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.expert.ExpertIsland;

public class BlockCharacter extends ExpertCharacter {
    private int availableBlockTile;
    public BlockCharacter(int idChar) {
        super(idChar);
        availableBlockTile = 4;
    }

    @Override
    public boolean applyEffect() {
        return false;
    }

    public boolean applyEffect(boolean payedToken, ExpertIsland island) { // for 5th character

        if (availableBlockTile > 0 && payedToken) {
            island.block();
        }

        return false;
    }
}
