package it.polimi.ingsw.server.model.baseLogic;
import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;

public enum Team implements PossibleParameters {
    BLACK("37;40"),
    WHITE("30;47"),
    GREY("48;2;211;215;207;30");

    private final String colorCode;
    private static final String ESCAPE_CODE = "\u001b[";

    Team(String colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public String toString() {
        return ESCAPE_CODE + colorCode + "m" + this.name() + ESCAPE_CODE + "0m";
    }
}
