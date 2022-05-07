package it.polimi.ingsw.model.expert.character.type;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.IntegerBoxing;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.expert.character.functionalInterfaces.ApplyEffect;
import it.polimi.ingsw.model.expert.character.costants.CharacterUtility;
import it.polimi.ingsw.model.expert.boardInterfaces.StudentPlaces;
import it.polimi.ingsw.model.influence.Influence;

import java.util.List;
import java.util.Objects;

public abstract class MasterCharacter {
    protected final int idChar;
    protected int cost;
    protected final String explanation;
    protected CharacterUtility characterName;
    protected ApplyEffect charactersFunction;

    /**
     * requires idChar between 1 and 12.
     * @param idChar id corresponding to the position in the CharacterList
     */
    protected MasterCharacter(int idChar) {
        this.idChar = idChar;
        characterName = CharacterUtility.getInstance(idChar);
        cost = characterName.getCost();
        explanation = characterName.getExplanation();
        charactersFunction = characterName.getFunction();
    }

    public abstract void applyEffect(List<Color> students, List<StudentPlaces> placesList, Influence influence, IntegerBoxing steps)
            throws StudentException, IllegalAccessException;

    public abstract String getEffect();

    public CharacterUtility getCharacterType() {
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
        if (!(o instanceof MasterCharacter)) return false;
        MasterCharacter that = (MasterCharacter) o;
        return getCost() == that.getCost() && Objects.equals(getExplanation(), that.getExplanation()) && characterName == that.characterName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCost(), getExplanation(), characterName);
    }
}
