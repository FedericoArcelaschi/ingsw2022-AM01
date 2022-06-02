package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.interfaces.Affectable;

/**
 * Class needed to get the right available steps to move mother nature
 * (to keep the reference in the MailMan effect)
 */

public class IntegerBoxing implements Affectable {
    private int i;

    public IntegerBoxing(int i) {
        this.i = i;
    }

    public int getInt() {
        return i;
    }

    public void setInt(int i) {
        this.i = i;
    }

    /**
     * ExpertMode method for MailMan expertLogic card.
     * Increases motherNature possible moving distance by NumOfSteps
     * @param increase increase in distance
     */
    @Override
    public void affect(int increase) {
        i += increase;
    }
}

