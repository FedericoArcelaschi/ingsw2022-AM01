package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.interfaces.Affectable;

/**
 * Class needed to get the right available steps to move mother nature
 * (to keep the reference in the MailMan effect)
 */

public class IntegerBoxing implements Affectable {

    private int anInt;

    public IntegerBoxing(int i) {
        this.anInt = i;
    }

    public int getInt() {
        return anInt;
    }

    public void setInt(int i) {
        this.anInt = i;
    }

    public void zero() {
        anInt = 0;
    }

    public void add(int i) {
        anInt += i;
    }

    /**
     * ExpertMode method for MailMan expertLogic card.
     * Increases motherNature possible moving distance by NumOfSteps
     * @param increase increase in distance
     */
    @Override
    public void affect(int increase) {
        anInt += increase;
    }

    @Override
    public String toString() {
        return Integer.toString(anInt);
    }
}

