package it.polimi.ingsw.model;

public enum Color {
    YELLOW(1),
    GREEN(2),
    PINK(3),
    BLUE(4),
    RED(5);

    private final int id;

    Color(int id){
        this.id = id;
    }
}
