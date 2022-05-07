package it.polimi.ingsw.model.expert.character.functionalInterfaces;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.boardInterfaces.StudentPlaces;

import java.util.List;

/**
 * Functional interface for Student-Type ExpertCharacter
 */
public interface ApplyEffectStudents extends ApplyEffect<List<Color>, List<Color>, Bag, List<StudentPlaces>> {
    @Override
    /**
     * method for cards that can move students around
     * @param requestedStudents students that the player wants to move
     * @param availableStudents students "on" the expert character
     * @param bag only for characters that extract new students while applying the effect
     * @param placesList where to put or get students. this List coould contain either castles of islands.
     */
    void applyEffect(List<Color> requestedStudents, List<Color> availableStudents, Bag bag, List<StudentPlaces> placesList)
            throws NoSuchStudentException, TooManyStudentsException, IllegalAccessException;
}
