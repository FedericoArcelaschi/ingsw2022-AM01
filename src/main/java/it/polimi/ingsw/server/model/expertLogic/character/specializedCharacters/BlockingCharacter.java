package it.polimi.ingsw.server.model.expertLogic.character.specializedCharacters;

import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.expertLogic.character.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import org.jetbrains.annotations.Range;

/**
 * The Witch is the only character that has this power.
 */
public class BlockingCharacter extends StandardCharacter {

    private int availableBlockTiles; @Range(from = 0, to = 4)

    public BlockingCharacter(int idChar) {
        super(idChar);
        availableBlockTiles = 4;
    }

    /**
     * WITCH character: blocks islands
     * @ param island contains the island to block
     */
    @Override
    public void applyEffect(ParametersForCharacter par) throws StudentException, IllegalAccessException {
        par.setAvailableTiles(availableBlockTiles);
        par.setBlockCharacter(this);
        function.applyEffect(par);
        availableBlockTiles--;
        cost = character.getCost() + 1;
    }
    public void addBlockTile(){
        availableBlockTiles++;
    }
}
