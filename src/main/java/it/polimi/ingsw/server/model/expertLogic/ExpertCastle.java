package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.Castle;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.exceptions.CoinException;
import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.server.model.baseLogic.interfaces.StudentPlaces;

import java.util.Arrays;
import java.util.List;

public class ExpertCastle extends Castle implements StudentPlaces {

    private int coins;

    /**
     * Castle default constructor
     * @param studentList student list to initialize the waiting room
     */
    public ExpertCastle(Team team, int nPlayer, List<StudentColor> studentList) {
        super(team, nPlayer, studentList);
        coins = 1;
    }

    /**
     * Wheen needed adds a coin to the player
     * @param studentColor color of the student to add
     * @throws TooManyStudentsException if the dining room already contains 10 students of the color <code>color</code>
     */
    @Override
    public void addStudentInDiningRoom(StudentColor studentColor) throws TooManyStudentsException {
        super.addStudentInDiningRoom(studentColor);
        if (diningRoom.get(studentColor) % 3 == 0)
            coins++;
    }

    /**
     * Adds a coin to this player
     * @param students The list of students to add to the dining room.
     * @throws TooManyStudentsException if the dining room already contains 10 students of one of the colors
     */
    @Override
    public void addStudentsInDiningRoom(List<StudentColor> students) throws TooManyStudentsException {
        for (StudentColor c: students) {
            addStudentInDiningRoom(c);
        }
    }

    /**
     * Removes a student from the Dining room.
     * Only available in Expert mode.
     * @param studentColor color of the student to remove
     */
    private void removeStudentFromDiningRoom(StudentColor studentColor) throws NoSuchStudentException {
        if(diningRoom.get(studentColor) > 0) {
            diningRoom.replace(studentColor, diningRoom.get(studentColor) - 1);
        }
        else
            throw new NoSuchStudentException("StudentCharacter " + studentColor + " not in dining room");
    }

    /**
     * Removes the cost of coins from the castle
     */
    protected void payCharacter(int price) throws CoinException {
        if(this.coins >= price)
            this.coins -= price;
        else
            throw new CoinException(price, coins);
    }

    @Override
    public int getCoins() {
        return coins;
    }

//EXPERT-CHARACTERS IMPLEMENTATION:
    @Override
    public void adds(StudentColor student, int place) throws TooManyStudentsException {
        if(place == 0) {
            addStudentInDiningRoom(student);
            return;
        }
        if(place == 1) {
            addStudentsInWaitingRoom(Arrays.asList(student));
            return;
        }
        throw new IllegalArgumentException("You can only place a student in the dining room or the waiting room");
    }

    @Override
    public void removes(StudentColor student, int place) throws NoSuchStudentException {
        if(place == 0) {
            removeStudentFromDiningRoom(student);
            return;
        }
        if(place == 1) {
            removeStudentsFromWaitingRoom(Arrays.asList(student));
            return;
        }
        throw new IllegalArgumentException("You can only place a student in the dining room or the waiting room");
    }
}