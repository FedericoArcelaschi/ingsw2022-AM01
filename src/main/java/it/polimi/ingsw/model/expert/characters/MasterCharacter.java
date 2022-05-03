package it.polimi.ingsw.model.expert.characters;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class MasterCharacter {
    protected int cost;
    protected final String explanation;
    protected CharactersInfo characterName;

    /**
     * requires idChar between 1 and 12.
     * @param idChar id corresponding to the position in the CharacterList
     */
    public MasterCharacter(int idChar) {
        characterName = CharactersInfo.getChar(idChar);
        cost = characterName.getCost();
        explanation = characterName.getExplanation();
    }

    public abstract void applyEffect(Map<PossibleParameter, Objects> parameterMap);

    public abstract void getEffect();

    public CharactersInfo getCharacterType() {
        return characterName;
    }

    public String getExplanation() {
        return explanation + "cost - " + this.getCost();
    }

    public int getCost() {
        return cost;
    }

}
