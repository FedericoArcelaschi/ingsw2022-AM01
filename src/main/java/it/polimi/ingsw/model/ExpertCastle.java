package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertCastle extends Castle{
    private int coins;

    public ExpertCastle(String PLayerID, Team team, int nPlayer){
        super(PLayerID, team, nPlayer);
        this.coins = 0;
    }

    public boolean addStudentsInWaitingRoom(List<Color> c){
        try {
            List<Color> waitingRoom = new ArrayList<>(getWaitingRoom());
            for (Color col : c) {
                waitingRoom.remove(col);
            }
            setWaitingRoom(waitingRoom);
                return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes a student from the dining room. Only available in expert mode due to the presence of characters that allow this.
     * @param c
     * @return boolean
     */
    public boolean removeDR(List<Color> c){
        try {
            Map<Color, Integer> diningRoom = new HashMap<>(getDiningRoom());
            for (Color col : c) {
                diningRoom.put(col, diningRoom.get(col) - 1);
            }
            setDiningRoom(diningRoom);
            return true;
        }catch(NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    /** Pays the character to perform the action.
     *
     * @return
     */
    //public boolean payChar(){
    //
    //}
}
