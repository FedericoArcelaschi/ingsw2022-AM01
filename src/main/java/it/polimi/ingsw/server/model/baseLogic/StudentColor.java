
package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;


public enum StudentColor implements PossibleParameters {
    YELLOW("38;2;252;233;79"),
    GREEN("32"),
    PINK("35"),
    BLUE("34"),
    RED("31");

    private final String colorCode;
    private static final String ESCAPE_CODE = "\u001b[";

    StudentColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getPath() {
        return "students/student_" + name().toLowerCase() + ".png";
    }

    public String getCSS() {
        return "studentBackground" + name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }

    public static StudentColor getColor(String s) {
        for(StudentColor c : StudentColor.values())
            if(s.equalsIgnoreCase(c.name())) return c;
        throw new IllegalArgumentException(s + " is not a valid colorCode name.");
    }

    @Override
    public String toString() {
        return ESCAPE_CODE + colorCode + "m" + this.name().toLowerCase() + ESCAPE_CODE + "0m";
    }

}
