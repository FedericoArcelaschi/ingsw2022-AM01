package it.polimi.ingsw.server.model.baseLogic.interfaces;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;

public interface StudentPlaces {

    /**
     * Function that can be called from an expertLogic character on the island or on the castle.
     * @param student student to be added in the "place"
     * @param place - for castle use only.
     *              0 -> adds the student in the dining room
     *              1 -> adds the student in the waiting room
     */
    default void adds(StudentColor student, int place) throws IllegalAccessException, TooManyStudentsException {
        throw new IllegalAccessException("method valid only for Expert-Mode");
    }

    /**
     * Function that can be called from an expertLogic character on the island or on the castle.
     * @param student student to put in the "place"
     * @param place  for castle use only.
     *              0 -> takes the student from the dining room
     *              1 -> takes the student from the waiting room
     */
    default void removes(StudentColor student, int place) throws NoSuchStudentException, TooManyStudentsException, IllegalAccessException {
        throw new IllegalAccessException("method valid only for Expert-Mode");
    }
}
