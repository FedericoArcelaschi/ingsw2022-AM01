package it.polimi.ingsw.model.expert; 

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;

import java.util.List;

public class ExpertCastle extends Castle {

    private int coins;

    /**
     * Castle default constructor
     * @param studentList student list to initialize the waiting room
     */
    public ExpertCastle(Team team, int nPlayer, List<Color> studentList) {
        super(team, nPlayer, studentList);
        coins = 1;
    }

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
            throw new NoSuchStudentException("Student " + color + "not in dining room", color);
    }

    /**
     * Removes the cost of coins from the castle
     */
    public void payCharacter(int price) {
        if(this.coins >= price)
            this.coins -= price;
        if(coins < 0)
            throw new IllegalStateException("the player didn't have the money to pay for the character");
    }

    public int getCoins() {
        return coins;
    }

}