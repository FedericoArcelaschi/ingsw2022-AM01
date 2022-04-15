package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.Characters.Generic;
import it.polimi.ingsw.model.expert.Characters.Parameters;
import it.polimi.ingsw.model.expert.Characters.Tavern;

import java.util.*;

public class ExpertBoard extends Board {
    private Tavern tavern;
    private List<Generic> expertCharactersCards;


    public ExpertBoard(String playerID1, String playerID2, Turn t){
        super(t);
        castleMap.put(playerID1, new ExpertCastle(playerID1, Team.WHITE, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID2, new ExpertCastle(playerID2, Team.BLACK, nPlayer,bag.multipleExtract(7)));
        construct();
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3, Turn t){
        super(t);
        castleMap.put(playerID1, new ExpertCastle(playerID1, Team.WHITE, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID2, new ExpertCastle(playerID2, Team.BLACK, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID3, new ExpertCastle(playerID3, Team.GREY, nPlayer, bag.multipleExtract(9)));
        construct();
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3, String playerID4, Turn t){
        super(t);
        castleMap.put(playerID1, new ExpertCastle(playerID1, Team.WHITE, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID2, new ExpertCastle(playerID2, Team.BLACK, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID3, new ExpertCastle(playerID3, Team.WHITE, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID4, new ExpertCastle(playerID4, Team.BLACK, nPlayer, bag.multipleExtract(7)));
        construct();
    }

    /**Cleans the contructors' implementation
     */
    private void construct(){
        setupIslands();
        drawExpertCharacters();
    }

    /**
     * Initializes <code>expertCharactersCards</code>. It's a factory method
     * @returns ArrayList<Characters>
     */
    private void drawExpertCharacters() {
        tavern = new Tavern(bag);
        expertCharactersCards = tavern.extract();
    }

    public void playExpertCard(int idChar, int move, List<Color> studentsList) throws NoSuchStudentException, TooManyStudentsException {
        playExpertCard(idChar, (ExpertIsland) this.getIslandList().get(0), move, studentsList);
    }

        /**
         * Tries to pay for a card and calls applyEffect with the right parameters
         * @param idChar
         * @return if works -> true else false
         */
    public boolean playExpertCard(int idChar, ExpertIsland island, int move, List<Color> studentsList) throws NoSuchStudentException, TooManyStudentsException {
        String playerID = this.getTurn();
        Generic ec = expertCharactersCards.get(idChar);
        if(((ExpertCastle)castleMap.get(playerID)).payCharacter(ec.getCost())) {
            //in/out parameters for the applyEffect method
            Map<Parameters, Object> parametersMap = new HashMap<>();
            //Parameter to setup: (will clean code up)
            if(island == null) island = (ExpertIsland) getIslandList().get(0);
            parametersMap.putAll(Map.of(
                Parameters.PAY_TOKEN,((ExpertCastle) castleMap.get(playerID)).payCharacter(ec.getCost()),
                Parameters.PLAYERID, playerID,
                Parameters.ISLAND, island,
                Parameters.CASTLEMAP, castleMap,
                Parameters.PROFESSORMAP, professorMap,
                Parameters.STUDENTLIST, studentsList,
                Parameters.MOVE, move));
            if (ec.applyEffect(parametersMap))
                return true;
            else ((ExpertCastle)castleMap.get(playerID)).unpayCharacter(ec.getCost());
        }
            return false;
        //at this point in the view I would print the reasons why it could have stopped (coins, wrong parameters, etc..)
    }

    /**sets up for the ExpertIslands
     */
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

    /**adds to the available Characters also the Character #idChar.
     * @param idChar
     */
    public void setup4CharacterTesting(int idChar){
        expertCharactersCards.set(idChar, tavern.extract4testing(idChar));
    }

    /**For testing: returns the list of available characters
     */
    public List<Generic> getAvailableCharacterCards(){
        return expertCharactersCards;
    }

    public Bag getBag() {return bag;
    }
}
