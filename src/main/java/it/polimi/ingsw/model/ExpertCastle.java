package it.polimi.ingsw.model;

import java.util.List;

public class ExpertCastle extends Castle{
    private int coins;

    public ExpertCastle(String PLayerID, Team team, int nPlayer, List<Color> students){
        super(PLayerID, team, nPlayer, students);
        this.coins = 0;
    }

    public int getCoins() {
        return coins;
    }

    /**
     * same as the super method but check if the player get a coin
     * @param color color of the student to add
     * @return if the bounds are respected
     */

    public boolean addStudentInDiningRoom(Color color){
        int nStudentsBefore, nStudentsAfter;
        nStudentsBefore = diningRoom.get(color);
        super.addStudentsInDiningRoom(color);
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
     * @param cost cost of the character that the player wants to buy
     * @return if the player can afford to pay the cost
     */
    public boolean payChar(int cost){
        if(coins >= cost){
            coins -= cost;
            return true;
        }
        else return false;
    }
}
