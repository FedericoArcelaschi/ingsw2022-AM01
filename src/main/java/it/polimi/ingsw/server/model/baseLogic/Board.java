package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.communication.modelData.BoardData;
import it.polimi.ingsw.communication.modelData.ModelDataBuilder;
import it.polimi.ingsw.server.model.baseLogic.influence.Influence;
import it.polimi.ingsw.server.model.baseLogic.influence.Professors;
import it.polimi.ingsw.server.model.baseLogic.interfaces.GreaterTeam;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.expertLogic.character.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("Redundant")
public class Board {

    protected static final int numOfStudentsPerColor = 24;
    protected int motherNaturePosition = 0;

    protected final int nPlayer;
    protected final Bag bag;
    protected final List<Cloud> cloudList = new ArrayList<>();
    protected final List<Island> islandList = new ArrayList<>();
    protected final Map<String, Castle> castleMap = new HashMap<>();

    protected Influence influence = new Influence(new Professors(castleMap));

    protected final Turn turn;
    //the idea is to save it or send it to the player at the end of the game
    private final long seed;
    protected final PossibleMovingSteps possibleMovingSteps = new PossibleMovingSteps(); //calculated form the card: must be stored in memory til the player action turn
    //constants
    private final int INITIAL_NUMBER_OF_ISLANDS = 12;
    private final int MINIMUM_NUMBER_OF_ISLANDS = 3;
    private final int TOWERS_TO_PLACE_TO_WIN_2_4_PLAYERS = 8;
    private final int TOWERS_TO_PLACE_TO_WIN_3_PLAYERS = 8;
    private final int CLOUD_SIZE_2_4_PLAYERS = 3;
    private final int CLOUD_SIZE_3_PLAYERS = 4;
    private boolean endGame = false;

