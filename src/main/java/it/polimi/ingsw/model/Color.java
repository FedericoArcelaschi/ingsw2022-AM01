package it.polimi.ingsw.model;

public enum Color {
    YELLOW("\u001B[33myellow\u001B[0m", "\u001B[33m"),
    GREEN("\u001B[32mgreen\u001B[0m", "\u001B[32m"),
    PINK("\u001B[35mpink\u001B[0m", "\u001B[35m"),
    BLUE("\u001B[34mblue\u001B[0m", "\u001B[34m"),
    RED("\u001B[31mred\u001B[0m", "\u001B[31m");

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
