package it.polimi.ingsw.model.expert.character.functionalInterfaces;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.IntegerBoxing;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.boardInterfaces.StudentPlaces;
import it.polimi.ingsw.model.expert.influence.PossibleParameters;
import it.polimi.ingsw.model.influence.Influence;

import java.util.List;

/**
 * Modifies the Influence or the Professors compute function
 * @param <T> can either be Team or Color
 */
public interface ApplyEffectInfluence<T extends PossibleParameters>{
    void applyEffect(Influence influence, T t)
            throws NoSuchStudentException, TooManyStudentsException, IllegalAccessException;

}
