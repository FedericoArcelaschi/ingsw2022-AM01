package it.polimi.ingsw.model.expert;

public abstract class Character {
    int cost;
    String explanation;
    Map<ListCharacters, int cost, String explaination>
    public Character(int cost, String explanation) {
        this.cost = cost;
        this.explanation = explanation;
    }


    public  String getExplanation(){
        return explanation;
    }

    public int getCost(){
        return cost;
    }

    public abstract boolean applyEffect();

}
