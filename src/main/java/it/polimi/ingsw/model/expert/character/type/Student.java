package it.polimi.ingsw.model.expert.character.type;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.IntegerBoxing;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.expert.boardInterfaces.StudentPlaces;
import it.polimi.ingsw.model.influence.Influence;

import java.util.*;

//todo: think about functionalInterfaces intefaces for ExpertCharacterImplementation.
public class Student extends MasterCharacter {
    private static Bag bag;
    private final List<Color> availableStudents = new ArrayList<>();
    private int numberOfAvailableStudents;

    /**
     * constructor for "stateless" characters
     */
    public Student(int idChar) {
        super(idChar);
    }

    /**
     * Constructor for characters that have a buffer of available students
     * @param bag to extract the students.
     */
    public Student(int idChar, Bag bag) {
        super(idChar);
        Student.bag = bag;

        switch (idChar) { //TODO: più funzionale
            case 1, 11 -> numberOfAvailableStudents = 4;
            case 7 -> numberOfAvailableStudents = 6;
            default -> throw new IllegalArgumentException("Wrong character-id: can only be 1, 7, 11. Actual: " + idChar);
        }
        availableStudents.addAll(bag.multipleExtract(numberOfAvailableStudents));
    }


    /**
     * MONK: Adds one student from the card to the given island
     * JESTER: Switches three or fewer students form the card to the dining room
     * ecc ecc
     */
    @Override
    public void applyEffect(List<Color> students, List<StudentPlaces> placesList, Influence influence, IntegerBoxing steps)
            throws StudentException, IllegalAccessException {
        cost = characterName.getCost() + 1;
    }
    public String getEffect(){
        return availableStudents.toString();
    }

    @Override
    public void movesToDestination(List<Color> students, StudentPlaces destination) {
        //TODO: lol mo'?
    }

}
