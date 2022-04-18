package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpertIsland extends Island {
    /**
     * Value of blocked Island.
     * if it's true -> when the island is being conquered, is not conquered and this token becomes false.
     */
    private boolean isBLocked;

    public ExpertIsland(Color student) {
        super(student);
        this.isBLocked = false;
    }

    public ExpertIsland(){
        super();
        this.isBLocked = false;
    }

    /**
     * Method for the <em>Witch</em> Character: Blocks the Island from being conquered
     * @return true -> the island could be blocked and was successfully blocked
     */
    public boolean blockIsland(){
        if(isBLocked)
            return false;
        isBLocked = true;
        return true;
    }

    public boolean isBLocked(){return isBLocked;}

    public void unlockIsland(){isBLocked = false;}

}
