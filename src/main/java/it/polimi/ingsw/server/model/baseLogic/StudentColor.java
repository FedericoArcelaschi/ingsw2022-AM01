
package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;
import org.jetbrains.annotations.Range;


public enum StudentColor implements PossibleParameters {
    YELLOW("\u001B[33m"),
    GREEN("\u001B[32m"),
    PINK("\u001B[35m"),
    BLUE("\u001B[34m"),
    RED("\u001B[31m");

    private final String color;
    StudentColor(String color) {
        this.color = color;
    }

    public static StudentColor getColor(String s){
        for(StudentColor c : StudentColor.values())
            if(s.equalsIgnoreCase(c.name())) return c;
        throw new IllegalArgumentException(s + " is not a valid color name.");
    };

    public static StudentColor getColor(@Range(from = 0, to = 4) int i){
        switch (i){
            case 0: return GREEN;
            case 1: return RED;
            case 2: return YELLOW;
            case 3: return PINK;
            case 4: return BLUE;
            default: return null;
        }
    }

    public String getColorCode(){
        return color;
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

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public static StudentColor parseColor(String color) {
        for (StudentColor student : StudentColor.values()) {
            if(student.name().equalsIgnoreCase(color))
                return student;
        }
        return null;
    }

}
