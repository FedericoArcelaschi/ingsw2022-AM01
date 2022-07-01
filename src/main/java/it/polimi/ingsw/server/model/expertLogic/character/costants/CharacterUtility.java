package it.polimi.ingsw.server.model.expertLogic.character.costants;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ApplyEffect;
import org.jetbrains.annotations.Contract;

import java.text.ParseException;

public enum  CharacterUtility {
    MONK(CharacterParametersType.STUDENT),
    FARMER(CharacterParametersType.INFLUENCE),
    GUARD(CharacterParametersType.ISLAND),
    MAILMAN(CharacterParametersType.STANDARD),
    WITCH(CharacterParametersType.ISLAND),
    CENTAUR(CharacterParametersType.INFLUENCE),
    JESTER(CharacterParametersType.STUDENT),
    KNIGHT(CharacterParametersType.INFLUENCE),
    COOK(CharacterParametersType.INFLUENCE),
    STORYTELLER(CharacterParametersType.STUDENT),
    QUEEN(CharacterParametersType.STUDENT),
    TAXMAN(CharacterParametersType.STUDENT);

    private final int id;
    private final CharacterParametersType characterParametersType;

    /**
     * The cost is also dynamically added to the explanation
     */
    CharacterUtility(CharacterParametersType characterParametersType){
        this.characterParametersType = characterParametersType;
        this.id = this.ordinal() + 1;
    }

    @Contract(pure = true)
    public String getExplanation() {
        return CharacterExplanation.getInstance(id).getDescription();
    }

    @Contract(pure = true)
    public CharacterParametersType getCharacterType() {
        return characterParametersType;
    }

    @Contract(pure = true)
    public int getCost() {
        if(id % 3 == 0) return 3;
        return id % 3;
    }

    @Contract
    public int getId(){
        return id;
    }

    @Contract(pure = true)
    public ApplyEffect getFunction() {
        return CharacterFunction.getCharFunction(id);
    }

    /**
     * @param charId >= 0, charId <= 12
     */
    public static CharacterUtility getChar(int charId) {
        if(charId <=  0 || charId > CharacterUtility.values().length)
            throw new IllegalArgumentException("Please insert a valid Character ID");
        return CharacterUtility.values()[charId - 1];
    }

    public static CharacterUtility getChar(String charName) throws  ParseException {
        for (CharacterUtility expertCharacter : CharacterUtility.values()) {
            if (charName.equalsIgnoreCase(expertCharacter.name()))
                return expertCharacter;
        }
        throw new ParseException(charName + " is not a character name", 0);
    }

}

