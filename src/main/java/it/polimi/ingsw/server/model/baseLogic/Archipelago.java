package it.polimi.ingsw.server.model.baseLogic;

public class Archipelago extends Island{
    int islandNumber;

    /**
     * Contructor that bonds two islands
     */
    public Archipelago(Island island1, Island island2){
        super();
        this.islandNumber = island1.getIslandNumber()+island2.getIslandNumber();
        setOwnership(island1.getOwnership());
        addStudent(island1.getStudents());
        addStudent(island2.getStudents());
    }

    /**
     * Contructor that bonds three islands
     */
    public Archipelago(Island island1, Island island2, Island island3){
        super();
        this.islandNumber = island1.getIslandNumber()+island2.getIslandNumber()+island3.getIslandNumber();
        setOwnership(island1.getOwnership());
        addStudent(island1.getStudents());
        addStudent(island2.getStudents());
        addStudent(island3.getStudents());
    }

    /**
     * Number of island that where bonded into this Archipelago
     * @return islandNumber ! >= 2
     */
    @Override
    public int getIslandNumber() {
        return islandNumber;
    }
}
