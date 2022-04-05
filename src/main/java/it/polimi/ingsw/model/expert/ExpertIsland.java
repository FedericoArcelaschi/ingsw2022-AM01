package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;

public class ExpertIsland extends Island {
    boolean isBLocked;

    public ExpertIsland(Color student) {
        super(student);
        this.isBLocked = false;
    }

    public boolean block(){
        if(isBLocked)
            return false;
        isBLocked = true;
        return true;
    }
}
