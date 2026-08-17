package it.polimi.ingsw.server.model.expertLogic.character.specializedCharacters;

import it.polimi.ingsw.server.model.baseLogic.Bag;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.expertLogic.character.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;

import java.util.*;

public class StudentCharacter extends StandardCharacter {
    private final Bag bag;
    private final List<StudentColor> availableStudents = new ArrayList<>();
    private int numberOfAvailableStudents;

    /**
     * Constructor for characters that have a buffer of available students
     * @param bag to extract the students.
     */
    public StudentCharacter(int idChar, Bag bag) {
        super(idChar);
        this.bag = bag;
        switch (idChar) {
            case 1, 11 ->   numberOfAvailableStudents = 4;
            case 7 ->       numberOfAvailableStudents = 6;
            default -> throw new IllegalArgumentException("Wrong character-id: can only be 1, 7, 11. Actual: " + idChar);
        }
        availableStudents.addAll(bag.multipleExtract(numberOfAvailableStudents));
    }


    /**
     * MONK: Adds one student from the card to the given island
     * JESTER: Switches three or fewer students form the card to the dining room
     * QUEEN: lets you swap a student in the dining room with a student on the card
     *
     * @param par   The student(s) that this character will affect (move)
     *              The index to use in case the student(s) need(s) to be moved to an island
     * @implNote    If the student(s) doesn't need to be moved to an island -> islandIndex = 0,
     *              then this index will contain the number of Castles(useful for the TaxMan effect)
     * @implNote    places contains both  and <code>Castle</code> & <code>Island</code> type objects.
     *              As a convention the current player's castle is the first and the others follow with no particular order.
     *              (There is no need to distinguish the other castles).
     *              Islands are added with their prevoius order and the index is <em>off-setted</em> by the number of players
     */
    @Override
    public void applyEffect(ParametersForCharacter par) throws StudentException, IllegalAccessException {
        par.setAvailableStudentsList(availableStudents);
        par.setBag(bag);
        function.applyEffect(par);
        cost = character.getCost() + 1;
    }

    /**
     * To be used only for tests.
     */
    @Override
    public List<StudentColor> getAvailableStudents() {
        return new ArrayList<>(availableStudents);
    }

    /**
     * @return the explanation, the cost and the available students.
     */
    @Override
    public String getExplanation() {
        return super.getExplanation()
                + "\nAvailable students are: "
                + availableStudents
                    .toString()
                    .toLowerCase()
                    .replace('[', ' ')
                    .replace(']', '.');
    }

}
