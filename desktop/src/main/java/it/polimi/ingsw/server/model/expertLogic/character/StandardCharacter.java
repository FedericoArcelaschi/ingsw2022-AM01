package it.polimi.ingsw.server.model.expertLogic.character;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.CharacterFunction;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ApplyEffect;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterParametersType;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

import java.util.List;
import java.util.Objects;

public class StandardCharacter {
    protected final int idChar;
    protected final String explanation;
    protected final CharacterUtility character;
    protected final ApplyEffect function;
    protected int cost;


    /**
     * requires idChar between 1 and 12.
     * @param idChar id corresponding to the position in the CharacterList
     */
    public StandardCharacter(int idChar) {
        this.idChar = idChar;
        character = CharacterUtility.getChar(idChar);
        cost = character.getCost();
        explanation = character.getExplanation();
        function = CharacterFunction.getCharFunction(idChar);
    }

    /**
     * Standard effect method
     * <p>to see the implementations check out {@link it.polimi.ingsw.server.model.expertLogic.character.applyEffect.CharacterFunction}
     */
    public void applyEffect(ParametersForCharacter par) throws StudentException, IllegalAccessException {
        function.applyEffect(par);
        cost = character.getCost() + 1;
    }

//GETTERS
    public CharacterParametersType getCharacterType() {
        return character.getCharacterType();
    }

    /**
     * @return the explanation and the cost (updated)
     */
    public String getExplanation() {
        return explanation + ", cost: " + this.getCost() + "\n\n";
    }
    public String getName() {
        return character.name();
    }

    public List<StudentColor> getAvailableStudents() {
        return null;
    }

    public int getCost() {
        return cost;
    }

    public CharacterUtility getCharacterUtility() {
        return character;
    }

    @Override
    public String toString() {
        return CharacterUtility.getChar(idChar).toString().toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StandardCharacter that)) return false;
        return getCost() == that.getCost() && Objects.equals(getExplanation(), that.getExplanation()) && character == that.character;
    }

    @Override
    public int hashCode() {
        return Objects.hash(character);
    }

}
