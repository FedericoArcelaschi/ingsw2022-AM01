package it.polimi.ingsw.model.expert;

public class InfluenceCharacter extends Character{
    int cost;
    String explanation;

    public InfluenceCharacter(int cost, InfluenceCharacterExecution execution) {
        String explanation = "Gain the influence over a Professor even if in your castle \n you have as many students  "
        super(cost, explanation);

    }

    @Override
    public boolean applyEffect() {
        return false;
    }
}