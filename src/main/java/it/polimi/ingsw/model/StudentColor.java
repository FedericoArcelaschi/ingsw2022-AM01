package it.polimi.ingsw.model;

import it.polimi.ingsw.userInterface.gui.ResourcesPath;

public enum StudentColor {
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
    public String getStr() {
        return str;
    }

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
        switch (s){
            case "yellow" -> {
                return StudentColor.YELLOW;
            }
            case "red" -> {
                return StudentColor.RED;
            }
            case "blue" -> {
                return StudentColor.BLUE;
            }
            case "pink" -> {
                return StudentColor.PINK;
            }
            case "green" -> {
                return StudentColor.GREEN;
            }
        }
        return null;
    }

}
