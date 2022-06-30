package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.ModelDataBuilder;
import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.baseLogic.interfaces.StudentPlaces;
import it.polimi.ingsw.server.model.exceptions.CoinException;
import it.polimi.ingsw.server.model.exceptions.WrongGameModeException;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.expertLogic.character.applyEffect.ParametersForCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import it.polimi.ingsw.server.model.expertLogic.influence.ExpertInfluence;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ExpertProfessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ExpertBoard extends Board {

    protected Map<CharacterUtility, StandardCharacter> expertCharactersCards;
    protected CharacterUtility playedExpertChar = null;

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
    protected void construct() {
        for (int i = 0; i < 12; i++) {
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
        Tavern tavern = new Tavern(bag);
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
    @Override
    public void playExpertCard (int idChar, Integer islandIndex, List<StudentColor> studentsList) throws StudentException, CoinException, PhaseNotRightException {
        if(turn.getCurrentPhase() == TurnPhase.PLANNING)
            throw new PhaseNotRightException("You can't use this command in this stage of the game.");
        StandardCharacter character = checkLegalExpertCard(idChar);
        ParametersForCharacter par = getParameters(character, studentsList, islandIndex);
        int actualCost = character.getCost();
        try{
            character.applyEffect(par);
        } catch (StudentException | IllegalAccessException e) {
            throw new StudentException(e);
        }
        playedExpertChar = CharacterUtility.getChar(idChar);
        ((ExpertCastle) getCastle(turn.getCurrentPlayer())).payCharacter(actualCost);
    }

    private @NotNull StandardCharacter checkLegalExpertCard(int idChar) throws CoinException {
        String charName = CharacterUtility.getChar(idChar).name();
        if (playedExpertChar != null) //another character is played this turn.
            if(CharacterUtility.getChar(idChar) == playedExpertChar)
                throw new IllegalStateException(charName + " is already active during this turn.");
            else
                throw new IllegalStateException(
                        "Not possible to play " + charName + " card. During this turn " +
                                playedExpertChar.name() + " is already active.");

        CharacterUtility characterUtility = CharacterUtility.getChar(idChar);

        if (!expertCharactersCards.containsKey(characterUtility)) {
            String charactersName = expertCharactersCards.values().stream().map(StandardCharacter::getName).toString().replace("[", "").replace("]", "");
            throw new IllegalArgumentException(characterUtility.name() + " was not an extracted character.\n" +
                    "Available characters are: " + charactersName);
        }
        int availableCoins = 0;
        try {
            availableCoins = castleMap
                    .get(turn.getCurrentPlayer())
                    .getCoins();
        } catch (WrongGameModeException ignored) {}
        StandardCharacter character = expertCharactersCards.get(characterUtility);
        if(availableCoins < character.getCost())
            throw new CoinException(character.getCost(), availableCoins);
        return character;
    }

    /**
     * Factory to make tailored ParameterForCharacter objects for the character apply effects.
     * Uses the client inputs and attributes from the board
     * @param studentsList input from client
     * @param islandIndex input from client
     */
    private @NotNull ParametersForCharacter getParameters(StandardCharacter character, List<StudentColor> studentsList, @Nullable Integer islandIndex) {
        return switch (character.getCharacterType()) {
            case STANDARD       -> standardParameters();
            case STUDENT        -> studentParameters(studentsList, islandIndex);
            case ISLAND         -> islandParameters(islandIndex);
            case INFLUENCE      -> influenceParameters(studentsList);
        };
    }

    private @NotNull ParametersForCharacter influenceParameters(List<StudentColor> studentsList) {
        ParametersForCharacter par = new ParametersForCharacter();
        par.setInfluence((ExpertInfluence) influence);
        if(studentsList != null)
            if(!studentsList.isEmpty())
                par.setRequestedStudent(studentsList.get(0));
        par.setCurrentTeam(getCurrentTeam());
        return par;
    }

    private @NotNull ParametersForCharacter standardParameters() {
        ParametersForCharacter par = new ParametersForCharacter();
        possibleMovingSteps.add(turn.getPossibleMovingSteps());
        par.setSteps(possibleMovingSteps);
        return par;
    }


    private @NotNull ParametersForCharacter studentParameters(List<StudentColor> studentsList, Integer islandIndex) {
        ParametersForCharacter par = new ParametersForCharacter();
        par.setRequestedStudentList(studentsList);
        par.setIslandIndex(islandIndex);
        par.setNumberOfPlayers(castleMap.size());
        List<StudentPlaces> places = new ArrayList<>();
        places.add(castleMap.get(turn.getCurrentPlayer())); //index 0.
        places.addAll(castleMap
                .keySet()
                .stream()
                .filter(key -> !key.equals(turn.getCurrentPlayer()))
                .map(castleMap::get)
                .toList()); //All castles but currentPlayer's.
        places.addAll(islandList); //index numberOfPlayer - 1
        par.setPlacesList(places);
        return par;
    }

    private @NotNull ParametersForCharacter islandParameters(@NotNull Integer islandIndex) {
        ParametersForCharacter par = new ParametersForCharacter();
        assert influence != null;
        par.setInfluence((ExpertInfluence) influence);
        assert islandList != null && !islandList.isEmpty();
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
        List<Island> islandList = new ArrayList<>();
        islandsToJoin.forEach(index -> islandList.add(this.islandList.get(index)));
        this.islandList
                .add(firstIslandIndex,
                new ExpertIsland(
                        new Archipelago(islandList)));
        motherNaturePosition = firstIslandIndex;
    }

    @Override
    public void endOfRound() {
        super.endOfRound();
        playedExpertChar = null;
        influence.reset();
    }

    @Override
    public Map<CharacterUtility, StandardCharacter> getAvailableCharacters() {
        return expertCharactersCards;
    }

    public Team getCurrentTeam() {
        return castleMap.get(turn.getCurrentPlayer()).getTeam();
    }

    //FOR VIEW:
    @Override
    public BoardData getData(String playerID) {
        return ModelDataBuilder.newExpertBoardData(this, playerID);
    }

    public CharacterUtility getPlayedExpertChar() {
        return playedExpertChar;
    }
}