package it.polimi.ingsw.model;

import java.util.List;

public class Archipelago extends Island{
    int islandNumber;

    public Archipelago(Island island1, Island island2){
        super();
        this.islandNumber = island1.getIslandNumber()+island2.getIslandNumber();
        setOwnership(island1.getOwnership());
        addStudent(island1.getStudents());
        addStudent(island2.getStudents());
    }

    public Archipelago(Island island1, Island island2, Island island3){
        super();
        this.islandNumber = island1.getIslandNumber()+island2.getIslandNumber()+island3.getIslandNumber();
        setOwnership(island1.getOwnership());
        addStudent(island1.getStudents());
        addStudent(island2.getStudents());
        addStudent(island3.getStudents());
    }

    @Override
    public int getIslandNumber() {
        return islandNumber;
    }
}
