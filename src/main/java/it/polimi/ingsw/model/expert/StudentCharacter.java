package it.polimi.ingsw.model.expert;
import it.polimi.ingsw.model.*;

public class StudentCharacter extends Character{

    public StudentCharacter(int cost, String explaination){
        super(cost, explaination);
    }

    @Override
    public String getExplanation() {
        return null;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public boolean applyEffect() {//TODO:
        return true;
    }
}
