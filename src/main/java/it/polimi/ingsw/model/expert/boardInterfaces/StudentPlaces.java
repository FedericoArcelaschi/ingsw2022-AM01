package it.polimi.ingsw.model.expert.boardInterfaces;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;

public interface StudentPlaces {

    /**
     * Function that can be called from an expert character on the island or on the castle.
     * @param student student to be added in the "place"
     * @param place - for castle use only.
     *              0 -> adds the student in the dining room
     *              1 -> adds the student in the waiting room
     */
    void adds(Color student, int place) throws IllegalAccessException, TooManyStudentsException;


    /**
     * Function that can be called from an expert character on the island or on the castle.
     * @param student student to put in the "place"
     * @param place  for castle use only.
     *              0 -> takes the student from the dining room
     *              1 -> takes the student from the waiting room
     */
    void removes(Color student, int place) throws NoSuchStudentException, TooManyStudentsException, IllegalAccessException;

}
