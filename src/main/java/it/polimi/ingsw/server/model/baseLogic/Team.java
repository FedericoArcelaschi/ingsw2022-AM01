package it.polimi.ingsw.server.model.baseLogic;
import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;

public enum Team implements PossibleParameters {
    BLACK("37;40"),
    WHITE("30;48;2;255;255;255"),
    GREY("48;2;211;215;207;30");

    private final String colorCode;
    private static final String ESCAPE_CODE = "\u001b[";

    Team(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getCSS(){
        return "tower" + name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return ESCAPE_CODE + colorCode + "m" + this.name() + ESCAPE_CODE + "0m";
    }
}
