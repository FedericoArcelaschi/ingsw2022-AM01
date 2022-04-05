package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.expert.ExpertBoard;
import org.jetbrains.annotations.NotNull;

public class Action extends Generic {

    public Action(int idChar){
        super(idChar);
    }

    @Override
    public boolean applyEffect() {
        return false;
    }

    /**
     * Method for 4th character. increases the MN move range.
     * @param move
     * @return move +2
     */
    public int applyEffect(int move) { // for 4th character
        return move + 2;
    }

    /**
     * effefct for third character
     *
     * @param island
     * @return island
     */
    public Island applyEffect(Island island) {
        return island;
    }


}
