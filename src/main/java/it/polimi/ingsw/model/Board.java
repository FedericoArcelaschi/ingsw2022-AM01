package it.polimi.ingsw.model;


import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Board {
    private static final int numOfStudentsPerColor=24;
    protected int motherNaturePosition = 0;
    protected int nPlayer;
    protected final Bag bag;
    protected final List<Cloud> cloudList = new ArrayList<>();
    protected final List<Island> islandList = new ArrayList<>();
    protected final Map<String, Castle> castleMap = new HashMap<>();
    protected Map<Color, Team> professorsMap;
    protected final Turn turn;
    private final long seed;
    //constants
    private final int numberOfIslands = 12;
    private final int numberOfIslandsToEndGame = 3;
    private final int numberOfTowersToPlace = 8;
    private static final int cloudSize2_4Player = 3;
    private static final int cloudSize3Player = 4;


    public Board(String playerID1, String playerID2, Turn turn, int seed){
        nPlayer = 2;
        this.seed = seed;
        bag = new Bag(numOfStudentsPerColor, seed);
        this.turn=turn;
        castleMap.put(playerID1, new Castle(Team.WHITE, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID2, new Castle(Team.BLACK, nPlayer, bag.extractForCastleSetup(nPlayer)));
        nPlayer = castleMap.size();
        construct();
    }

    public Board(String playerID1, String playerID2, String playerID3, Turn turn, int seed){
        nPlayer = 3;
        this.seed = seed;
        bag = new Bag(numOfStudentsPerColor, seed);
        construct();
        castleMap.put(playerID1, new Castle(Team.WHITE, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID2, new Castle(Team.BLACK, nPlayer, bag.extractForCastleSetup(nPlayer)));
        castleMap.put(playerID3, new Castle(Team.GREY, nPlayer, bag.extractForCastleSetup(nPlayer)));
        this.turn = turn;
    }

    public Board(String playerID1, String playerID2, String playerID3, String playerID4, Turn turn, int seed){
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
        setupProfessorMap();
    }

    /**Constructor for ExpertBoard
     */
    protected Board(Turn turn, int seed, int nPlayer){
        this.turn = turn;
        this.seed = seed;
        this.bag = new Bag(numOfStudentsPerColor, seed);
        this.nPlayer = nPlayer;
        setupClouds();
        setupProfessorMap();
    }

    /**
     * Generates the clouds based on the nPlayer
     */

    protected void setupClouds(){
        int cloudSize = nPlayer == 3 ? cloudSize3Player : cloudSize2_4Player;
        for (int i = 0; i < nPlayer; i++) cloudList.add(new Cloud(bag, cloudSize));
    }

    /**
     * instance at null the professor map
     */
    private void setupProfessorMap(){
        professorsMap = new HashMap<>();
        for(Color c : Color.values()){
            professorsMap.put(c,null);
        }
    }

    /**
     * Generates the islands.
     */

    private void setupIslands(){
        List<Color> s = bag.extractForIslandSetup();
        for(int i = 0, c = 0; i < numberOfIslands; i++){
            if( i % (numberOfIslands / 2) == 0){
                islandList.add(new Island());
            }
            else{
                islandList.add(new Island(s.get(c)));
                c++;
            }
        }
    }

    /**
     * Refills each cloud with new students.
     * @return if the move is legal and played or not
     */
    public boolean refillClouds() {
        for(Cloud c: cloudList){
            if (!c.refill()) return false;
        }
        return true;
    }

    /**
     * Moves students from the selected cloud to the waiting room of the current player.
     * @param PlayerID the id of the player that ask for this move
     * @param cloudID the cloud that is chosen
     * @return if the move is legal and played or not
     */
    public boolean chooseCloud(String PlayerID, int cloudID) throws NotYourTurnException, TooManyStudentsException {
        if(!turn.getCurrentPlayer().equals(PlayerID)) throw new NotYourTurnException();
        Castle castle = castleMap.get(PlayerID);
        Cloud cloud = cloudList.get(cloudID);
        return castle.addStudentsInWaitingRoom(cloud.choose());
    }

    /**
     * Checks which player has more students of each color and assigns the professors.
     */

    private void updateProfessorsOwners() {
        for(Color color : Color.values()) {
            int max = 0;
            Team newOwner = professorsMap.get(color);
            for (Castle castle : castleMap.values())
                if(professorsMap.get(color) == castle.getTeam())
                    max = castle.getDiningRoom().get(color);
            for (Castle castle : castleMap.values()) {
                int n = castle.getDiningRoom().get(color);
                if(n > max){
                    max = n;
                    newOwner = castle.getTeam();
                }
            }
            if(newOwner != null) professorsMap.replace(color, newOwner);
        }
    }

    /**
     * Moves students from the waiting room to the dining room.
     * @param PlayerID the id of the player that ask for this move
     * @param students a list of students you want to move
     * @throws NoSuchStudentException if the student is not in the Waiting Room of the current player
     * @throws NotYourTurnException if the player in the argument is not the current player
     * @throws TooManyStudentsException if the castle dining room already contains 9 students
     */
    public void moveStudentToDiningRoom(String PlayerID, List<Color> students)
            throws NoSuchStudentException, NotYourTurnException, TooManyStudentsException {
        if(!turn.getCurrentPlayer().equals(PlayerID)) throw new NotYourTurnException();
        Castle castle = castleMap.get(PlayerID);
        castle.removeStudentsFromWaitingRoom(students);
        castle.addStudentsInDiningRoom(students);
        updateProfessorsOwners();
    }

    /**
     * Moves the students in the list <code>students</code> from <code>Player</code> 's waiting room
     * to the island n°<code>islandNumber</code>.
     * @param Player the id of the player that ask for this move
     * @param islandNumber the number of the island where you want to move the students
     * @param students a list of students you want to move
     * @return true if the students are present and added to the island.
     */
    public boolean moveStudentToIsland(String Player, int islandNumber, List<Color> students)
            throws NoSuchStudentException, NotYourTurnException {
        if(!turn.getCurrentPlayer().equals(Player)) throw new NotYourTurnException();
        castleMap.get(Player).removeStudentsFromWaitingRoom(students);
        for(Color c : students){
            islandList.get(islandNumber).addStudent(c);
        }
        return true;
    }

    /**
     * @param PlayerID  the id of the player that ask for this move
     * @param card the number of the card the player want to use
     * @return if the move is legal and played, false otherwise
     */
    public boolean playCard(String PlayerID, int card) throws NotYourTurnException {
        if(!turn.getCurrentPlayer().equals(PlayerID)) throw new NotYourTurnException();
        Castle castle = castleMap.get(PlayerID);
        return castle.playCard(card);
    }

    /**
     * @param PlayerID the id of the player that ask for this move
     * @return a list of not yet played card
     */

    public List<Card> getDeck(String PlayerID){
        Castle castle = castleMap.get(PlayerID);
        return castle.getDeck();
    }

    /**
     * @return a map that contains the number of placed towers on the islands for each team
     */
    private Map<Team,Integer> sumTowers() {
        Map<Team, Integer> nTowers = new HashMap<>();
        for (Team t : Team.values()) { //fill nTowers map for all team at 0
            nTowers.put(t, 0);
        }
        for (Island i : islandList) { //sum towers for each island to the map
            if (i.getOwnership() != null) {
                nTowers.replace(i.getOwnership(), nTowers.get(i.getOwnership()) + i.getIslandNumber());
            }
        }
        return nTowers;
    }

    /**
     * Calculates who has the highest Integer in the map that he receives
     * @param map map that contains team and a generic Integer
     * @return the team with the most towers.
     */
    private Team findMaxTeam(Map<Team,Integer> map){
        int max;
        Team winner;

        max = map.get(Team.WHITE);
        winner = Team.WHITE;
        if(map.get(Team.BLACK) > max){
            max = map.get(Team.BLACK);
            winner = Team.BLACK;
        }
        else if (map.get(Team.BLACK) == max) winner = null;
        if(map.get(Team.GREY) > max){
            winner = Team.GREY;
        }
        else if (map.get(Team.GREY) == max) winner = null;
        return winner;
    }

    /**
     * Checks if there are no more cards.
     * @return number of cards left.
     */
    private int remainingCards(){
        int cardsLeft = 0;
        for(Castle castle : castleMap.values()) cardsLeft += (int) castle.getDeck().stream().filter(card -> card.isAvailable()).count();
        return cardsLeft;
    }

    /**
     * Checks if the game is won after a player finishes his turn.
     * @return the winner team
     */
    public Team isWinningPosition() {
        Map <Team, Integer> nTowers = sumTowers();
        Team winner = null;
        if(islandList.size() <= numberOfIslandsToEndGame){
            winner = findMaxTeam(nTowers);
            if(winner == null){
                winner = teamWithMostProfessors();
            }
        }
        else{
            for(Team t : Team.values()){
                if(nTowers.get(t) >= numberOfTowersToPlace) winner = t;
            }
        }
        return winner;
    }

    /**
     * Returns the team with the most professors.
     * @return the team with the most professors.
     */
    private Team teamWithMostProfessors() {
        Team more = null;
        int max = 0;
        for(Team t1 : Team.values()){
            int sum = 0;
            for(Team t2 : professorsMap.values()){
                if(t1 == t2) sum++;
            }
            if(sum > max){
                max = sum;
                more = t1;
            }
            else if (sum == max) {
                more = null;
            }
        }
        return more;
    }

    /**
     * Checks if the game is won after each player's turn is over by checking whether the players don't have any more
     * cards or if there are no more students in the bag.
     * @return the winner team
     */
    public Team isWonByResources() {
        if(bag.remainingStudents() == 0 || remainingCards() == 0){
            Map <Team, Integer> nTowers = sumTowers();
            Team teamWithMostTowers = findMaxTeam(nTowers);
            if(teamWithMostTowers == null) return teamWithMostProfessors();
            return teamWithMostTowers;
        }
        else return null;
    }

    /**
     * Joins the islands and puts the new island in the list.
     */
    public void joinIslands(@NotNull List<Island> islandList) {
        int firstIslandIndex
                = this.islandList
                .indexOf(islandList.get(0));
        if(firstIslandIndex == -1)
            throw new IllegalArgumentException("island: " + islandList.get(0).toString() + "not found!");
        Island newIsland = null;
        if(islandList.size()==2){
            if(this.islandList.removeAll(Arrays.asList(islandList.get(0), islandList.get(1))))
                newIsland = new Archipelago(islandList.get(0),islandList.get(1));
        }
        else if(islandList.size()==3){
            if(this.islandList.removeAll(Arrays.asList(islandList.get(0), islandList.get(1), islandList.get(2))))
                newIsland = new Archipelago(islandList.get(0),islandList.get(1),islandList.get(2));
        }
        else
            throw new IllegalArgumentException("wrong number of islands in the given list: " + islandList);
        this.islandList.add(firstIslandIndex, newIsland);
    }

    /**
     * Calculates the influence and sets a new owner
     * on the island mother nature lands on.
     * Checks if nearby islands have the same owner and, if possible, joins them.
     * Checks if someone won the game after an island is conquered.
     * @param move number of steps forward of mother nature
     */
    public void moveMotherNature(int move) {
        if (motherNaturePosition + move / islandList.size() >= 1) motherNaturePosition += move - islandList.size();
        else motherNaturePosition += move;
        conquerIsland(islandList.get(motherNaturePosition));

    }

    /**
     * Calculates influence on given island and sets a new owner if possible.
     * @param island the current island mother nature is on
     */
    protected void conquerIsland(@NotNull Island island) {
        Map<Team, Integer> influence = island.calculateInfluence(professorsMap);
        Team t = findMaxTeam(influence);
        if (t != null) island.setOwnership(t);
        checkJoinIsland(island);
    }

    /**
     * Checks if neighbouring islands have the same owner and joins them to the current island
     * @param island the island mother nature is on
     */
    protected void checkJoinIsland(Island island) {
        Island previous, next;
        List<Island> islandToJoin = new ArrayList<>();
        islandToJoin.add(island);
        if(motherNaturePosition == 0){
            previous = islandList.get(islandList.size()-1);
            next = islandList.get(1);
        }
        else if(motherNaturePosition == islandList.size()) {
            previous = islandList.get(islandList.size() - 1);
            next = islandList.get(0);
        }
        else{
            previous = islandList.get(motherNaturePosition - 1);
            next = islandList.get(motherNaturePosition + 1);
        }
        if(island.getOwnership() != null && previous.getOwnership() == island.getOwnership()) islandToJoin.add(0,previous);
        if(island.getOwnership() != null && next.getOwnership() == island.getOwnership()) islandToJoin.add(next);
        if(islandToJoin.size() == 2 || islandToJoin.size() == 3) joinIslands(islandToJoin);
    }

    public boolean equals(Board b){
        return this.motherNaturePosition == b.motherNaturePosition && this.nPlayer == b.nPlayer &&
                this.islandList.equals(b.islandList) && this.bag.equals(b.bag) && this.cloudList.equals(b.cloudList) &&
                this.castleMap.equals(b.castleMap) && this.professorsMap.equals(b.professorsMap) && this.turn.equals(b.turn);
    }

    //getters

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

    public Map<Color, Team> getProfessorsMap() {//TODO: make return only a copy
        return professorsMap;
    }

    public int getNPlayer() {
        return nPlayer;
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public List<String> getPlayerUsernames(){
        List<String> playerUsernames = new ArrayList<>();
        for (String username: castleMap.keySet()) {
            playerUsernames.add(username);
        }
        return playerUsernames;
    }

    @Override
    public String toString() {
        return "Board{" +
                "motherNaturePosition=" + motherNaturePosition +
                ", nPlayer=" + nPlayer +
                ", bag=" + bag +
                ", cloudList=" + cloudList +
                ", islandList=" + islandList +
                ", castleMap=" + castleMap +
                ", professorsMap=" + professorsMap +
                ", turn=" + turn +
                '}';
    }
}
