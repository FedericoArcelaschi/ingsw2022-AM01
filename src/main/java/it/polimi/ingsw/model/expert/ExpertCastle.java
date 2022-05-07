package it.polimi.ingsw.model.expert; 

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.boardInterfaces.StudentPlaces;

import java.util.Arrays;
import java.util.List;

public class ExpertCastle extends Castle implements StudentPlaces {

    //TODO: should use a decorator or a "sottoclasse"
    private int coins;

    /**
     * Castle default constructor
     * @param studentList student list to initialize the waiting room
     */
    public ExpertCastle(Team team, int nPlayer, List<Color> studentList) {
        super(team, nPlayer, studentList);
        coins = 1;
    }

    //ONLY METHODS FOR EXPERTMODE:
    //COINS:
    /**
     * Adds also a coin to the player
     * @param color color of the student to add
     * @throws TooManyStudentsException if the dining room already contains 10 students of the color <code>color</code>
     */
    @Override
    public void addStudentInDiningRoom(Color color) throws TooManyStudentsException {
        super.addStudentInDiningRoom(color);
        if (diningRoom.get(color) % 3 == 0)
            coins++;
    }

    /**
     * Adds a coin to this player
     * @param students The list of students to add to the dining room.
     * @throws TooManyStudentsException if the dining room already contains 10 students of one of the colors
     */
    @Override
    public void addStudentsInDiningRoom(List<Color> students) throws TooManyStudentsException {
        for (Color c: students) {
            addStudentInDiningRoom(c);
        }
    }

    /**
     * Removes a student from the Dining room.
     * Only available in Expert mode.
     * @param color color of the student to remove
     */
    public void removeStudentFromDiningRoom(Color color) throws NoSuchStudentException {
        if(diningRoom.get(color) > 0){
            diningRoom.replace(color, diningRoom.get(color)-1);
        }
        else
            throw new NoSuchStudentException("Student " + color + " not in dining room");
    }

    /**
     * Removes the cost of coins from the castle
     */
    public void payCharacter(int price) {
        if(this.coins >= price)
            this.coins -= price;
        else
            throw new IllegalStateException("the player didn't have the money to pay for the character");
    }

    public int getCoins() {
        return coins;
    }

//EXPERTCHARACTER IMPLEMENTATION:
    @Override
    public void adds(Color student, int place) throws TooManyStudentsException {
        if(place == 0){
            addStudentInDiningRoom(student);
            return;
        }
        if(place == 1){
            addStudentsInWaitingRoom(Arrays.asList(student));
            return;
        }
        throw new IllegalArgumentException("You can only place a student in the dining room or the waiting room");
    }

    @Deprecated
    @Override
    public void adds(Color student) throws IllegalAccessException {
        throw new IllegalAccessException("This method should be used only for Islands classes.");
    }

    @Override
    public void removes(Color student, int place) throws NoSuchStudentException {
        if(place == 0){
            removeStudentFromDiningRoom(student);
            return;
        }
        if(place == 1){
            removeStudentsFromWaitingRoom(Arrays.asList(student));
            return;
        }
        throw new IllegalArgumentException("You can only place a student in the dining room or the waiting room");
    }
}