package it.polimi.ingsw.model;

import java.util.List;
import java.util.Map;

public class Archipelago extends Island{
    private int islandNumber;

    public Archipelago(Color students, List<Island> islandList){
        super(students);
        int in = 0;
        for (Island island : islandList) {
            in += island.getIslandNumber();
        }
        this.islandNumber = in;
    }

    public int getIslandNumber(){
        return islandNumber;
    }
}
