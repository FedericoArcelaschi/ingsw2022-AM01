package it.polimi.ingsw.model;

public enum Color {
    YELLOW("yellow", "\u001B[33m"),
    GREEN("green", "\u001B[32m"),
    PINK("pink", "\u001B[35m"),
    BLUE("blue", "\u001B[34m"),
    RED("red", "\u001B[31m");

    public final String str;
    public final String colorCode;

    public String getStr() {
        return str;
    }

    Color(String str, String colorCode){
        this.str = str;
        this.colorCode = colorCode;
    }

    String getName(){
        return str;
    }

}
