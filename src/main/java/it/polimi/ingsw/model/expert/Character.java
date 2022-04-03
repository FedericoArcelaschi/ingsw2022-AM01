package it.polimi.ingsw.model.expert;

import java.util.HashMap;
import java.util.Map;

public abstract class Character {
    private int cost;
    private String explanation;

    public abstract String getExplanation();

    public abstract int getCost();

    public abstract boolean applyEffect();

}
