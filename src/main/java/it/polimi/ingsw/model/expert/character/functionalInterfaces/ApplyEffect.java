package it.polimi.ingsw.model.expert.character.functionalInterfaces;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.StudentException;

/**
 * abstract effect for MailMan or Centaur;
 * @param <A> can either be BoxingInteger or Influence
 */
public interface ApplyEffect<A> {
    void applyEffect(A a);
}
