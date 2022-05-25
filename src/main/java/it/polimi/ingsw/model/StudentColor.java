package it.polimi.ingsw.model;

import it.polimi.ingsw.userInterface.gui.ResourcesPath;

public enum StudentColor {
    YELLOW("\u001B[33myellow\u001B[0m", "\u001B[33m", ResourcesPath.STUDENTS.path + "students/student_yellow.png"),
    GREEN("\u001B[32mgreen\u001B[0m", "\u001B[32m", ResourcesPath.STUDENTS.path + "students/student_green.png"),
    PINK("\u001B[35mpink\u001B[0m", "\u001B[35m", ResourcesPath.STUDENTS.path + "students/student_pink.png"),
    BLUE("\u001B[34mblue\u001B[0m", "\u001B[34m", ResourcesPath.STUDENTS.path + "students/student_blue.png"),
    RED("\u001B[31mred\u001B[0m", "\u001B[31m", ResourcesPath.STUDENTS.path + "students/student_red.png");

    public final String str;
    public final String colorCode;
    public final String path;

    public String getStr() {
        return str;
    }

    StudentColor(String str, String colorCode, String path){
        this.str = str;
        this.colorCode = colorCode;
        this.path = path;
    }

    String getName(){
        return str;
    }

}
