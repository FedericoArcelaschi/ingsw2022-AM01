package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.expert.character.type.MasterCharacter;
import it.polimi.ingsw.model.expert.character.type.Tavern;
import it.polimi.ingsw.model.expert.character.costants.CharacterUtility;
import it.polimi.ingsw.model.expert.boardInterfaces.StudentPlaces;
import it.polimi.ingsw.model.expert.influence.InfluenceExpert;
import it.polimi.ingsw.model.influence.Professors;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ExpertBoard extends Board {

    private Tavern tavern;
    private List<MasterCharacter> expertCharactersCards;
    private int playedExpertCard = -1;
    private InfluenceExpert influence = new InfluenceExpert(new Professors(castleMap));

    public ExpertBoard(String playerID1, String playerID2, Turn t, long seed) {
        super(t, seed, 2);
        castleMap.put(playerID1, new ExpertCastle(Team.WHITE, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID2, new ExpertCastle(Team.BLACK, nPlayer, bag.multipleExtract(7)));
        construct();
    }
    public ExpertBoard(String playerID1, String playerID2, String playerID3, Turn t, long seed) {
        super(t, seed, 3);
        castleMap.put(playerID1, new ExpertCastle(Team.WHITE, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID2, new ExpertCastle(Team.BLACK, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID3, new ExpertCastle(Team.GREY, nPlayer, bag.multipleExtract(9)));
        construct();
    }
    public ExpertBoard(String playerID1, String playerID2, String playerID3, String playerID4, Turn t, long seed) {
        super(t, seed, 4);
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
        for (int i = 0; i < 12; i++)
            islandList.add(i, new ExpertIsland(islandList.get(i)));
        drawExpertCharacters();
    }

    /**
     * Initializes <code>expertCharactersCards</code>. It's a factory method
     */
    private void drawExpertCharacters() {
        tavern = new Tavern(bag);
        expertCharactersCards = tavern.extract();
    }

//Steps phase

    /**
     * Pays for the card and then calls applyEffect with the right parameters
     * @param idChar      character id correspondingo to CharacterList's position
     * @param islandIndex - index choose for the effect application (as seen in the view)
     * @throws IllegalStateException another Character was already activated this turn
     * @throws IllegalArgumentException the selected character was not extracted during this turn
     * @throws CoinException if the player doesn't have the needed coins to pay
     */
    public void playExpertCard(int idChar, int islandIndex, List<Color> studentsList) throws StudentException, CoinException {
        MasterCharacter ec = checkLegalExpertCard(idChar);

        ExpertCastle currPlayerCastle = (ExpertCastle) castleMap.get(getCurrentPlayer());
        /**
         * 0 -> currentPlayerCastle
         * 1 -> island before the one choose by the player
         * 2 -> island choose by the player (when available)
         * 3 -> island after the one choose by the player
         * 4,5,[6,7] all castles
         * null -> List not needed for the selected character.
         */
        List<StudentPlaces> studentPlaces = new ArrayList<>(Arrays.asList(currPlayerCastle));//index 0.
        if(CharacterUtility.getCharacterThatMoveStudents.contains(idChar)) {
            studentPlaces.addAll(getNeighbouringIsland(islandIndex));//index 1 to 3.
        }
        if(CharacterUtility.getCharactersThatNeedAllCastles.contains(idChar))
            studentPlaces.addAll(castleMap.values().stream().toList());//index 1 to size().

        int cost = ec.getCost();
        if (currPlayerCastle.getCoins() < cost)
            throw new CoinException(cost, currPlayerCastle.getCoins()); //player doesn't the the money!

        try {

            ec.applyEffect(studentsList, studentPlaces, influence, possibleMovingSteps);
            //todo: all effect should either be correct of have NO action
            currPlayerCastle.payCharacter(cost);
            playedExpertCard = idChar;

        } catch (StudentException e) {
            System.out.println(e.getMessage());
            throw new StudentException(e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Method for Farmer, Mailman, Centaur, Knight call
     * number of the character as defined in the Enum
     */
    public void playExpertCard(@NotNull int idChar) throws CoinException, StudentException {
        playExpertCard(idChar, 0, Arrays.asList());
    }

    /**
     * Method for Jester, Cook, Storyteller, Queen, Taxman
     * @param idChar char number to call the method on the right object
     */
    public void playExpertCard(@NotNull int idChar, @NotNull List<Color> studentsList) throws CoinException, StudentException {
        playExpertCard(idChar, 0, studentsList);
    }

    private MasterCharacter checkLegalExpertCard(int idChar) {
        String charName = CharacterUtility.getInstance(idChar).name();
        if (playedExpertCard != -1)
            if(idChar == playedExpertCard)
                throw new IllegalStateException(charName + " is already active in this turn.");
            else
                throw new IllegalStateException(
                    "Not possible to play " + charName + " card. During this turn " +
                    CharacterUtility.getInstance(playedExpertCard).name() + " is already active.");
        MasterCharacter ec = expertCharactersCards.get(idChar);
        if (ec == null) { //case where there is no active character but the card isn't available
            List<String> charactersName = new ArrayList<>();
            for (MasterCharacter m:expertCharactersCards) {
                if(m != null)
                    charactersName.add(m.getCharacterType().name());
            }
            throw new IllegalArgumentException(CharacterUtility.getInstance(idChar) + " was not an extracted character.\n" +
                    "Available characters are: " + charactersName);
        }return ec;
    }

    @Override
    protected void joinIslands(@NotNull List<Island> islandList) {
        int firstIslandIndex
                = this.islandList
                .indexOf(islandList.get(0));
        if(firstIslandIndex == -1)
            throw new IllegalArgumentException("island: " + islandList.get(0).toString() + "not found!");
        Island newIsland = null;
        if(islandList.size()==2){
            if(this.islandList.removeAll(Arrays.asList(islandList.get(0), islandList.get(1))))
                newIsland = new ExpertIsland(new Archipelago(islandList.get(0),islandList.get(1)));
        }
        else if(islandList.size()==3){
            if(this.islandList.removeAll(Arrays.asList(islandList.get(0), islandList.get(1), islandList.get(2))))
                newIsland = new ExpertIsland( new Archipelago(islandList.get(0),islandList.get(1),islandList.get(2)));
        }
        else
            throw new IllegalArgumentException("wrong number of islands in the given list: " + islandList);
        this.islandList.add(firstIslandIndex, newIsland);

    }

    private void turnReset() {
        cloudList.stream().forEach((boh) -> refillClouds());
        playedExpertCard = -1;
    }

//Getter

    public List<MasterCharacter> getAvailableCharacterCards() {
        return expertCharactersCards;
    }

    public Bag getBag() {
        return bag;
    }

//FOR TESTING
    /**Adds to the available characters also the Character #idChar.
     * @param idChar number of the character as defined in the enum
     */
    protected void extract4CharacterTesting(int idChar) {
        MasterCharacter ec = tavern.extract4testing(idChar);
        expertCharactersCards.set(idChar, ec);
    }
}