package it.polimi.ingsw.model.expert.Characters;

import java.util.Map;
import java.util.Objects;

public abstract class Generic {
    protected int cost;
    protected final String explaination;
    protected int idChar;
    protected CharactersList lc = null;

    /**
     * requires idChar between 1 and 12.
     * @param idChar
     */
    public Generic(int idChar){
        this.idChar = idChar;
        CharactersList lc = CharactersList.values()[idChar-1];
        cost = lc.getCost();
        explaination = lc.getExplaination();
    }


    public abstract boolean applyEffect(Map<Parameters, Object> ParameterMap);
    public abstract Map<Parameters, Object> getEffect();

    public CharactersList getLc() {
        return lc;
    }
    public String getExplanation(){
        return explaination;
    }
    public int getCost(){
        return cost;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Generic)) return false;
        Generic generic = (Generic) o;
        return getLc() == generic.getLc();
    }
}
