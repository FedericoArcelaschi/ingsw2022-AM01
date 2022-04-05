package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;

import java.util.ConcurrentModificationException;
import java.util.List;

public class ExpertCastle extends Castle {
    private int coins;
    public ExpertCastle(String PLayerID, Team team, int nPlayer) {
        super(PLayerID, team, nPlayer);
        coins = 1;
    }

    @Override
    public boolean addStudentDR(List<Color> c) {
        try {
            for (Color col : c) {
                if(diningRoom.containsKey(col))
                    diningRoom.put(col, diningRoom.get(col) + 1);
                else diningRoom.put(col, 1);
                if((diningRoom.get(col) + 1) % 3 == 0)
                    coins++;
            }
            return true;
        }catch(NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds internally the coins when Students are added in the write positions
     * It's the only way to get coins
     */
    public boolean payCharacter(int price) {
        if(this.coins >= price) {
            this.coins = this.coins - price;
            return true;
        }else
            return false;
    }
}

