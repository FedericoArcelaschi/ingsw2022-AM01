package it.polimi.ingsw.model.expert;

public class InfluenceCharacter1 extends InfluenceCharacter{
    int cost;
    String explanation;

    public InfluenceCharacter1(int cost, String explanation, InfluenceCharacterExecution execution) {

    }

    @Override
    public boolean applyEffect() {
        return false;
    }
}