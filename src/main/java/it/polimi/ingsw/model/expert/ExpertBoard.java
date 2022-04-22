package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.Characters.CharactersList;
import it.polimi.ingsw.model.expert.Characters.Generic;
import it.polimi.ingsw.model.expert.Characters.Parameters;
import it.polimi.ingsw.model.expert.Characters.Tavern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertBoard extends Board {
    private Tavern tavern;
    private List<Generic> expertCharactersCards;
    private CharactersList activeChar = null;

    public ExpertBoard(String playerID1, String playerID2, Turn t) {
        super(t);
        castleMap.put(playerID1, new ExpertCastle(Team.WHITE, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID2, new ExpertCastle(Team.BLACK, nPlayer, bag.multipleExtract(7)));
        construct();
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3, Turn t) {
        super(t);
        castleMap.put(playerID1, new ExpertCastle(Team.WHITE, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID2, new ExpertCastle(Team.BLACK, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID3, new ExpertCastle(Team.GREY, nPlayer, bag.multipleExtract(9)));
        construct();
    }

    public ExpertBoard(String playerID1, String playerID2, String playerID3, String playerID4, Turn t) {
        super(t);
        castleMap.put(playerID1, new ExpertCastle(Team.WHITE, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID2, new ExpertCastle(Team.BLACK, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID3, new ExpertCastle(Team.WHITE, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID4, new ExpertCastle(Team.BLACK, nPlayer, bag.multipleExtract(7)));
        construct();
    }

    /**
     * Cleans the contructors' implementation
     */
    private void construct() {
        setupIslands();
        drawExpertCharacters();
    }

    /**
     * Sets up for the ExpertIslands
     */
    private void setupIslands() {
        List<Color> s = bag.extractForIslandSetup();
        for (int i = 0; i < 12; i++) {
            if (i % 6 != 0) {
                islandList.add(i, new ExpertIsland(s.get(0)));
                s.remove(0);
            } else
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
     * Needed to reset active char at the end of a turn;
     *
     * @param PlayerID the id of the player that ask for this move
     * @param cloudID  the cloud that is chosen
     * @return true -> the current player ends his turn
     * @throws TooManyStudentsException error in adding a student to the waiting room (shouldn't occur)
     */
    @Override
    public boolean chooseCloud(String PlayerID, int cloudID) throws NotYourTurnException, TooManyStudentsException {
        if (!super.chooseCloud(PlayerID, cloudID)) return false;
        activeChar = null;
        return true;
    }

    /**
     * Tries to pay for the card and then calls applyEffect with the right parameters
     *
     * @param idChar character id correspondingo to CharacterList's position
     * @throws IllegalStateException    another Character was already activated this turn
     * @throws IllegalArgumentException the selected character was not extracted during this turn
     * @throws Exception                if the player doesn't have the needed coins to pay
     */

    public void playExpertCard(int idChar, ExpertIsland island, List<Color> studentsList) throws Exception {
        if (activeChar != null) {
            throw new IllegalStateException("Not possible to play " + CharactersList.values()[idChar - 1] + " card. During this turn "
                    + activeChar.name() + " is already active.");
        }
        Generic ec = expertCharactersCards.get(idChar);
        if (ec == null) {
            throw new IllegalArgumentException(CharactersList.values()[idChar - 1] + " was not an extracted character.\n" +
                    "Available characters are: " + expertCharactersCards);
        }
        String currentPlayer = this.getCurrentPlayer();
        int cost = ec.getCost();
        ExpertCastle currPlayerCastle = (ExpertCastle) castleMap.get(currentPlayer);
        if (!currPlayerCastle.payCharacter(cost)) {
            throw new Exception("not enough coins available to pay for the effect.\n" +
                    "needed coins: " + cost + ", available coins: " + currPlayerCastle.getCoins());
        }
        //in/out parameters for the applyEffect method
        //Parameter for setup (will clean code up)

        if (island == null) island = (ExpertIsland) getIslandList().get(0);
        Map<Parameters, Object> parametersMap
                = new HashMap<>(Map.of(
                Parameters.PLAYERID, currentPlayer,
                Parameters.ISLAND, island,
                Parameters.CASTLEMAP, castleMap,
                Parameters.PROFESSORSMAP, professorsMap,
                Parameters.STUDENTLIST, studentsList));
        try {
            ec.applyEffect(parametersMap);
        } catch (StudentException e) {
            currPlayerCastle.unpayCharacter(cost);
            System.out.println(e.getMessage());
            throw new StudentException(e);
        } catch (Exception e) {
            currPlayerCastle.unpayCharacter(cost);
            System.out.println(e.getMessage());
            //TODO handle the exception
        }
        activeChar = ec.getCharacterName();
        // in this version of the program the method is void and is correctly executed if not specified else wise.
    }

    /**
     * @param idChar number of the character as defined in the enum
     */
    public void playExpertCard(int idChar) {
        if (activeChar != null) {
            throw new IllegalStateException("Not possible to play " + CharactersList.values()[idChar - 1] + " card. During this turn "
                    + activeChar.name() + " is already active.");
        }
        Generic ec = expertCharactersCards.get(idChar);
        if (ec == null) {
            throw new IllegalArgumentException(CharactersList.values()[idChar - 1] + " was not an extracted character.\n" +
                    "Available characters are: " + expertCharactersCards.toArray().toString());
        }
        String currentPlayer = this.getCurrentPlayer();
        int cost = ec.getCost();
        ExpertCastle currPlayerCastle = (ExpertCastle) castleMap.get(currentPlayer);
        if (!currPlayerCastle.payCharacter(cost)) {
            throw new Exception("not enough coins available to pay for the effect.\n" +
                    "needed coins: " + cost + ", available coins: " + currPlayerCastle.getCoins());
        }
        activeChar = CharactersList.MAILMAN;
    }

    /**
     * Adds to the available Characters also the Character #idChar.
     *
     * @param idChar number of the character as defined in the enum
     */
    public Generic extract4CharacterTesting(int idChar) {
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
        else {
            currIsland.unlockIsland();
            //TODO: char.add(blockTile);
        }
    }

    /**
     * Smaller version of the playExpertCard()
     * Removes the coins from the Castle and calls the applyEffect() of the right character.
     *
     * @param idChar char number to call the method on the right object
     */
    public void playExpertCard(int idChar, List<Color> studentsList) throws Exception {
        playExpertCard(idChar, (ExpertIsland) this.getIslandList().get(0), studentsList);
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

    public CharactersList getActiveChar() {
        return activeChar;
    }
}
