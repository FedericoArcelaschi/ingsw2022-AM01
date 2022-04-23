package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;

import java.util.Map;
import java.util.Objects;

public abstract class Generic {
    protected int cost;
    protected final String explanation;
    protected int idChar;
    protected CharactersList characterName;

    /**
     * requires idChar between 1 and 12.
     *
     * @param idChar id corresponding to the position in the CharacterList
     */
    public Generic(int idChar) {
        this.idChar = idChar;
        this.characterName = CharactersList.getChar(idChar);
        cost = characterName.getCost();
        explanation = characterName.getExplanation();
    }


    public abstract void applyEffect(Map<Parameters, Object> parameterMap) throws NoSuchStudentException, TooManyStudentsException, IllegalArgumentException;

    public abstract Map<Parameters, Object> getEffect();

    public CharactersList getCharacterType() {
        return characterName;
    }

    public String getExplanation() {
        return explanation + "cost - " + this.getCost();
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Generic generic)) return false;
        return getCost() == generic.getCost() && idChar == generic.idChar && Objects.equals(getExplanation(), generic.getExplanation()) && getCharacterType() == generic.getCharacterType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCost(), getExplanation(), idChar, getCharacterType());
    }
}
