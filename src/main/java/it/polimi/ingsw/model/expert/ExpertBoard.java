package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.expert.Characters.ExpertCharacter;
import it.polimi.ingsw.model.expert.Characters.Tavern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertBoard extends Board {
    private Tavern tavern;
    List<ExpertCharacter> expertCharactersCards;

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
    private List<ExpertCharacter> drawExpertCharacters(){
        tavern = new Tavern(this.bag);
        return tavern.extract();
    }
    /**
     * Calculates the influence for <code>InfluenceCharacters (MONK)</code>
     * <code>parameter</code></code> changes based on the active <code>Character Effect</code>
     * @return <code>professorMap</code>
     */
    public Map<Color, Castle> getProfessorMap(int parameter) {
        return new HashMap<>(professorMap);
    }

    /**
     * Tries to pay for a card and activates the right method.
     * @param idChar
     * @return
     */
    public boolean playExpertCard(int idChar){return true;}

}
