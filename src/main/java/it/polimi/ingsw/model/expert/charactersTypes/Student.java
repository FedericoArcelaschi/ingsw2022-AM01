package it.polimi.ingsw.model.expert.charactersTypes;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.charactersFunctions.ApplyEffectStudents;
import it.polimi.ingsw.model.expert.influence.ExpertInfluenceMap;
import it.polimi.ingsw.model.expert.interfaces.MovesToDestination;
import it.polimi.ingsw.model.expert.interfaces.StudentPlaces;

import java.util.*;

//todo: think about functional intefaces for ExpertCharacterImplementation.
public class Student extends MasterCharacter implements MovesToDestination {
    private static Bag bag;
    private final List<Color> availableStudents = new ArrayList<>();
    private int numberOfAvailableStudents;
    private ApplyEffectStudents functionS;

    public Student(int idChar) {
        super(idChar);
        functionS = charactersFunction.getFunctionS();
    }
    public Student(int idChar, Bag bag) {
        super(idChar);
        Student.bag = bag;
        functionS = charactersFunction.getFunctionS();

        switch (idChar) {
            case 1, 11 -> numberOfAvailableStudents = 4;
            case 7 -> numberOfAvailableStudents = 6;
            default -> throw new IllegalArgumentException("Wrong character-id: can only be 1, 7, 11. Actual: " + idChar);
        }
        availableStudents.addAll(bag.multipleExtract(numberOfAvailableStudents));
    }
    /**
     * MONK: Adds one student from the card to the given island
     * JESTER: Switches three or fewer students form the card to the dining room
     */
    @Override
    public void applyEffect(List<Color> students, List<StudentPlaces> placesList, ExpertInfluenceMap influenceMap, Integer possibleMovingSteps)
            throws NoSuchStudentException, TooManyStudentsException, IllegalAccessException {
        functionS.applyEffect(students, availableStudents, bag, placesList);
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
