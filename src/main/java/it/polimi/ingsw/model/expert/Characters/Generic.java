package it.polimi.ingsw.model.expert.Characters;

public abstract class Generic {
    final int cost;
    final String explaination;
    protected int IdChar;
    final CharactersList lc = null; //in fatto che sia final mi triggera un po' e non so se va' bene che l'ho inizializzato a null come ha chiesto l'IDE
    int idChar; //non so se serve forse no.

    public Generic(int idChar){
        this.idChar = idChar;
        CharactersList lc = CharactersList.values()[idChar];
        cost = lc.getCost();
        explaination = lc.getExplaination();
    }

    public String getExplanation(){
        return explaination;
    }

    public int getCost(){
        return cost;
    }

    public abstract boolean applyEffect();
}
