package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.expert.Characters.CharactersList;
import it.polimi.ingsw.model.expert.Characters.Generic;
import it.polimi.ingsw.model.expert.Characters.Tavern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertBoard extends Board {
    int idChar;
    private Tavern tavern;
    List<Generic> expertCharactersCards;
    private final List<ExpertIsland> islandList = new ArrayList<>();
    private final Map<String, ExpertCastle> castleMap = new HashMap<>();

    public ExpertBoard(String playerID1, String playerID2) {
        super(playerID1, playerID2);
        for (Island i: super.getIslandList()){
            islandList.add((ExpertIsland)i);
        }
        expertCharactersCards = this.drawExpertCharacters();
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3) {
        super();
        setupIslands();
        setupClouds();
        castleMap.put(playerID1, new ExpertCastle(playerID1, Team.WHITE, nPlayer));
        castleMap.put(playerID2, new ExpertCastle(playerID2, Team.BLACK, nPlayer));
        castleMap.put(playerID3, new ExpertCastle(playerID3, Team.GREY, nPlayer));
        this.expertCharactersCards = this.drawExpertCharacters();
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3, String playerID4) {
        super(playerID1, playerID2, playerID3, playerID4);
        expertCharactersCards = this.drawExpertCharacters();
    }

    /**
     * Initializes <code>expertCharactersCards</code>. It's a factory method
     * @returns ArrayList<Characters>
     */
    private List<Generic> drawExpertCharacters() {
        tavern = new Tavern(this);
        return tavern.extract();
    }
    /**
     * Tries to pay for a card and activates the right method.
     * @param idChar
     * @return if works -> true else false
     */
    public boolean playExpertCard(int idChar, ExpertIsland island, Map<String, Color> professorMap, int move, List<Color> students){
        Generic ec = expertCharactersCards.get(expertCharactersCards.indexOf(CharactersList.values()[idChar]));
        String playerID = this.getTurn();
        ec.applyEffect(island, playerID, castleMap.get(playerID), professorMap, payExpertCharacter(ec), move, null );
    return true;
    }

    private boolean payExpertCharacter(Generic ec){
        String playerID = this.getTurn();
        return castleMap.get(playerID).payCharacter(ec.getCost());
    }

    private Island islandConquering(Island island){
        Map<Team, Integer> influence = island.calculateInfluence(getProfessorMap());
        Team t = teamWithMoreInfluence(influence);
        if(t != null) { island.setOwnership(t); }
        return island;
    }

    public Map<String, ExpertCastle> getExpCastleMap() {
        return new HashMap<>(castleMap);
    }
    public List<ExpertIsland> getExpIslandList() {
        return new ArrayList<>(islandList);
    }

}
