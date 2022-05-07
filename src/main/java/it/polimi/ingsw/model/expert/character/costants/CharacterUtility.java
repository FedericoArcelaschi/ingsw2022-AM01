package it.polimi.ingsw.model.expert.character.costants;
import it.polimi.ingsw.model.expert.character.functionalInterfaces.ApplyEffect;
import org.jetbrains.annotations.Contract;

import java.util.Set;

public enum CharacterUtility { //TODO: complete characters' explaination
    MONK,
    FARMER,
    GUARD,
    MAILMAN,
    WITCH,
    CENTAUR,
    JESTER,
    KNIGHT,
    COOK,
    STORYTELLER,
    QUEEN,
    TAXMAN;

    private final int id;
    private final ApplyEffect function;
    private String explanation;

    /**
     * The cost is also dynamically added to the explanation
     */
    CharacterUtility(){
        this.id = this.ordinal() + 1;
        this.explanation = CharacterExplanation.getInstance(id).getDescription();
        this.function = CharacterFunction.getInstance(id).getFunction();
    }

    @Contract(pure = true)
    public static CharacterUtility getInstance(int charId) {
        return CharacterUtility.values()[charId - 1];
    }

    @Contract(pure = true)
    public String getExplanation() {
        return this.explanation;
    }

    @Contract(pure = true)
    public int getCost() {
        if(id == 3) return 3;
        return id % 3;
    }

    @Contract(pure = true)
    public int getId() {
        return id;
    }
    @Contract(pure = true)
    public ApplyEffect getFunction() {
        return function;
    }

    //TODO: make more functional
    public static final Set<Integer> getCharacterThatMoveStudents = Set.of( MONK.id, GUARD.id, WITCH.id, JESTER.id, STORYTELLER.id, QUEEN.id );
    public static final Set<Integer> getCharactersThatNeedAllCastles = Set.of( TAXMAN.id );
}
