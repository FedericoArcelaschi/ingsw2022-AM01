
package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.Arrays;


public enum StudentColor implements PossibleParameters {

    GREEN("32"),
    RED("31"),
    YELLOW("38;2;252;233;79"),
    PINK("35"),
    BLUE("34");

    private final String colorCode;
    private static final String ESCAPE_CODE = "\u001b[";

    public static @NotNull StudentColor parseColor(String s) throws ParseException {
        for(StudentColor c : StudentColor.values())
            if(s.equalsIgnoreCase(c.name())) return c;
        throw new ParseException(s + " is not a valid colorCode name.", 0);
    }

    StudentColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getPath() {
        return "students/student_" + name().toLowerCase() + ".png";
    }

    public String getStudentCSS() {
        return "studentBackground" + name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }

    public String getTeacherCSS() {
        return "teacherBackground" + name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }

    public String toStringColored() {
        return ESCAPE_CODE + colorCode + "m" + this.name().toLowerCase() + ESCAPE_CODE + "0m";
    }
    public String toUppercaseStringColored() {
        return ESCAPE_CODE + colorCode + "m" + this.name() + ESCAPE_CODE + "0m";
    }

}
