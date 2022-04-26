package it.polimi.ingsw.model;

public enum Color {
    YELLOW("yellow"),
    GREEN("green"),
    PINK("pink"),
    BLUE("blue"),
    RED("red");

    public final String str;

    public String getStr() {
        return str;
    }

    Color(String str){
        this.str = str;
    }

    String getName(){
        return str;
    }

}
