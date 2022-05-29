package it.polimi.ingsw.model.expertLogic.character.charTypes;

import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.expertLogic.character.applyEffect.ParametersForCharacter;
import org.jetbrains.annotations.Range;

public class BlockCharacter extends StandardCharacter {

    private int availableBlockTiles; @Range(from = 0, to = 4)

    public BlockCharacter(int idChar) {
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
        par.setBlockChar(this);
        function.applyEffect(par);
        availableBlockTiles--;
        cost = character.getCost() + 1;
    }
    public void addBlockTile(){
        availableBlockTiles++;
    }
}
