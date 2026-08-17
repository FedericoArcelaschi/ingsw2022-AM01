package it.polimi.ingsw.server.model.baseLogic;

import java.util.List;

public class Archipelago extends Island {

    int islandNumber;

    /**
     * Constructor that bonds 2 or 3 islands
     */
    public Archipelago(List<Island> islandList) {
        super();
        if(islandList.size() < 2 || islandList.size() > 3)
            throw new IllegalArgumentException("not a valid number of islands");
        setOwnership(islandList.get(0).getOwnership());
        islandList.forEach(island -> islandNumber += island.getIslandNumber());
        islandList.forEach(island -> this.addStudent(island.getStudents()));
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
