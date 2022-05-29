package it.polimi.ingsw.model.expertLogic;

import it.polimi.ingsw.model.baseLogic.*;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.baseLogic.interfaces.StudentPlaces;
import it.polimi.ingsw.model.expertLogic.character.charTypes.StandardCharacter;
import it.polimi.ingsw.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.model.expertLogic.character.charTypes.*;
import it.polimi.ingsw.model.expertLogic.character.costants.CharacterParametersType;
import it.polimi.ingsw.model.expertLogic.character.costants.CharacterUtility;
import it.polimi.ingsw.model.expertLogic.influence.ExpertInfluence;
import it.polimi.ingsw.model.expertLogic.influence.professor.ExpertProfessors;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ExpertBoard extends Board {
    private Tavern tavern;
    private List<StandardCharacter> expertCharactersCards;
    private int playedExpertChar = -1;

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
        for (int i = 0; i < 12; i++){
            Island oldIsland = islandList.remove(i);
            islandList.add(i, new ExpertIsland(oldIsland));
        }
        influence = new ExpertInfluence(new ExpertProfessors(castleMap));
        drawExpertCharacters();
    }

    /**
     * Initializes <code>expertCharactersCards</code>. It's a factory method
     */
    private void drawExpertCharacters() {
        tavern = new Tavern(bag);
        expertCharactersCards = tavern.extract();
    }

    /**
     * Pays for the card and then calls applyEffect with the right parameters
     * @param idChar      character id corresponding to CharacterList's position
     * @param islandIndex - index choose for the effect application (as seen in the view)
     * @throws IllegalStateException another Character was already activated this turn
     * @throws IllegalArgumentException the selected character was not extracted during this turn
     * @throws CoinException if the player doesn't have the needed coins to pay
     */
    public void playExpertCard (int idChar, Integer islandIndex, List<StudentColor> studentsList) throws StudentException, CoinException {
        StandardCharacter ec = checkLegalExpertCard(idChar);
        ParametersForCharacter par = getParameters(ec.getCharacterType(), studentsList, islandIndex);
        int actualCost = ec.getCost();
        try{
            ec.applyEffect(par);
        } catch (StudentException e) {
            System.out.println(e.getMessage());
            throw new StudentException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        playedExpertChar = idChar;
        ((ExpertCastle) getCastle(getCurrentPlayer())).payCharacter(actualCost);
    }

    private @NotNull StandardCharacter checkLegalExpertCard(int idChar) throws CoinException {
        String charName = CharacterUtility.getChar(idChar).name();
        if (playedExpertChar != -1) //another character is played this turn.
            if(idChar == playedExpertChar)
                throw new IllegalStateException(charName + " is already active in this turn.");
            else
                throw new IllegalStateException(
                        "Not possible to play " + charName + " card. During this turn " +
                                CharacterUtility.getChar(playedExpertChar).name() + " is already active.");

        StandardCharacter ec = expertCharactersCards.get(idChar);

        if (ec == null) { //case where there is no active character but the card isn't available
            List<String> charactersName = new ArrayList<>();
            for (StandardCharacter m : expertCharactersCards) {
                if(m != null)
                    charactersName.add(m.getName());
            }
            throw new IllegalArgumentException(CharacterUtility.getChar(idChar) + " was not an extracted character.\n" +
                    "Available characters are: " + charactersName);
        }
        int availableCoins = ((ExpertCastle) castleMap
                .get(getCurrentPlayer()))
                .getCoins();
        if(availableCoins < ec.getCost())
            throw new CoinException(ec.getCost(), availableCoins); //payment check
        return ec;
    }

    private @NotNull ParametersForCharacter getParameters(CharacterParametersType characterParametersType, List<StudentColor> studentsList, Integer islandIndex) {
        return switch (characterParametersType) {
            case STUDENT ->     studentParameters(studentsList, islandIndex);
            case ISLAND ->      islandParameters(islandIndex);
            case STANDARD ->    standardParameters();
            case INFLUENCE ->   influenceParameters(studentsList);
        };
    }

    private @NotNull ParametersForCharacter influenceParameters(List<StudentColor> studentsList) {
        ParametersForCharacter par = new ParametersForCharacter();
        par.setInfluence((ExpertInfluence) influence);
        par.setRequestedStudent(studentsList.get(0));
        par.setCurrentTeam(getCurrentTeam());
        return par;
    }

    private @NotNull ParametersForCharacter standardParameters() {
        ParametersForCharacter par = new ParametersForCharacter();
        par.setSteps(possibleMovingSteps);
        return par;
    }


    private @NotNull ParametersForCharacter studentParameters(List<StudentColor> studentsList, Integer islandIndex) {
        ParametersForCharacter par = new ParametersForCharacter();
        par.setRequestedStudentList(studentsList);
        par.setIslandIndex(islandIndex);
        par.setNumberOfPlayers(castleMap.size());
        List<StudentPlaces> places = new ArrayList<>();
        places.add(castleMap.get(getCurrentPlayer())); //index 0.
        places.addAll(castleMap
                .keySet()
                .stream()
                .filter(key -> !key.equals(getCurrentPlayer()))
                .map(castleMap::get)
                .toList()); //All castles but currentPlayer's.
        places.addAll(islandList); //index numberOfPlayer - 1
        par.setPlacesList(places);
        return par;
    }

    private @NotNull ParametersForCharacter islandParameters(Integer islandIndex) {
        ParametersForCharacter par = new ParametersForCharacter();
        par.setInfluence((ExpertInfluence) influence);
        par.setIslandList(islandList);
        par.setIslandIndex(islandIndex);
        return par;
    }

    /**
     * Needed to Decorate the new <code>Archipelago</code> "island" into <code>ExpertIslands</code>.
     * @param islandsToJoin indexes of the islands to join after an island is conquered.
     */
    protected void joinIslands(@NotNull List<Integer> islandsToJoin) {
        int firstIslandIndex = islandsToJoin.get(0);
        int secondIslandIndex = islandsToJoin.get(1);
        Island newIsland;
        if ( islandsToJoin.size() == 2 )
            newIsland = new ExpertIsland(new Archipelago(islandList.get(firstIslandIndex), islandList.get(secondIslandIndex)));
        else if(islandsToJoin.size()==3) {
            int thirdIslandIndex = islandsToJoin.get(2);
            newIsland
                    = new ExpertIsland(new Archipelago(  islandList.get(firstIslandIndex),
                    islandList.get(islandsToJoin.get(1)),
                    islandList.get(islandsToJoin.get(2))));
        } else
            throw new IllegalArgumentException("wrong number of islands in the given list: " + islandList);
        for (Integer index : islandsToJoin)
            this.islandList.remove(index);
        this.islandList.add(firstIslandIndex, newIsland);
        motherNaturePosition = firstIslandIndex;
    }

    //
    private void turnReset() {
        cloudList.forEach(Cloud::refill);
        playedExpertChar = -1;
        ((ExpertInfluence)influence).reset();
    }

//Getter

    public List<StandardCharacter> getAvailableCharacterCards() {
        return expertCharactersCards;
    }

    public Team getCurrentTeam(){
        return castleMap.get(getCurrentPlayer()).getTeam();
    }

//FOR TESTING
    /**Adds to the available characters also the Character #idChar.
     * @param idChar number of the character as defined in the enum
     */
    public void extract4CharacterTesting(int idChar) {
        StandardCharacter ec = tavern.extract4testing(idChar);
        expertCharactersCards.set(idChar, ec);
    }
    
}