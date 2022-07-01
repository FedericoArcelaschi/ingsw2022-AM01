package it.polimi.ingsw.server.model.baseLogic;

/**
 * Class needed to get the right available steps to move mother nature
 * (to keep the reference in the MailMan effect)
 */

public class PossibleMovingSteps {

    private int steps;
    private boolean updated;

    public int get() {
        return steps;
    }

    public void zero() {
        steps = 0;
        updated = false;
    }

    public void update(int possibleMovingSteps) {
        if(!updated) {
            steps += possibleMovingSteps;
            updated= true;
        }
    }

    public void add(int i) {
        steps += i;
    }

    @Override
    public String toString() {
        return Integer.toString(steps);
    }
}

