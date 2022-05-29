/*
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
}
*/
package it.polimi.ingsw.model.baseLogic;

import it.polimi.ingsw.model.baseLogic.interfaces.PossibleParameters;
import it.polimi.ingsw.userInterface.gui.ResourcesPath;

public enum StudentColor implements PossibleParameters {
    YELLOW("\u001B[33myellow\u001B[0m", "\u001B[33m", ResourcesPath.STUDENTS.path + "students/student_yellow.png", "yellow", "studentBackgroundYellow"),
    GREEN("\u001B[32mgreen\u001B[0m", "\u001B[32m", ResourcesPath.STUDENTS.path + "students/student_green.png", "green", "studentBackgroundGreen"),
    PINK("\u001B[35mpink\u001B[0m", "\u001B[35m", ResourcesPath.STUDENTS.path + "students/student_pink.png", "pink", "studentBackgroundPink"),
    BLUE("\u001B[34mblue\u001B[0m", "\u001B[34m", ResourcesPath.STUDENTS.path + "students/student_blue.png", "blue", "studentBackgroundBlue"),
    RED("\u001B[31mred\u001B[0m", "\u001B[31m", ResourcesPath.STUDENTS.path + "students/student_red.png", "red", "studentBackgroundRed");

    public final String str;
    public final String colorCode;
    public final String path;
    public final String color;
    public final String cssClass;

    StudentColor(String str, String colorCode, String path, String color, String cssClass){
        this.str = str;
        this.colorCode = colorCode;
        this.path = path;
        this.color = color;
        this.cssClass = cssClass;
    }

    String getName(){
        return str;
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

}
