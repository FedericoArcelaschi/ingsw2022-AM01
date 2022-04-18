package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;

import java.util.Map;

public abstract class Generic {
    protected int cost;
    protected final String explanation;
    protected int idChar;
    protected CharactersList characterName;

    /**
     * requires idChar between 1 and 12.
     * @param idChar
     */
    public Generic(int idChar){
        this.idChar = idChar;
        this.characterName = CharactersList.values()[idChar-1];
        cost = characterName.getCost();
        explanation = characterName.getExplanation();
    }


    public abstract boolean applyEffect(Map<Parameters, Object> ParameterMap) throws NoSuchStudentException, TooManyStudentsException;
    public abstract Map<Parameters, Object> getEffect();

    public CharactersList getCharacterName() {
        return characterName;
    }
    public String getExplanation(){
        return explanation + "cost - " + String.valueOf(this.getCost());
    }
    public int getCost(){
        return cost;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Generic)) return false;
        Generic generic = (Generic) o;
        return getCharacterName() == generic.getCharacterName();
    }
}
