
package it.polimi.ingsw.model.baseLogic;

import it.polimi.ingsw.model.baseLogic.interfaces.PossibleParameters;


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

    public String getColorCode(){
        return color;
    }

    public String getPath() {
        return "students/student_" + name().toLowerCase() + ".png";
    }

    public String getCSS() {
        return "studentBackground" + name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}
