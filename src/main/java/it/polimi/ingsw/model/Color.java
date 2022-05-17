package it.polimi.ingsw.model;

import it.polimi.ingsw.model.expert.boardInterfaces.PossibleParameters;

public enum Color implements PossibleParameters {
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
