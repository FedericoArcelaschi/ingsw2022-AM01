package it.polimi.ingsw.communication.modelData.expertMode;

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
        return character.getName() + ", cost: " + character.getCost();
    }
}
