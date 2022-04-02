package it.polimi.ingsw.model;

import java.util.List;

public class Archipelago extends Island{
    int islandNumber;

    public Archipelago(List<Island> islands){
        super();
        this.islandNumber = islands.size();
        setOwnership(islands.get(0).getOwnership());
        for(Island i : islands){
            addStudent(i.getStudents());
        }
    }

    @Override
    public int getIslandNumber() {
        return islandNumber;
    }
}
