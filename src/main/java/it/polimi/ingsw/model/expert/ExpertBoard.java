package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.expert.Characters.CharactersList;
import it.polimi.ingsw.model.expert.Characters.Generic;
import it.polimi.ingsw.model.expert.Characters.Parameters;
import it.polimi.ingsw.model.expert.Characters.Tavern;

import java.util.*;

public class ExpertBoard extends Board {
    private int idChar;
    private Tavern tavern;
    private List<Generic> expertCharactersCards;

    public ExpertBoard(String playerID1, String playerID2){
        nPlayer = 2;
        setupClouds();
        setupIslands();
        drawExpertCharacters();
        turn = new Turn(Arrays.asList(playerID1, playerID2));
        castleMap.put(playerID1, new ExpertCastle(playerID1, Team.WHITE, nPlayer));
        castleMap.put(playerID2, new ExpertCastle(playerID2, Team.BLACK, nPlayer));
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3){
        nPlayer = 3;
        setupClouds();
        setupIslands();
        drawExpertCharacters();
        castleMap.put(playerID1, new ExpertCastle(playerID1, Team.WHITE, nPlayer));
        castleMap.put(playerID2, new ExpertCastle(playerID2, Team.BLACK, nPlayer));
        castleMap.put(playerID3, new ExpertCastle(playerID3, Team.GREY, nPlayer));
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3, String playerID4){
        nPlayer = 4;
        setupClouds();
        setupIslands();
        drawExpertCharacters();
        castleMap.put(playerID1, new ExpertCastle(playerID1, Team.WHITE, nPlayer));
        castleMap.put(playerID2, new ExpertCastle(playerID2, Team.BLACK, nPlayer));
        castleMap.put(playerID3, new ExpertCastle(playerID3, Team.WHITE, nPlayer));
        castleMap.put(playerID4, new ExpertCastle(playerID4, Team.BLACK, nPlayer));
    }

    /**
     * Initializes <code>expertCharactersCards</code>. It's a factory method
     * @returns ArrayList<Characters>
     */
    private void drawExpertCharacters() {
        tavern = new Tavern(this);
        expertCharactersCards = tavern.extract();
    }
    /**
     * Tries to pay for a card and activates the right method.
     * @param idChar
     * @return if works -> true else false
     */
    public boolean playExpertCard(int idChar, ExpertIsland island, int move, List<Color> studentsList){
        idChar--;
        //DOESN'T WORK:
        //expertCharactersCards.indexOf((CharactersList.values()[idChar]).equals(CharactersList.values()[idChar])));
        Generic ec = expertCharactersCards.get(expertCharactersCards.indexOf(CharactersList.values()[idChar]));
        String playerID = this.getTurn();
        Map<Parameters, Object> parametersMap = new HashMap<>();
        parametersMap.put(Parameters.PAY_TOKEN, payCharacter(ec));
        parametersMap.put(Parameters.PLAYERID, playerID);
        parametersMap.put(Parameters.ISLAND, island);
        parametersMap.put(Parameters.CASTLE, castleMap.get(playerID));
        parametersMap.put(Parameters.PROFESSORMAP, professorMap);
        parametersMap.put(Parameters.STUDENTLIST, studentsList);
        parametersMap.put(Parameters.MOVE, move);
        ec.applyEffect(parametersMap);
    return true;
    }

    private boolean payCharacter(Generic ec){
        String playerID = this.getTurn();
        ExpertCastle expcas = (ExpertCastle)castleMap.get(playerID);
        return expcas.payCharacter(ec.getCost());
    }

    private void setupIslands(){
        List<Color> s = bag.extractForIslandSetup();
        for(int i=0, c=0; i<12; i++){
            if(i%6 == 0){
                islandList.add(new ExpertIsland());
            }
            else{
                islandList.add(new ExpertIsland(s.get(c)));
                c++;
            }
        }
    }

    public void setup4CharacterTesting(int idChar){
        expertCharactersCards.add(tavern.extract4testing(idChar));
    }
    public List<Generic> getAvailableCharacterCards(){
        return expertCharactersCards;
    }
}
