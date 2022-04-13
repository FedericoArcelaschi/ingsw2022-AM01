package it.polimi.ingsw.model.expert; 

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import org.jetbrains.annotations.NotNull;

import java.util.ConcurrentModificationException;
import java.util.List;

public class ExpertCastle extends Castle {
    private int coins;
    public ExpertCastle(String PLayerID, Team team, int nPlayer) {
        super(PLayerID, team, nPlayer);
        coins = 1;
    }

    /**same as the super method but check adds a coin to the player
     * @param color color of the student to add
     * @return if the bounds are respected
     */
    public boolean addStudentInDiningRoom(Color color){
        int nStudentsBefore, nStudentsAfter;
        nStudentsBefore = diningRoom.get(color);
        //super.addStudentsInDiningRoom(color);
        nStudentsAfter = diningRoom.get(color);
        if(nStudentsBefore/3 < nStudentsAfter/3)
            coins++;
        return true;
    }

    /**
     * Removes a student from the dining room. Only available in expert mode due to the presence of characters that allow this.
     * @param color color of the student to remove
     * @return boolean if the student can be removed
     */
    public boolean removeStudentFromDiningRoom(Color color){
        if(diningRoom.get(color) > 0){
            diningRoom.replace(color, diningRoom.get(color)-1);
            return true;
        }
        else
            return false;
    }

    /** remove coins form the castle
     * @param price cost of the character that the player wants to buy
     * @return if the player can afford to pay the cost
     */
    public boolean payCharacter(int price) {
        if(this.coins >= price) {
            this.coins -= price;
            return true;
        }else
            return false;
    }

    public void unpayCharacter(int price){
        this.coins += price;
    }

    public int getCoins() {
        return coins;
    }

}