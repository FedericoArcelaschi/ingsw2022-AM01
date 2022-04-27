package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.expert.Characters.Block;

public class BlockedIsland extends ExpertIsland {

    private final ExpertIsland i;
    private final Block Expertcharacter;

    public BlockedIsland(ExpertIsland i, Block Expertcharacter) {
        //TODO: shouldn't block a blocked island
        this.i = i;
        this.Expertcharacter = Expertcharacter;
    }

    @Override
    public ExpertIsland setOwnership(Team ownership) {
        Expertcharacter.addBlockTile();
        return i;
    }

    @Override
    public boolean isBlocked() {
        return true;
    }

}