    public Board(String playerID1, String playerID2, Turn turn, long seed) {
        nPlayer = 2;
        this.seed = seed;
        bag = new Bag(numOfStudentsPerColor, seed);
        this.turn = turn;
        castleMap.put(playerID1, new Castle(Team.WHITE, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID2, new Castle(Team.BLACK, nPlayer, bag.extractForCastleSetup(nPlayer)));
        construct();
    }

    public Board(String playerID1, String playerID2, String playerID3, Turn turn, long seed) {
        nPlayer = 3;
        this.seed = seed;
        bag = new Bag(numOfStudentsPerColor, seed);
        this.turn = turn;
        castleMap.put(playerID1, new Castle(Team.WHITE, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID2, new Castle(Team.BLACK, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID3, new Castle(Team.GREY, nPlayer, bag.extractForCastleSetup(nPlayer)));
        construct();
    }

    public Board(String playerID1, String playerID2, String playerID3, String playerID4, Turn turn, long seed) {
        nPlayer = 4;
        this.seed = seed;
        bag = new Bag(numOfStudentsPerColor, seed);
        this.turn = turn;
        castleMap.put(playerID1, new Castle(Team.WHITE, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID2, new Castle(Team.BLACK, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID3, new Castle(Team.WHITE, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID4, new Castle(Team.BLACK, nPlayer, bag.extractForCastleSetup(nPlayer)));
        construct();
    }

    /**
     * Constructor for ExpertBoard: doesn't generate the castles.
     */
    protected Board(Turn turn, long seed, int nPlayer) {
        this.turn = turn;
        this.seed = seed;
        this.bag = new Bag(numOfStudentsPerColor, seed);
        this.nPlayer = nPlayer;
        construct();
    }

    /**
     * Cleans the constructor implementation
     */
    private void construct() {
        setupClouds();
        setupIslands();
    }

    protected void setupClouds(){
        int cloudSize = (nPlayer == 3) ? CLOUD_SIZE_3_PLAYERS : CLOUD_SIZE_2_4_PLAYERS;
        for (int i = 0; i < nPlayer; i++) cloudList.add(new Cloud(bag, cloudSize));
    }

    private void setupIslands() {
        List<StudentColor> s = bag.extractForIslandSetup();
        for (int i = 0, c = 0; i < INITIAL_NUMBER_OF_ISLANDS; i++) {
            if (i % (INITIAL_NUMBER_OF_ISLANDS / 2) == 0) {
                islandList.add(new Island());
            } else {
                islandList.add(new Island(s.get(c)));
                c++;
            }
        }
    }

//methods for the PLANNING PHASE
    /**
     * @param playerID the id of the player that asks for this move.
     * @param cardID   the number of the card the user wants to play.
     * @throws IllegalArgumentException the card is not available
     *                                  the card is already played and the player has another card he can play
     */
    public void playCard(String playerID, int cardID) throws PhaseNotRightException {
        if (turn.getCurrentPhase() != TurnPhase.PLANNING)
            throw new PhaseNotRightException("You can't play a card in this phase of the game. " +
                    "Current phase is " + turn.getCurrentPhase().toString().toLowerCase());
        Castle castle = castleMap.get(playerID);
        if(!turn.isAlreadyPlayed(cardID) ||
                castle.getDeck()
                        .stream()
                        .filter(Card::isAvailable)
                        .allMatch(card-> turn.isAlreadyPlayed(card.priority())))
            if(castle.isCardAvailable(cardID)) {
                Card card = castle.playCard(cardID);
                turn.addCard(playerID, card);
                possibleMovingSteps.zero();
                return;
            }
        throw new IllegalArgumentException("Card cannot be played. " +
                (turn.isAlreadyPlayed(cardID) && !castle.getDeck().stream().allMatch(card-> turn.isAlreadyPlayed(card.priority()))
                        ? "Card is already played and you have another card to play in your castle. " : "") +
                (!castle.isCardAvailable(cardID) ? "You don't have this card in the castle." : "" ));
    }

//methods for the Action phase:
    /**
     * Moves students from the waiting room to the dining room.
     * @param playerID the id of the player that ask for this move
     * @param students a list of students you want to move
     * @throws NoSuchStudentException if the student is not in the Waiting Room of the current player
     * @throws TooManyStudentsException if the castle dining room already contains 9 students
     */
    public void moveStudentsToDiningRoom(String playerID, List<StudentColor> students)
            throws NoSuchStudentException, TooManyStudentsException, PhaseNotRightException {
        if(turn.getCurrentPhase() != TurnPhase.STUDENTS)
            throw new PhaseNotRightException("You can't move students in this phase of the game. " +
                    "Current phase is " + turn.getCurrentPhase().toString().toLowerCase());
        castleMap.get(playerID).removeStudentsFromWaitingRoom(students);
        castleMap.get(playerID).addStudentsInDiningRoom(students);
        // FIXME
        influence.updateProfessors();
    }

    /**
     * Moves the students in the list <code>students</code> from <code>Player</code>'s waiting room
     * to the island n°<code>islandNumber</code>.
     * @param playerID     the id of the player that ask for this move
     * @param islandNumber the number of the island where you want to move the students
     * @param students     a list of students you want to move
     */
    public void moveStudentToIsland(String playerID, int islandNumber, List<StudentColor> students)
            throws NoSuchStudentException, PhaseNotRightException {
        if (turn.getCurrentPhase() != TurnPhase.STUDENTS)
            throw new PhaseNotRightException("You can't move students in this phase of the game. " +
                    "Current phase is " + turn.getCurrentPhase().toString().toLowerCase());
        castleMap.get(playerID).removeStudentsFromWaitingRoom(students);
        students.forEach(islandList.get(islandNumber)::addStudent);
    }

    /**
     * MovesMotherNature and computes the influence and sets a new owner
     * on the island mother nature lands on.
     * Checks if nearby islands have the same owner and possibly joins them.
     * Checks if someone won the game after an island is conquered
     * @param steps number of steps forward of mother nature
     */
    public void moveMotherNature(int steps) throws PhaseNotRightException {
        if(turn.getCurrentPhase() != TurnPhase.MOTHERNATURE)
            throw new PhaseNotRightException("You can't move mother nature in this stage of the game. " +
                    "Current phase is " + turn.getCurrentPhase().toString().toLowerCase());
        possibleMovingSteps.update(turn.getPossibleMovingSteps());
        if(steps < 1)
            throw new IllegalArgumentException("You must move! Steps must be grater or equal than zero.");
        if(steps > possibleMovingSteps.get())
            throw new IllegalArgumentException("Too many steps. possible steps: " + possibleMovingSteps.get());
        if ((motherNaturePosition + steps) >= (islandList.size()))
            motherNaturePosition += steps - islandList.size();
        else
            motherNaturePosition += steps;
        conquerIsland(motherNaturePosition);
        if(turn.isSkipCloudPhase())
            possibleMovingSteps.update(turn.getPossibleMovingSteps());
    }

    /**
     * Computes influence on given island and sets a new owner if possible.
     * @param islandIndex the current island mother nature is on
     */
    protected void conquerIsland(int islandIndex) {
        Island island = islandList.remove(islandIndex);
        Team teamBeforeComputing = island.getOwnership();
        Team t = GreaterTeam.findGreaterTeam(influence.getInfluenceMap(island));
        islandList.add(islandIndex, island.setOwnership(t));
        if (t != null && t != teamBeforeComputing)
            checkJoinIsland(islandIndex);
    }

    /**
     * Checks if neighbouring islands have the same owner and joins them to the current island
     * @param islandIndex the island mother nature is on
     */
    protected void checkJoinIsland(Integer islandIndex) {
        List<Integer> islandsToJoin = getNeighbouringIsland(islandIndex);
        islandsToJoin = getSameOwner(islandsToJoin);
        if(!islandsToJoin.isEmpty())
            joinIslands(islandsToJoin);
    }

    @Contract(pure = true)
    protected List<Integer> getNeighbouringIsland(int islandIndex) {
        int previous = islandIndex - 1,
                next = islandIndex + 1;
        if(islandIndex == 0)
            previous = islandList.size() - 1;
        else if(islandIndex == islandList.size() - 1)
            next = 0;
        return List.of(previous, islandIndex, next);
    }

    private List<Integer> getSameOwner(List<Integer> neightbouringIsland) {
        Set<Integer> islandToJoin = new HashSet<>();
        Island  firstIsland = islandList.get(neightbouringIsland.get(0)),
                secondIsland = islandList.get(neightbouringIsland.get(1)),
                thirdIsland = islandList.get(neightbouringIsland.get(2));
        if (firstIsland.getOwnership() == secondIsland.getOwnership())
            islandToJoin.addAll(neightbouringIsland.subList(0, 2));
        if (secondIsland.getOwnership() == thirdIsland.getOwnership())
            islandToJoin.addAll(neightbouringIsland.subList(1, 3));
        return islandToJoin.stream().toList();
        //add all should remove the repetition of the second island index
    }

    /**
     * Joins the islands and puts the Archipelago in the list.
     * @param islandsToJoin the indexes of 2 or three islands that are about to be merged
     */
    protected void joinIslands(@NotNull List<Integer> islandsToJoin) {
        int firstIslandIndex = islandsToJoin.get(0);
        List<Island> islandsToRemove = new ArrayList<>();
        islandsToJoin.forEach(index -> islandsToRemove.add(this.islandList.get(index)));
        this.islandList.add(firstIslandIndex, new Archipelago(islandsToRemove));
        this.islandList.removeAll(islandsToRemove);
        motherNaturePosition = firstIslandIndex;
    }

    /**
     * Moves students from the selected cloud to the waiting room of the current player.
     * @param PlayerID the id of the player that ask for this move
     * @param cloudID  0<=cloudID<nPlayers
     */
    public void chooseCloud(String PlayerID, int cloudID) throws TooManyStudentsException, PhaseNotRightException {
        if(turn.getCurrentPhase() != TurnPhase.CLOUD)
            throw new PhaseNotRightException("You can't choose a cloud in this phase of the game. " +
                    "Current phase is " + turn.getCurrentPhase().toString().toLowerCase());
        if(cloudID < 0 || cloudID >= nPlayer)
            throw new IllegalArgumentException("Illegal cloudId number. Please, insert a number between 1 and " + nPlayer);
        Castle castle = castleMap.get(PlayerID);
        Cloud cloud = cloudList.get(cloudID);
        castle.addStudentsInWaitingRoom(cloud.choose());
    }

    /**
     * Resets the students that can be moved
     */
    public void endOfRound() {
        boolean areCloudsRefillable;
        if(turn.isLastActionTurn()) {
            areCloudsRefillable = cloudRefill();
            endGame =  areCloudsRefillable || endedCards();
        }
        possibleMovingSteps.zero();
    }

    /**
     * after a whole turn
     */
    public boolean cloudRefill() {
        if(turn.isSkipCloudPhase())
            return true;
        if(bag.remainingStudents() < (cloudList.size()*cloudList.get(0).getSTUDENTS_ON_CLOUD())) {
            turn.setSkipCloudPhase(true);
            return false;
        }
        if(bag.remainingStudents() == (cloudList.size()*cloudList.get(0).getSTUDENTS_ON_CLOUD())) {
            turn.setSkipCloudPhase(true);
        }
        //if()
        //cloudList.stream().map(Cloud::isFillable).anyMatch(false);
        cloudList.forEach(Cloud::refill);
        return false;
    }

//Ending of a game

    /**
     * Checks if there are no more cards.
     * @return number of cards left.
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    private boolean endedCards() {
        Castle currPlayerCastle = castleMap.get(turn.getCurrentPlayer());
        int cardsLeft = (int) currPlayerCastle.getDeck().stream().filter(Card::isAvailable).count();
        return cardsLeft == 0;
    }

    /**
     * Checks if the game is won (if he placed the 8th tower)
     * @return the winner team
     */
    public boolean isWinningState() {
        EnumMap<Team, Integer> nTowers = placedTowers();
        for (Team t : Team.values())
            if (nTowers.get(t) >= (castleMap.size() == 3 ? TOWERS_TO_PLACE_TO_WIN_3_PLAYERS : TOWERS_TO_PLACE_TO_WIN_2_4_PLAYERS)
                    || islandList.size() <= MINIMUM_NUMBER_OF_ISLANDS)
                return true;
        return false;
    }

    /**
     * In case of End of Game (by resources or by state) checks if there is a winner.
     * @throws DrawException if the game is a tie both for the islands and the professors.
     */
    public Team getWinner() throws DrawException {
        Map<Team, Integer> nTowers = placedTowers();
        for (Team t : Team.values())
            if (nTowers.get(t) == (castleMap.size() == 3 ? TOWERS_TO_PLACE_TO_WIN_3_PLAYERS : TOWERS_TO_PLACE_TO_WIN_2_4_PLAYERS))
                return t;
        Team winner = GreaterTeam.findGreaterTeam(nTowers);
        if (winner == null)
            return teamWithMostProfessors();
        return winner;
    }

    /** @return a map that contains the number of placed towers on the islands for each team */
    public EnumMap<Team,Integer> placedTowers() {
        EnumMap<Team, Integer> teamTowersMap = new EnumMap<>(Team.class);
        for (Team t : Team.values()) { //fill nTowers map for all team at 0
            teamTowersMap.put(t, 0);
            for (Island i : islandList) //sum towers for each island to the map
                if (i.getOwnership() == t)
                    teamTowersMap.replace(t, teamTowersMap.get(t) + i.getIslandNumber());
        }
        return teamTowersMap;
    }

    /**
     * @return the team with the most professors.
     * @throws DrawException if there is no team with more professors than the others (E.g.: White: 2, Black: 1, Grey: 1)
     */
    private @NotNull Team teamWithMostProfessors() throws DrawException {
        Team withMoreProfessors = null;
        int max = 0;
        for(Team t1 : Team.values()) {
            int sum = 0;
            for(Team t2 : getProfessorsMap().values()) {
                if(t1 == t2) sum++;
            }
            if (sum > max) {
                max = sum;
                withMoreProfessors = t1;
            }
            else if (sum == max)
                withMoreProfessors = null;
        }
        if (withMoreProfessors == null) throw new DrawException("Two players have the same number of professors");
        return withMoreProfessors;
    }

    public void playExpertCard (int idChar, Integer islandIndex, List<StudentColor> studentsList) throws StudentException, CoinException, WrongGameModeException, PhaseNotRightException {
        throw new WrongGameModeException("You can't use this command in this game mode!");
    }

    public Map<CharacterUtility, StandardCharacter> getAvailableCharacters() throws WrongGameModeException {
        throw new WrongGameModeException("You can't use this command in this game mode!");
    }

    public void changePhase(){
        turn.changePhase();
    }
    //Getters
    public List<Cloud> getCloudList() {
        return new ArrayList<>(cloudList);
    }

    public List<Island> getIslandList() {
        return new ArrayList<>(islandList);
    }

    public Map<String, Castle> getCastleMap() {
        return new HashMap<>(castleMap);
    }

    public Castle getCastle(String playerID) {
        return castleMap.get(playerID);
    }

    public Map<StudentColor, Team> getProfessorsMap() {
        return influence.getProfessorsMap();
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public Turn getTurn() {
        return turn;
    }

    public boolean isEndGame() {
        return endGame;
    }

    //FOR VIEW:
    public BoardData getData(String playerID) {
        return ModelDataBuilder.newBoardData(this, playerID);
    }

    public CharacterUtility getPlayedExpertChar() throws WrongGameModeException {
        throw new WrongGameModeException("You can't use this command in this gamemode.");
    }

    public Bag getBag() {
        return bag;
    }

    public String getCurrentPlayer() {
        return turn.getCurrentPlayer();
    }
}
