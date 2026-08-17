package it.polimi.ingsw.server.model.expertLogic.character.applyEffect;

import it.polimi.ingsw.server.model.exceptions.StudentException;

/**
 * function that is called by each character.
 */
@FunctionalInterface
public interface ApplyEffect {
    void applyEffect(ParametersForCharacter p) throws StudentException, IllegalAccessException;
}
