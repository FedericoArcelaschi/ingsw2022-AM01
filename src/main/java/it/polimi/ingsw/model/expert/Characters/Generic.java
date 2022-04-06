package it.polimi.ingsw.model.expert.Characters;

import java.util.Map;
import java.util.Objects;

public abstract class Generic {
    protected final int cost;
    protected final String explaination;
    protected int idChar;
    protected CharactersList lc = null;

    /**
     * requires idChar between 1 and 12.
     * @param idChar
     */
    public Generic(int idChar){
        this.idChar = idChar;
        CharactersList lc = CharactersList.values()[idChar--];
        cost = lc.getCost();
        explaination = lc.getExplaination();
    }

    public String getExplanation(){
        return explaination;
    }

    public int getCost(){
        return cost;
    }

    public abstract boolean applyEffect(Map<Parameters, Object> ParameterMap);

    public CharactersList getLc() {
        return lc;
    }

    public boolean equals(CharactersList charlist) {
        return charlist == this.lc;
    }
}
