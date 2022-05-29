package it.polimi.ingsw.model.expertLogic.character.costants;
import it.polimi.ingsw.model.expertLogic.character.applyEffect.ApplyEffect;
import org.jetbrains.annotations.Contract;

public enum CharacterUtility { //TODO: complete characters' explaination
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

    public static CharacterUtility getChar(int charId) {
        return CharacterUtility.values()[charId - 1];
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

    @Contract(pure = true)
    public int getId() {
        return id;
    }

    @Contract(pure = true)
    public ApplyEffect getFunction() {
        return CharacterFunction.getCharFunction(id);
    }

}

