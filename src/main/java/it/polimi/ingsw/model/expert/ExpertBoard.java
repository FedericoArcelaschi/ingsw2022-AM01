package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.Characters.CharactersList;
import it.polimi.ingsw.model.expert.Characters.Generic;
import it.polimi.ingsw.model.expert.Characters.Parameters;
import it.polimi.ingsw.model.expert.Characters.Tavern;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertBoard extends Board {
    private Tavern tavern;
    private List<Generic> expertCharactersCards;
    private int expertPlayedCard;

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

    /**Sets up for the ExpertIslands
     */
    private void setupIslands(){
        List<Color> s = bag.extractForIslandSetup();
        for(int i = 0; i < 12; i++){
            if(i % 6 != 0){
                islandList.add(i, new ExpertIsland(s.get(0)));
                s.remove(0);
            }else
                islandList.add(i, new ExpertIsland());
        }
    }

    /**
     * Initializes <code>expertCharactersCards</code>. It's a factory method
     */
    private void drawExpertCharacters() {
        tavern = new Tavern(bag);
        expertCharactersCards = tavern.extract();
    }

    /**
     * Tries to pay for the card and then calls applyEffect with the right parameters
     *
     * @param idChar character id correspondingo to CharacterList's position
     * @return if works -> true else false
     */
    public boolean playExpertCard(int idChar, ExpertIsland island, List<Color> studentsList) throws StudentException {
        String playerID = this.getCurrentPlayer();
        Generic ec = expertCharactersCards.get(idChar);
        if (ec != null && ((ExpertCastle) castleMap.get(playerID)).payCharacter(ec.getCost())) {

            //in/out parameters for the applyEffect method
            Map<Parameters, Object> parametersMap = new HashMap<>();
            //Parameter for setup (will clean code up)

            if (island == null) island = (ExpertIsland) getIslandList().get(0);
            parametersMap.putAll(Map.of(
                    Parameters.PLAYERID, playerID,
                    Parameters.ISLAND, island,
                    Parameters.CASTLEMAP, castleMap,
                    Parameters.PROFESSORSMAP, professorsMap,
                    Parameters.STUDENTLIST, studentsList));
            try {
                ec.applyEffect(parametersMap);
            } catch (StudentException e) {
                ((ExpertCastle) castleMap.get(playerID)).unpayCharacter(ec.getCost());
                throw new StudentException(e);
            }
            return true;
        }
        System.out.println("Character " + CharactersList.values()[idChar - 1] + "too expensive");
        return false;
        //at this point in the view I would print the reasons why it could have stopped (coins, wrong parameters, etc..)
    }

    /**
     * Smaller version of the playExpertCard()
     * Removes the coins from the Castle and calls the applyEffect() of the right character.
     *
     * @param idChar char number to call the method on the right object
     * @return true if the character worked
     */
    public boolean playExpertCard(int idChar, List<Color> studentsList) throws StudentException {
        return playExpertCard(idChar, (ExpertIsland) this.getIslandList().get(0), studentsList);
    }

    /**
     * Only the fourth character needs no other input parameters
     *
     * @param idChar
     * @return true if the expert card was correctly played
     * @throws NoSuchStudentException
     * @throws TooManyStudentsException
     */
    public boolean playExpertCard(int idChar) throws StudentException {
        if (idChar == 4 && expertCharactersCards.get(4) != null)
            return playExpertCard(idChar,
                    (ExpertIsland) this.getIslandList().get(0),
                    List.of());
        return false;
    }

    /**Adds to the available Characters also the Character #idChar.
     * @param idChar
     */
    public Generic extract4CharacterTesting(int idChar){
        Generic ec = tavern.extract4testing(idChar);
        expertCharactersCards.set(idChar, ec);
        return ec;
    }

    /**
     * As super but checks if the island is blocked.
     */
    @Override
    public void moveMotherNature(int move) {
        if (motherNaturePosition + move / islandList.size() >= 1) motherNaturePosition += move - islandList.size();
        else motherNaturePosition += move;
        ExpertIsland currIsland = (ExpertIsland) islandList.get(motherNaturePosition);
        if (!currIsland.isBLocked())
            conquerIsland(currIsland);
        else{
            currIsland.unlockIsland();
            //TODO: char.add(blockTile);
        }
    }

    /**
     * For testing: returns the list of available characters
     */
    public List<Generic> getAvailableCharacterCards() {
        return expertCharactersCards;
    }

    public Bag getBag() {
        return bag;
    }
    
}
