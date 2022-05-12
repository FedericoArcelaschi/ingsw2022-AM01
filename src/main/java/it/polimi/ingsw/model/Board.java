package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.functionalnterfaces.GreaterTeam;
import it.polimi.ingsw.model.influence.Influence;
import it.polimi.ingsw.model.influence.Professors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Board {
    private static final int numOfStudentsPerColor = 24;
    protected int motherNaturePosition = 0;
    protected int nPlayer;
    protected final Bag bag;
    protected final List<Cloud> cloudList = new ArrayList<>();
    protected final List<Island> islandList = new ArrayList<>();
    protected final Map<String, Castle> castleMap = new HashMap<>();

    private Influence influence = new Influence(new Professors(castleMap));

    protected final Turn turn;
    private long seed;
    protected IntegerBoxing possibleMovingSteps = new IntegerBoxing(0); //calculated form the card: must be stored in memory til the player action turn
        //TODO: fix this in expert.

    //constants
    private final int numberOfIslands = 12;
    private final int numberOfIslandsToEndGame = 3;
    private final int numberOfTowersToPlace = 8;
    private final int cloudSize2_4Player = 3;
    private final int cloudSize3Player = 4;


    public Board(String playerID1, String playerID2, Turn turn, long seed){
        nPlayer = 2;
        this.seed = seed;
        bag = new Bag(numOfStudentsPerColor, seed);
        this.turn=turn;
        castleMap.put(playerID1, new Castle(Team.WHITE, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID2, new Castle(Team.BLACK, nPlayer, bag.extractForCastleSetup(nPlayer)));
        construct();
    }

    public Board(String playerID1, String playerID2, String playerID3, Turn turn, long seed){
        nPlayer = 3;
        this.seed = seed;
        bag = new Bag(numOfStudentsPerColor, seed);
        castleMap.put(playerID1, new Castle(Team.WHITE, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID2, new Castle(Team.BLACK, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID3, new Castle(Team.GREY, nPlayer, bag.extractForCastleSetup(nPlayer)));
        this.turn = turn;
        construct();
    }

    public Board(String playerID1, String playerID2, String playerID3, String playerID4, Turn turn, long seed){
        nPlayer = 4;
        this.seed = seed;
        bag = new Bag(numOfStudentsPerColor, seed);
        this.turn=turn;
        castleMap.put(playerID1, new Castle(Team.WHITE, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID2, new Castle(Team.BLACK, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID3, new Castle(Team.WHITE, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID4, new Castle(Team.BLACK, nPlayer, bag.extractForCastleSetup(nPlayer)));
        construct();
    }

    /**Cleans the constructor implementation
     */
    private void construct(){
        setupClouds();
        setupIslands();
    }

    /**
     * Constructor for ExpertBoard
     */
    protected Board(Turn turn, long seed, int nPlayer){
        this.turn = turn;
        this.seed = seed;
        this.bag = new Bag(numOfStudentsPerColor, seed);
        this.nPlayer = nPlayer;
        construct();
    }


    /**
     * Generates the clouds based on the nPlayer
     */
    protected void setupClouds(){
        int cloudSize = nPlayer == 3 ? cloudSize3Player : cloudSize2_4Player;
        for (int i = 0; i < nPlayer; i++) cloudList.add(new Cloud(bag, cloudSize));
    }

    /**
     * Generates the islands.
     */

    private void setupIslands() {
        List<Color> s = bag.extractForIslandSetup();
        for (int i = 0, c = 0; i < numberOfIslands; i++) {
            if (i % (numberOfIslands / 2) == 0) {
                islandList.add(new Island());
            } else {
                islandList.add(new Island(s.get(c)));
                c++;
            }
        }
    }

//methods for the PLANNING PHASE
    /**
     * @param PlayerID the id of the player that ask for this move
     * @return a list of not yet played card
     */
    public Boolean[] getAvaliableCard(String PlayerID){
        Castle castle = castleMap.get(PlayerID);
        return castle.getCards();
    }

    /**
     * @param PlayerID  the id of the player that ask for this move
     * @param card the number of the card the player want to use
     * @return if the move is legal and played, false otherwise
     */
    public boolean playCard(String PlayerID, int card) throws NotYourTurnException {
        if(!turn.getCurrentPlayer().equals(PlayerID))
            throw new NotYourTurnException("You can't play, It's " + getCurrentPlayer() + "'s turn.");
        Castle castle = castleMap.get(PlayerID);
        possibleMovingSteps.setInt((card + 1 )/2);
        return castle.playCard(card);
    }

//methods for the action phase

    /**
     * Moves students from the waiting room to the dining room.
     * @param playerID the id of the player that ask for this move
     * @param students a list of students you want to move
     * @throws NoSuchStudentException if the student is not in the Waiting Room of the current player
     * @throws NotYourTurnException if the player in the argument is not the current player
     * @throws TooManyStudentsException if the castle dining room already contains 9 students
     */
    public void moveStudentToDiningRoom(String playerID, List<Color> students)
            throws NoSuchStudentException, NotYourTurnException, TooManyStudentsException {
        if(!turn.getCurrentPlayer().equals(playerID))
            throw new NotYourTurnException("It's "+getCurrentPlayer()+"'s turn. "+ playerID +" can't play.");
        Castle castle = castleMap.get(playerID);
        castle.removeStudentsFromWaitingRoom(students);
        castle.addStudentsInDiningRoom(students);
        influence.updateProfessors();
    }

    /**
     * Moves the students in the list <code>students</code> from <code>Player</code> 's waiting room
     * to the island n°<code>islandNumber</code>.
     * @param playerID the id of the player that ask for this move
     * @param islandNumber the number of the island where you want to move the students
     * @param students a list of students you want to move
     * @return true if the students are present and added to the island.
     */
    public boolean moveStudentToIsland(String playerID, int islandNumber, List<Color> students) throws NoSuchStudentException, NotYourTurnException, TooManyStudentsException {
        if(!turn.getCurrentPlayer().equals(playerID)) throw new
                NotYourTurnException("It's "+getCurrentPlayer()+"'s turn. "+ playerID +" can't play.");
        castleMap.get(playerID).removeStudentsFromWaitingRoom(students);
        for(Color c : students){
            islandList.get(islandNumber).addStudent(c);
        }
        return true;
    }


    /**
     * Calculates the influence and sets a new owner
     * on the current island mother nature lands on.
     * Checks if nearby islands have the same owner and possibly joins them.
     * Checks if someone won the game after an island is conquered
     * @param steps number of steps forward of mother nature
     */
    public void moveMotherNature(int steps) {
        if (steps >
                possibleMovingSteps.getInt())
            throw new IllegalArgumentException("too many steps");
        if (motherNaturePosition + steps > islandList.size() - 1) motherNaturePosition += steps - islandList.size();
        else motherNaturePosition += steps;
        conquerIsland(islandList.get(motherNaturePosition));
    }

    /**
     * Calculates influence on given island and sets a new owner if possible.
     * @param island the current island mother nature is on
     */
    protected void conquerIsland(@NotNull Island island) {
        Team teamBeforeComputing = island.getOwnership();
        //TODO: should somehow change this studentColor from ExpertMode
        //idea-> decorator intorno all'interface
        Team t = GreaterTeam.findGreaterTeam(influence.getInfluenceMap(island));
        if(t == null || t.equals(teamBeforeComputing))
            //No one conquers the island. We may want to differentiate the cases to tell the client
            return;
        island = island.setOwnership(t);
        checkJoinIsland(island);
    }

    /**
     * Checks if neighbouring islands have the same owner and joins them to the current island
     * @param island the island mother nature is on
     */
    protected void checkJoinIsland(Island island) {
        getNeighbouringIsland(island);
        List<Island> islandToJoin = getSameOwner(getNeighbouringIsland(island));
        if(islandToJoin != null) joinIslands(islandToJoin);
    }

    //todo: testing
    private List<Island> getSameOwner(List<Island> neightbouringIsland) {
        List<Island> islandToJoin = null;
        if(neightbouringIsland.get(0).getOwnership() != null){
            if(neightbouringIsland.get(0).getOwnership() == neightbouringIsland.get(1).getOwnership())
                islandToJoin = neightbouringIsland.subList(0,2);
            if(neightbouringIsland.get(1).getOwnership() == neightbouringIsland.get(2).getOwnership())
                islandToJoin.add(neightbouringIsland.get(2));
            return islandToJoin;
        }
        if(neightbouringIsland.get(1).getOwnership() == neightbouringIsland.get(2).getOwnership())//not the firstone for sure.
            return islandToJoin = neightbouringIsland.subList(1,3);
        return islandToJoin;
    }

    //TODO: testing
    private  List<Island> getNeighbouringIsland(Island island) {
        int islandIndex = islandList.indexOf(island);
        return getNeighbouringIsland(islandIndex);
    }

    @Contract(pure = true)
    protected List<Island> getNeighbouringIsland(int islandIndex) {
        Island previous, next, island = islandList.get(islandIndex);
        if(islandIndex == 0){
            previous = islandList.get(islandList.size() - 1);
            next = islandList.get(1);
        }
        else if(islandIndex == islandList.size() - 1) {
            previous = islandList.get(islandList.size() - 2);
            next = islandList.get(0);
        }
        else{
            previous = islandList.get(islandIndex - 1);
            next = islandList.get(islandIndex + 1);
        }
        return new ArrayList<>(Arrays.asList(previous, island, next));
    }

    /**
     * Joins the islands and puts the new island in the list.
     */
    protected void joinIslands(@NotNull List<Island> islandList) { //todo: would be convenient to implement a 'List' with next
        int firstIslandIndex
                = this.islandList
                .indexOf(islandList.get(0));
        if(firstIslandIndex == -1)
            throw new IllegalArgumentException("island: " + islandList.get(0).toString() + "not found!");
        Island newIsland = null;
        if(islandList.size()==2){
            if(this.islandList.removeAll(islandList))
                newIsland = new Archipelago(islandList.get(0),islandList.get(1));
        }
        else if(islandList.size()==3){
            if(this.islandList.removeAll(islandList))
                newIsland = new Archipelago(islandList.get(0),islandList.get(1),islandList.get(2));
            else
                throw new IllegalStateException();
        }
        else
            throw new IllegalArgumentException("wrong number of islands in the given list: " + islandList);
        this.islandList.add(firstIslandIndex, newIsland);
        motherNaturePosition = firstIslandIndex;
    }



    /**
     * Moves students from the selected cloud to the waiting room of the current player.
     * @param PlayerID the id of the player that ask for this move
     * @param cloudID the cloud that is chosen
     * @return if the move is legal and played or not
     */
    public boolean chooseCloud(String PlayerID, int cloudID) throws NotYourTurnException, TooManyStudentsException {
        if(!turn.getCurrentPlayer().equals(PlayerID))
            throw new NotYourTurnException("It's " + turn.getCurrentPlayer() + "'s turn. " + PlayerID + " can't choose a cloud.");
        Castle castle = castleMap.get(PlayerID);
        Cloud cloud = cloudList.get(cloudID);
        return castle.addStudentsInWaitingRoom(cloud.choose());
    }

    /**
     * Refills each cloud with new students.
     * @return if the move is legal and played or not
     */
    public void refillClouds() {
        for(Cloud c: cloudList){
            c.refill();
        }
    }

//Ending of a game

    /**
     * Checks if the game is won after each player's turn is over by checking whether the players don't have any more
     * cards or if there are no more students in the bag.
     * @return the winner team
     */
    public Team isWonByResources() {
        if(bag.remainingStudents() == 0 || remainingCards() == 0){
            Map <Team, Integer> nTowers = sumTowers();
            Team teamWithMostTowers = GreaterTeam.findGreaterTeam(nTowers);
            if(teamWithMostTowers == null) return teamWithMostProfessors();
            return teamWithMostTowers;
        }
        else return null;
    }


    /**
     * Checks if there are no more cards.
     * @return number of cards left.
     */
    private int remainingCards(){
        int cardsLeft = 0;
        for(Castle castle : castleMap.values()) cardsLeft += (int) Arrays.stream(castle.getCards()).filter(card -> card != null && !card).count();
        return cardsLeft;
    }

    /**
     * Checks if the game is won after a player finishes his turn.
     * @return the winner team
     */
    public Team isWinningPosition() {
        Map <Team, Integer> nTowers = sumTowers();
        Team winner = null;
        for(Team t : Team.values()){
            if(nTowers.get(t) >= numberOfTowersToPlace) return winner = t;
        }
        if(islandList.size() <= numberOfIslandsToEndGame){
            winner = GreaterTeam.findGreaterTeam(nTowers);
            if(winner == null){
                winner = teamWithMostProfessors();
            }else{

            }
        }
        return winner;
    }

    /**
     * Returns the team with the most professors.
     * //TODO: test after modifications.
     * todo: maybe implement with greaterTeam()
     */
    private Team teamWithMostProfessors() {
        Team withMoreProfessors = null;
        int max = 0;
        for(Team t1 : Team.values()){
            int sum = 0;
            for(Team t2 : Team.values()){
                if(t1 == t2) sum++;
            }
            if(sum > max){
                max = sum;
                withMoreProfessors = t1;
            }
            else if (sum == max) {
                withMoreProfessors = null;
            }
        }
        return withMoreProfessors;
    }

    /**
     * Calculates who has the highest Integer in the map that he receives
     * @param nTowers map that contains team and the numberOfTowers
     * @return the team with the most towers.
     */

    /**
     * @return a map that contains the number of placed towers on the islands for each team
     */
    private Map<Team,Integer> sumTowers() {
        Map<Team, Integer> nTowers = new HashMap<>();
        for (Team t : Team.values()) { //fill nTowers map for all team at 0
            nTowers.put(t, 0);
            for (Island i : islandList) //sum towers for each island to the map
                if (i.getOwnership() == t)
                    nTowers.replace(t, nTowers.get(t) + i.getIslandNumber());
        }
        return nTowers;
    }

    //Getters

    public String getCurrentPlayer() {
        return turn.getCurrentPlayer();
    }

    public List<Cloud> getCloudList() {
        return new ArrayList<>(cloudList);
    }

    public List<Island> getIslandList() {
        return new ArrayList<>(islandList);
    }

    public Map<String, Castle> getCastleMap() {
        return new HashMap<>(castleMap);
    }

    public Castle getCastle(String playerID){//TODO: make return only a copy
        return castleMap.get(playerID);
    }

    @Deprecated//todo: ne ho davvero bisogno?
    public Map<Color, Team> getProfessorsMap() {//TODO: make return only a copy
        return influence.getProfessorsMap();
    }

    public int getPossibleMovingSteps() {
        return possibleMovingSteps.getInt();
    }
}
