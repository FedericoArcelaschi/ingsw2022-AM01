package it.polimi.ingsw.model.expert; 

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;

import java.util.List;

public class ExpertCastle extends Castle {
    private int coins;
    public ExpertCastle(String PLayerID, Team team, int nPlayer, List<Color> Students) {
        super(PLayerID, team, nPlayer, Students);
        coins = 1;
    }

    /**
     * Adds also a coin to the player
     * @param color color of the student to add
     * @return if the bounds are respected
     */
    public void addStudentInDiningRoom(Color color) throws TooManyStudentsException {
        super.addStudentInDiningRoom(color);
        if (diningRoom.get(color) % 3 == 0)
            coins++;
    }

    /**
     * Adds a coin to this player
     * @param students – The list of students to add to the dining room.
     * @throws TooManyStudentsException
     */
    public void addStudentsInDiningRoom(List<Color> students) throws TooManyStudentsException {
        boolean check = true;
        for (Color c: students) {
            addStudentInDiningRoom(c);
        }
    }


    /**
     * Removes a student from the Dining room.
     * Only available in Expert mode.
     * @param color color of the student remove
     */
    public void removeStudentFromDiningRoom(Color color) throws NoSuchStudentException {
        if(diningRoom.get(color) > 0){
            diningRoom.replace(color, diningRoom.get(color)-1);
        }
        else
            throw new NoSuchStudentException("Student " + color + "not in dining room");
    }

    /**Checks if the player can pay and if possible
     * Removes coins from the castle
     * @param price cost of the character that the player wants to buy
     * @return true if the transaction was successful
     */
    public boolean payCharacter(int price) {
        if(this.coins >= price) {
            this.coins -= price;
            return true;
        }
        return false;
    }

    /**
     * If the character doesn't <em>apply the effect</em> gives back to the player the money
     * @param price
     */
    public void unpayCharacter(int price){
        this.coins += price;
    }

    public int getCoins() {
        return coins;
    }

}