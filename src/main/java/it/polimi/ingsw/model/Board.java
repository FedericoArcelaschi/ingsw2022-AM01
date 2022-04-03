package it.polimi.ingsw.model;


import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private int motherNature = 0;
    private final int nPlayer;
    private final Bag bag = new Bag(24);
    private final List<Cloud> cloudList = new ArrayList<>();
    private final List<Island> islandList = new ArrayList<>();
    private final Map<String, Castle> castleMap = new HashMap<>();
    private Map<Color, Castle> professorMap; //Map<professorColor, Castle> to handle professors assignment, null if no castle has the professor

    public Board(String playerID1, String playerID2){
        nPlayer = 2;
        setupClouds();
        castleMap.put(playerID1, new Castle(playerID1, Team.WHITE, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID2, new Castle(playerID2, Team.BLACK, nPlayer, bag.multipleExtract(9)));
        setupIslands();
    }

    public Board(String playerID1, String playerID2, String playerID3){
        nPlayer = 3;
        setupClouds();
        castleMap.put(playerID1, new Castle(playerID1, Team.WHITE, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID2, new Castle(playerID2, Team.BLACK, nPlayer, bag.multipleExtract(7)));
        castleMap.put(playerID3, new Castle(playerID3, Team.GREY, nPlayer, bag.multipleExtract(7)));
        setupIslands();
    }

    public Board(String playerID1, String playerID2, String playerID3, String playerID4){
        nPlayer = 4;
        setupClouds();
        castleMap.put(playerID1, new Castle(playerID1, Team.WHITE, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID2, new Castle(playerID2, Team.BLACK, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID3, new Castle(playerID3, Team.WHITE, nPlayer, bag.multipleExtract(9)));
        castleMap.put(playerID4, new Castle(playerID4, Team.BLACK, nPlayer, bag.multipleExtract(9)));
        setupIslands();
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

    public Map<Color, Castle> getProfessorMap() {
        return new HashMap<>(professorMap);
    }

    /**
     * generate the clouds based on the nPlayer
     */

    private void setupClouds(){
        if(nPlayer == 3) {
            for (int i = 0; i < nPlayer; i++) cloudList.add(new Cloud(bag, 3));
        }
        else{
            for(int i=0; i<nPlayer;i++)   cloudList.add(new Cloud(bag,4));
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

    public boolean chooseCloud(String PlayerID, int cloudID){
        Castle castle = castleMap.get(PlayerID);
        Cloud cloud = cloudList.get(cloudID);

        return castle.addStudentWR(cloud.choose());
    }

    /**
     * move students form the waiting room to the dining room
     * @param PlayerID the id of the player that ask for this move
     * @param students a list of students you want to move
     * @return if the move is legal and played or not
     */

    public boolean moveStudentToDR(String PlayerID, List<Color> students){
        Castle castle = castleMap.get(PlayerID);
        if(castle.removeWR(students)) return false;
        return castle.addStudentDR(students);
    }

    /**
     *
     * @param PlayerID the id of the player that ask for this move
     * @param islandNumber the number of the island where you want to move the students
     * @param students a list of students you want to move
     * @return if the move is legal and played or not
     */

    public boolean moveStudentToIsland(String PlayerID, int islandNumber, List<Color> students){
        if(castleMap.get(PlayerID).removeWR(students)) return false;
        for(Color c : students){
            islandList.get(islandNumber).addStudent(c);
        }
        return true;
    }

    /**
     *
     * @param PlayerID  the id of the player that ask for this move
     * @param card the number of the card the player want to use
     * @return if the move is legal and played or not
     */

    public boolean playCard(String PlayerID, int card){//true if the move is legal, false otherwise
        Castle castle = castleMap.get(PlayerID);
        return castle.playCard(card);
    }

    /**
     *
     * @param PlayerID the id of the player that ask for this move
     * @return a list of not yet played card
     */

    public List<Card> getAviableCard(String PlayerID){
        Castle castle = castleMap.get(PlayerID);
        return castle.getCards().stream().filter(card -> !card.isPlayed()).collect(Collectors.toList());
    }

    /**
     *
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
     * calculate who has the highest number of towers in the map that he receives
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
     * use the teamWithMoreTowers algorithm to determine who has more influence
     * @param influence map that contains influence for each team
     * @return the team with more influence
     */

    private Team teamWithMoreInfluence(Map<Team,Integer> influence){
        return teamWithMoreTowers(influence);
    }

    /**
     * checks if the cards are ended
     * @return number of card left
     */

    private int remainingCards(){
        int cardsLeft = 0;
        for(Castle c : castleMap.values()) cardsLeft += c.remainingCards().size();
        return cardsLeft;
    }

    /**
     * checks if the game is won after a player turn
     * @return the winner team
     */

    public Team isWinningPosition(){
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
     * checks if the game is won after all players turn when cards or student in the bag are finished
     * @return the winner team
     */

    public Team isWonByResources(){
        if(bag.remainingStudents() == 0 || remainingCards() == 0){
            Map <Team, Integer> nTowers = sumTowers();
            return teamWithMoreTowers(nTowers);
        }
        else return null;
    }

    /*private boolean joinIslands(List<Island> il){ TODO: island join after archipelago is done
        return true;
    }*/

    /**
     * calculates influence and set new owner
     * checks if neighbouring island have the same owner and join
     * checks if someone won
     * @param move number of jumps forward motherNature have to do
     * @return the winner or null
     */

    public Team moveMotherNature(int move){
        if(motherNature+move/islandList.size() >= 1) motherNature += move-islandList.size();
        else motherNature += move;
        Island i = islandList.get(motherNature);
        //calculates influence and set new owner
        Map<Team, Integer> influence = i.calculateInfluence(professorMap);
        Team t = teamWithMoreInfluence(influence);
        if(t != null) i.setOwnership(t);

        //checks if neighbouring island have the same owner and join
        Island previous, next;
        List<Island> islandToJoin = new ArrayList<>();
        islandToJoin.add(i);
        if(motherNature==0){
            previous = islandList.get(islandList.size()-1);
            next = islandList.get(motherNature + 1);
        }
        else if(motherNature == islandList.size()) {
            previous = islandList.get(motherNature - 1);
            next = islandList.get(0);
        }
        else{
            previous = islandList.get(motherNature - 1);
            next = islandList.get(motherNature + 1);
        }
        if(previous.getOwnership() == i.getOwnership()) islandToJoin.add(previous);
        if(next.getOwnership() == i.getOwnership()) islandToJoin.add(next);
        //TODO join islands

        //checks if someone won and return the winner
        return isWinningPosition();
    }
}
