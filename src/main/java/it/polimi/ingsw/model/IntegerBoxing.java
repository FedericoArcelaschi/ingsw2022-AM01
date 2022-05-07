package it.polimi.ingsw.model;

/**
 * Class needed to get the right available steps to move mother nature
 * (to keep the reference in the MailMan effect)
 */

public class IntegerBoxing {
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

}

