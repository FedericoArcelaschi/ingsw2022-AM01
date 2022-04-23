package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.CoinException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.StudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.Characters.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertBoard extends Board {
    private Tavern tavern;
    private List<Generic> expertCharactersCards;
    private Generic activeChar = null;

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
     * Needs to reset active char at the end of a turn;
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
     * @param idChar      character id correspondingo to CharacterList's position
     * @param islandIndex - index as seen in the view (user input)
     * @throws IllegalStateException    another Character was already activated this turn
     * @throws IllegalArgumentException the selected character was not extracted during this turn
     * @throws CoinException if the player doesn't have the needed coins to pay
     */
    public void playExpertCard(int idChar, int islandIndex, List<Color> studentsList) throws StudentException {
        Generic ec = checkLegalExpertCard(idChar);
        ExpertCastle currPlayerCastle = (ExpertCastle) castleMap.get(getCurrentPlayer());
        int cost = ec.getCost();
        if (!currPlayerCastle.payCharacter(cost))
            throw new CoinException(cost, currPlayerCastle.getCoins());

        //in/out parameters for the applyEffect method
        //Parameter for setup (will clean code up)

        Map<Parameters, Object> parametersMap = initializeExpertCharacterParameters(idChar, islandIndex, studentsList);

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
            return;
        }

        outExpertCharacterParameters(parametersMap);
        if (isWinningPosition() != null) ;
        //TODO: end game --> valore di uscita

        activeChar = ec;
    }

    private void outExpertCharacterParameters(Map<Parameters, Object> parametersMap) {
        possibleMovingSteps = (Integer) parametersMap.get(Parameters.STEPS);
    }

    private Map<Parameters, Object> initializeExpertCharacterParameters(int idChar, int islandIndex, List<Color> studentsList) {
        Map<Parameters, Object> parametersMap = new HashMap<>(
                Map.of(
                        Parameters.PLAYERID, getCurrentPlayer(),
                        Parameters.ISLAND, getIslandList().get(islandIndex),
                        Parameters.CASTLEMAP, castleMap,
                        Parameters.PROFESSORSMAP, professorsMap,
                        Parameters.STUDENTLIST, studentsList,
                        Parameters.STEPS, possibleMovingSteps));

        //TODO: possibly pass the right parameters
        return parametersMap;
    }

    private Generic checkLegalExpertCard(int idChar) {
        String charName = CharactersList.getChar(idChar).name();
        if (activeChar != null) { //there is an active character;
            if (activeChar.getCharacterType().name().equals(charName))
                throw new IllegalStateException(charName + " is already active in this turn.");
            throw new IllegalStateException("Not possible to play " + CharactersList.getChar(idChar) + " card. During this turn "
                    + activeChar.getCharacterType().name() + " is already active.");
        }
        Generic ec = expertCharactersCards.get(idChar);
        if (ec == null) //there is no active character but the card isn't available
            throw new IllegalArgumentException(CharactersList.getChar(idChar) + " was not an extracted character.\n" +
                    "Available characters are: " + expertCharactersCards);
        return ec;
    }

    /**
     * Method only for Mailman call
     *
     * @param idChar number of the character as defined in the Enum
     */
    public void playExpertCard(int idChar) throws CoinException, StudentException {
        playExpertCard(idChar, 0, Arrays.asList());
    }

    /**
     * Smaller version of the playExpertCard()
     * Removes the coins from the Castle and calls the applyEffect() of the right character.
     *
     * @param idChar char number to call the method on the right object
     */
    public void playExpertCard(int idChar, List<Color> studentsList) throws CoinException, StudentException {
        playExpertCard(idChar, 0, studentsList);
    }

    public void playExpertCard(int idChar, int islandIndex, Color student) throws StudentException {
        playExpertCard(idChar, 0, List.of(student));
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
     * @param steps
     */
    @Override
    public void moveMotherNature (int steps) {
        if (steps > possibleMovingSteps)
            throw new IllegalArgumentException("too many steps");
        if (motherNaturePosition + steps / islandList.size() >= 1) motherNaturePosition += steps - islandList.size();
        else motherNaturePosition += steps;
        ExpertIsland currIsland = (ExpertIsland) islandList.get(motherNaturePosition);
        if (!currIsland.isBLocked())
            conquerIsland(currIsland);
        else {
            currIsland.unlockIsland();
            ((Block) expertCharactersCards.get(5)).addBlockTile();
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

    public CharactersList getActiveChar() {
        return activeChar.getCharacterType();
    }

}
