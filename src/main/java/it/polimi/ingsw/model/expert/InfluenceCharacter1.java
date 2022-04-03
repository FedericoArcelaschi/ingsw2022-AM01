package it.polimi.ingsw.model.expert;

public class InfluenceCharacter1 extends Character{
    int cost;
    String explanation;

    public InfluenceCharacter1(int cost, String explanation, InfluenceCharacterExecution execution) {
        super(cost, explanation);

    }

    @Override
    public boolean applyEffect() {
        return false;
    }
}