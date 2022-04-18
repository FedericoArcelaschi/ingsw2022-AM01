package it.polimi.ingsw.model;


import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private static final int numOfStudentsPerColor=24;
    protected int motherNaturePosition = 0;
    protected int nPlayer;
    protected final Bag bag = new Bag(numOfStudentsPerColor);
    protected final List<Cloud> cloudList = new ArrayList<>();
    protected final List<Island> islandList = new ArrayList<>();
    protected final Map<String, Castle> castleMap = new HashMap<>();
    protected Map<Color, Team> professorsMap;
    protected final Turn turn;
    protected int possibleMovingSteps;//calculated form the card: must be stored in memory til the player action turn


    public Board(String playerID1, String playerID2, Turn turn){
        nPlayer = 2;
        construct();
        castleMap.put(playerID1, new Castle(playerID1, Team.WHITE, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID2, new Castle(playerID2, Team.BLACK, nPlayer, bag.multipleExtract(9)));
        this.turn=turn;
    }

    public Board(String playerID1, String playerID2, String playerID3, Turn turn){
        nPlayer = 3;
        construct();
        castleMap.put(playerID1, new Castle(playerID1, Team.WHITE, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID2, new Castle(playerID2, Team.BLACK, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID3, new Castle(playerID3, Team.GREY, nPlayer, bag.multipleExtract(7)));
        this.turn = turn;
    }

    public Board(String playerID1, String playerID2, String playerID3, String playerID4, Turn turn){
        nPlayer = 4;
        construct();
        castleMap.put(playerID1, new Castle(playerID1, Team.WHITE, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID2, new Castle(playerID2, Team.BLACK, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID3, new Castle(playerID3, Team.WHITE, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID4, new Castle(playerID4, Team.BLACK, nPlayer, bag.multipleExtract(9)));
        this.turn = turn;
    }

    /**Cleans the constructor implementation
     */
    private void construct(){
        setupClouds();
        setupIslands();
        setupProfessorMap();
    }

    /**Contructor for ExpertBoard
     */
    protected Board(Turn turn){
        this.turn = turn;
        setupClouds();
        setupProfessorMap();
    }

    /**
     * Generates the clouds based on the nPlayer
     */

    protected void setupClouds(){
        if(nPlayer == 3) {
            for (int i = 0; i < nPlayer; i++) cloudList.add(new Cloud(bag, 3));
        }
        else{
            for(int i=0; i<nPlayer;i++)   cloudList.add(new Cloud(bag,4));
        }
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
     * generate the islands
     */

    private void setupIslands(){
        List<Color> s = bag.extractForIslandSetup();
        for(int i=0, c=0; i<12; i++){
            if(i%6 == 0){
                islandList.add(new Island());
            }
            else{
                islandList.add(new Island(s.get(c)));
                c++;
            }
        }
    }

    /**
     * refill all the cloud with new students
     * @return if the move is legal and played or not
     */
    public boolean refillClouds(){
        for(Cloud c: cloudList){
            if (!c.refill()) return false;
        }
        return true;
    }

    /**
     * move students form a chosen cloud to the waiting room of the player
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
     * Checks who has more student for each color and reassign the professors
     */

    public void updateProfessorsOwners(){
        for(Color color : Color.values()) {
            int max = 0;
            Castle newOwner = null;
            for (Castle castle : castleMap.values())
                if(professorsMap.get(color) == castle.getTeam())
                    max = castle.getDiningRoom().get(color);
            for (Castle castle : castleMap.values()) {
                int n = castle.getDiningRoom().get(color);
                if(n > max){
                    max = n;
                    newOwner = castle;
                }
            }
            if(newOwner != null) professorsMap.replace(color, newOwner.getTeam());
        }
    }

    /**
     * move students form the waiting room to the dining room
     * @param PlayerID the id of the player that ask for this move
     * @param students a list of students you want to move
     * @throws NoSuchStudentException if the student is not in the Waiting Room of the current player
     * @throws NotYourTurnException if the player in the argument is not the current player
     * @throws TooManyStudentsException if the castle dining room already contains 9 students
     */
    public void moveStudentToDR(String PlayerID, List<Color> students) throws NoSuchStudentException, NotYourTurnException, TooManyStudentsException {
        if(!turn.getCurrentPlayer().equals(PlayerID)) throw new NotYourTurnException();
        Castle castle = castleMap.get(PlayerID);
        castle.removeStudentsFromWaitingRoom(students);
        castle.addStudentsInDiningRoom(students);
        updateProfessorsOwners();
    }

    /**
     * @param PlayerID the id of the player that ask for this move
     * @param islandNumber the number of the island where you want to move the students
     * @param students a list of students you want to move
     * @return if the move is legal and played or not
     */
    public boolean moveStudentToIsland(String PlayerID, int islandNumber, List<Color> students) throws NoSuchStudentException, NotYourTurnException {
        if(!turn.getCurrentPlayer().equals(PlayerID)) throw new NotYourTurnException();
        castleMap.get(PlayerID).removeStudentsFromWaitingRoom(students);
        for(Color c : students){
            islandList.get(islandNumber).addStudent(c);
        }
        return true;
    }

    /**
     * @param PlayerID  the id of the player that ask for this move
     * @param card the number of the card the player want to use
     * @return if the move is legal and played or not
     */
    public boolean playCard(String PlayerID, int card) throws NotYourTurnException {//true if the move is legal, false otherwise
        if(!turn.getCurrentPlayer().equals(PlayerID)) throw new NotYourTurnException();
        Castle castle = castleMap.get(PlayerID);
        possibleMovingSteps = (card+1)/2;
        return castle.playCard(card);
    }

    /**
     * @param PlayerID the id of the player that ask for this move
     * @return a list of not yet played card
     */

    public Boolean[] getAvaliableCard(String PlayerID){
        Castle castle = castleMap.get(PlayerID);
        return castle.getCards();
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
     * Calculate who has the highest number of towers in the map that he receives
     * @param nTowers map that contains team and towers of the team on the islands
     * @return the team with most towers
     */
    private Team teamWithMoreTowers(Map<Team,Integer> nTowers){
        int max;
        Team winner;

        max = nTowers.get(Team.WHITE);
        winner = Team.WHITE;
        if(nTowers.get(Team.BLACK) > max){
            max = nTowers.get(Team.BLACK);
            winner = Team.BLACK;
        }
        else if (nTowers.get(Team.BLACK) == max) winner = null;
        if(nTowers.get(Team.GREY) > max){
            winner = Team.GREY;
        }
        else if (nTowers.get(Team.GREY) == max) winner = null;
        return winner;
    }

    /**
     * Use the teamWithMoreTowers algorithm to determine who has more influence
     * @param influence map that contains influence for each team
     * @return the team with more influence
     */
    protected Team teamWithMoreInfluence(Map<Team, Integer> influence){
        return teamWithMoreTowers(influence);
    }

    /**
     * Checks if the cards are ended
     * @return number of card left
     */
    private int remainingCards(){
        int cardsLeft = 0;
        for(Castle castle : castleMap.values()) cardsLeft += (int) Arrays.stream(castle.getCards()).filter(card -> card != null && !card).count();
        return cardsLeft;
    }

    /**
     * Checks if the game is won after a player turn
     * @return the winner team
     */
    private Team isWinningPosition(){
        Map <Team, Integer> nTowers = sumTowers();
        Team winner = null;
        if(islandList.size()<=3){
            winner = teamWithMoreTowers(nTowers);
        }
        else{
            for(Team t : Team.values()){
                if(nTowers.get(t) >= 8) winner = t;
            }
        }
        return winner;
    }

    /**
     * Checks if the game is won after all players turn when cards or student in the bag are finished
     * @return the winner team
     */
    public Team isWonByResources(){
        if(bag.remainingStudents() == 0 || remainingCards() == 0){
            Map <Team, Integer> nTowers = sumTowers();
            return teamWithMoreTowers(nTowers);
        }
        else return null;
    }

    /**
     * Joins the islands and put the new island into the list
     */
    public void joinIslands(@NotNull List<Island> islandList){
        int firstIslandIndex = this.islandList.indexOf(islandList.get(0));
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
     * on the current island mother nature lands on.
     * Checks if nearby islands have the same owner and possibly joins them.
     * Checks if someone won the game after an island is conquered
     * @param move number of steps forward of mother nature
     */
    public void moveMotherNature(int move) {
        if (motherNaturePosition + move / islandList.size() >= 1) motherNaturePosition += move - islandList.size();
        else motherNaturePosition += move;
        conquerIsland(islandList.get(motherNaturePosition));
    }

    /**
     * Calculates influence on island and sets a new owner if possible
     * @param island the current island mother nature is on
     */
    protected void conquerIsland(@NotNull Island island) {
        Map<Team, Integer> influence = island.calculateInfluence(professorsMap);
        Team t = teamWithMoreInfluence(influence);
        if (t != null) island.setOwnership(t);
        checkJoinIsland(island);
    }

    /**
     * Checks if neighbouring island have the same owner and joins them to the current island
     * @param island the island mother nature is on
     */
    protected void checkJoinIsland(Island island){
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

    public String getCurrentPlayer(){return turn.getCurrentPlayer();}

    public List<Cloud> getCloudList() {return new ArrayList<>(cloudList);}

    public List<Island> getIslandList() {return new ArrayList<>(islandList);}

    public Map<String, Castle> getCastleMap() {return new HashMap<>(castleMap);}

    public Castle getCastle(String playerID){//TODO: make return only a copy
        return castleMap.get(playerID);
    }

    public Map<Color, Team> getProfessorsMap() {//TODO: make return only a copy
        return professorsMap;
    }

}
