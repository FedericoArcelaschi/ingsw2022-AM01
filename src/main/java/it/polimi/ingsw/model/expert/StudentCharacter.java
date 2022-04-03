package it.polimi.ingsw.model.expert;
import it.polimi.ingsw.model.*;

public class StudentCharacter extends Character {

    public StudentCharacter(int cost, String explaination){
        super(cost, explaination);
    }
    @Override
    public boolean applyEffect() {//TODO:
        return true;
    }
}
