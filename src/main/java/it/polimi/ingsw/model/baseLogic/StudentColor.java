
package it.polimi.ingsw.model.baseLogic;

import it.polimi.ingsw.model.baseLogic.interfaces.PossibleParameters;


public enum StudentColor implements PossibleParameters {
    YELLOW("\u001B[33m"),
    GREEN("\u001B[32m"),
    PINK("\u001B[35m"),
    BLUE("\u001B[34m"),
    RED("\u001B[31m");

    public final String colorCode;

    StudentColor(String colorCode){
        this.colorCode = colorCode;
    }

    public static StudentColor getColor(String s){
        return switch (s.toLowerCase()){
            case "yellow" -> YELLOW;
            case "red" -> RED;
            case "blue" -> BLUE;
            case "pink" -> PINK;
            case "green" -> GREEN;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return colorCode + this.name().toLowerCase() + colorCode;
    }

    public String getPath() {
        return "students/student_" + name().toLowerCase() + ".png";
    }

    public String getCSS() {
        return "studentBackground" + name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }
}
