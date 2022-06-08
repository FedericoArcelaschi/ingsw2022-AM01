package it.polimi.ingsw.communication.modelData.expertMode;

import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;

import java.util.ArrayList;
import java.util.List;

public class CharacterData {
    private final String name;
    private final int cost;

    public CharacterData(String name, int cost){
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString(){
        return name + ", cost: " + cost;
    }
}
