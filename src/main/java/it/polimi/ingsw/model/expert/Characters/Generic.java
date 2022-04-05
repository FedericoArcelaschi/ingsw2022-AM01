package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.expert.ExpertIsland;

import java.util.List;
import java.util.Map;

public abstract class Generic {
    final int cost;
    final String explaination;
    protected int IdChar;
    final CharactersList lc = null; //in fatto che sia final mi triggera un po' e non so se va' bene che l'ho inizializzato a null come ha chiesto l'IDE
    int idChar; //non so se serve forse no.

    /**
     * requires idChar between 1 and 12.
     * @param idChar
     */
    public Generic(int idChar){
        this.idChar = idChar;
        CharactersList lc = CharactersList.values()[idChar-1];
        cost = lc.getCost();
        explaination = lc.getExplaination();
    }

    public String getExplanation(){
        return explaination;
    }

    public int getCost(){
        return cost;
    }

    public abstract boolean applyEffect(ExpertIsland island, String player, Castle castle, Map<String, Color> professorMap, boolean payedToken, int move, List<Color> students);

}
