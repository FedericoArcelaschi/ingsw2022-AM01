package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.expert.Characters.CharactersList;
import it.polimi.ingsw.model.expert.Characters.Generic;
import it.polimi.ingsw.model.expert.Characters.Parameters;
import it.polimi.ingsw.model.expert.Characters.Tavern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertBoard extends Board {
    int idChar;
    private Tavern tavern;
    List<Generic> expertCharactersCards;

    public ExpertBoard(String playerID1, String playerID2){
        nPlayer = 2;
        setupClouds();
        castleMap.put(playerID1, new ExpertCastle(playerID1, Team.WHITE, nPlayer));
        castleMap.put(playerID2, new ExpertCastle(playerID2, Team.BLACK, nPlayer));
        setupIslands();
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3){
        nPlayer = 3;
        setupClouds();
        setupIslands();
        castleMap.put(playerID1, new ExpertCastle(playerID1, Team.WHITE, nPlayer));
        castleMap.put(playerID2, new ExpertCastle(playerID2, Team.BLACK, nPlayer));
        castleMap.put(playerID3, new ExpertCastle(playerID3, Team.GREY, nPlayer));
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3, String playerID4){
        nPlayer = 4;
        setupClouds();
        castleMap.put(playerID1, new ExpertCastle(playerID1, Team.WHITE, nPlayer));
        castleMap.put(playerID2, new ExpertCastle(playerID2, Team.BLACK, nPlayer));
        castleMap.put(playerID3, new ExpertCastle(playerID3, Team.WHITE, nPlayer));
        castleMap.put(playerID4, new ExpertCastle(playerID4, Team.BLACK, nPlayer));
        setupIslands();
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
    public boolean playExpertCard(int idChar, ExpertIsland island, int move, List<Color> studentsList){
        Generic ec = expertCharactersCards.get(expertCharactersCards.indexOf(CharactersList.values()[idChar]));
        String playerID = this.getTurn();
        Map<Parameters, Object> parametersMap = new HashMap<>();
        
        parametersMap.put(Parameters.PAY_TOKEN, payExpertCharacter(ec));
        parametersMap.put(Parameters.PLAYERID, playerID);
        parametersMap.put(Parameters.ISLAND, island);
        parametersMap.put(Parameters.CASTLE, castleMap.get(playerID));
        parametersMap.put(Parameters.PROFESSORMAP, professorMap);
        parametersMap.put(Parameters.STUDENTLIST, studentsList);
        parametersMap.put(Parameters.MOVE, move);

        ec.applyEffect(parametersMap);
    return true;
    }

    private boolean payExpertCharacter(Generic ec){
        String playerID = this.getTurn();
        ExpertCastle expcas = (ExpertCastle)castleMap.get(playerID);
        return expcas.payCharacter(ec.getCost());
    }

    private Island islandConquering(Island island){
        Map<Team, Integer> influence = island.calculateInfluence(getProfessorMap());
        Team t = teamWithMoreInfluence(influence);
        if(t != null) { island.setOwnership(t); }
        return island;
    }
}
