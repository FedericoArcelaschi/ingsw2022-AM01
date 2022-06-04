package it.polimi.ingsw.server.model.baseLogic;
import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;

public enum Team implements PossibleParameters {
    BLACK,
    WHITE,
    GREY;

    public String getCSS(){
        return "tower" + name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }
}
