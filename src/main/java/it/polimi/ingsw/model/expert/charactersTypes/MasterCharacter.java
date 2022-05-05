package it.polimi.ingsw.model.expert.charactersTypes;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.charactersFunctions.ApplyEffect;
import it.polimi.ingsw.model.expert.charactersFunctions.CharactersFunction;
import it.polimi.ingsw.model.expert.influence.ExpertInfluenceMap;
import it.polimi.ingsw.model.expert.interfaces.StudentPlaces;

import java.util.List;
import java.util.Objects;

public abstract class MasterCharacter {
    protected final int idChar;
    protected int cost;
    protected final String explanation;
    protected CharactersInfo characterName;

    protected CharactersFunction charactersFunction;

    /**
     * requires idChar between 1 and 12.
     * @param idChar id corresponding to the position in the CharacterList
     */
    protected MasterCharacter(int idChar) {
        this.idChar = idChar;
        characterName = CharactersInfo.getChar(idChar);
        cost = characterName.getCost();
        explanation = characterName.getExplanation();
        charactersFunction = CharactersFunction.getCharacterFunction(idChar);
    }

    public abstract void applyEffect(List<Color> students, List<StudentPlaces> placesList, ExpertInfluenceMap influence, Integer steps)
            throws NoSuchStudentException, TooManyStudentsException, IllegalAccessException;

    public abstract String getEffect();

    public CharactersInfo getCharacterType() {
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
