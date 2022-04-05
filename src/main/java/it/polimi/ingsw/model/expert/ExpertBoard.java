package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.expert.Characters.Generic;
import it.polimi.ingsw.model.expert.Characters.Tavern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertBoard extends Board {
    private Tavern tavern;
    List<Generic> expertCharactersCards;
    int characterToken;

    public ExpertBoard(String playerID1, String playerID2) {
        super(playerID1, playerID2);
        expertCharactersCards = this.drawExpertCharacters();
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3, String playerID4) {
        super(playerID1, playerID2, playerID3, playerID4);
        expertCharactersCards = this.drawExpertCharacters();
    }

    /**
     * Initializes <code>expertCharactersCards</code>. It's a factory method
     * @returns ArrayList<Characters>
     */
    private List<Generic> drawExpertCharacters(){
        characterToken = 0;
        tavern = new Tavern(this.bag);
        return tavern.extract();
    }

    /**
     * Tries to pay for a card and activates the right method.
     * @param idChar
     * @return if works -> true else false //TODO: review
     */
    public boolean playExpertCard(int idChar){return true;}

    private Island islandConquering(Island island){
        Map<Team, Integer> influence = island.calculateInfluence(getProfessorMap());
        Team t = teamWithMoreInfluence(influence);
        if(t != null) { island.setOwnership(t); }
        return island;
    }

}
