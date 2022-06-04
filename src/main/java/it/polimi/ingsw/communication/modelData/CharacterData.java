package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;

import java.util.ArrayList;
import java.util.List;

public class CharacterData {
    private StandardCharacter character;

    public CharacterData(StandardCharacter character){
        this.character = character;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(character.getName()).append(", cost: ").append(character.getCost());
        return s.toString();
    }
}
