package it.polimi.ingsw.server.model.expertLogic.character.costants;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum CharacterInputs {
    MONK(1, StudentColor.class, Integer.class),
    FARMER(),
    GUARD(Integer.class),
    MAILMAN(),
    WITCH(Integer.class),
    CENTAUR(),
    JESTER(StudentColor.class, 2, 4, 6),
    KNIGHT(),
    COOK(StudentColor.class, 1),
    STORYTELLER(StudentColor.class, 2, 4),
    QUEEN(StudentColor.class, 1),
    TAXMAN(StudentColor.class, 1);

    private final Set<Type> types;
    private final Set<Integer> numberOfStudent;

    CharacterInputs(Type... types) {
        this.types = Arrays.stream(types).collect(Collectors.toSet());
        numberOfStudent = null;
    }

    CharacterInputs(Type type) {
        types = Set.of(type);
        numberOfStudent = null;
    }

    CharacterInputs(Type type, Integer... numberOfStudent) {
        types = Set.of(type);
        this.numberOfStudent = Arrays.stream(numberOfStudent).collect(Collectors.toSet());
    }

    CharacterInputs(Integer numberOfStudent, Type... types) {
        this.types = Arrays.stream(types).collect(Collectors.toSet());
        this.numberOfStudent = Set.of(numberOfStudent);
    }

    public Set<Type> getTypes() {
        return types;
    }

    public @Nullable Set<Integer> getNumberOfStudent() {
        return numberOfStudent;
    }

    /**
     * @param charId >= 0, charId <= 12
     */
    public static CharacterInputs getChar(int charId) {
        if(charId <=  0 || charId > CharacterInputs.values().length)
            throw new IllegalArgumentException("Please insert a valid Character ID");
        return CharacterInputs.values()[charId - 1];
    }

    public static CharacterInputs getChar(String charName) throws ParseException {
        for (CharacterInputs expertCharacter : CharacterInputs.values()) {
            if (charName.equalsIgnoreCase(expertCharacter.name()))
                return expertCharacter;
        }
        throw new ParseException(charName + " is not a character name", 0);
    }
}

