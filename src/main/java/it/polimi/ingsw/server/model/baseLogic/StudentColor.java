
package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;


public enum StudentColor implements PossibleParameters {
    YELLOW(),
    GREEN(),
    PINK(),
    BLUE(),
    RED();

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

    public String getColorCode(StudentColor studentColor){
        return switch (studentColor){
            case YELLOW -> "\u001B[33m";
            case GREEN -> "\u001B[32m";
            case PINK -> "\u001B[35m";
            case BLUE -> "\u001B[34m";
            case RED -> "\u001B[31m";
        };
    }

    public String getColorCode(){
        return getColorCode(this);
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public String getPath() {
        return "students/student_" + name().toLowerCase() + ".png";
    }

    public String getCSS() {
        return "studentBackground" + name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }
}
