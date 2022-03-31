package it.polimi.ingsw.model;


import java.util.*;

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

    private void setupClouds(){
        if(nPlayer == 3) {
            for (int i = 0; i < nPlayer; i++) cloudList.add(new Cloud(bag, 3));
        }
        else{
            for(int i=0; i<nPlayer;i++)   cloudList.add(new Cloud(bag,4));
        }
    }

    private void setupIslands(){
        List<Color> s = bag.extractForSetup();
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

    public boolean refillClouds(){
        for(Cloud c: cloudList){
            if (!c.refill()) return false;
        }
        return true;
    }

    public boolean chooseCloud(String PlayerID, int cloudID){
        Castle castle = castleMap.get(PlayerID);
        Cloud cloud = cloudList.get(cloudID);

        return castle.addStudentWR(cloud.choose());
    }

    public boolean moveStudentToDR(String PlayerID, List<Color> students){
        Castle castle = castleMap.get(PlayerID);
        if(castle.removeWR(students)) return false;
        return castle.addStudentDR(students);
    }

    public boolean moveStudentToIsland(String PlayerID, int islandNumber, List<Color> students){
        if(castleMap.get(PlayerID).removeWR(students)) return false;
        for(Color c : students){
            islandList.get(islandNumber).addStudent(c);
        }
        return true;
    }

    public boolean playCard(String PlayerID, int card){//true if the move is legal, false otherwise
        Castle castle = castleMap.get(PlayerID);
        return castle.playCard(card);
    }

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

    private Team whoHasMoreTowers(Map<Team,Integer> nTowers){
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

    private int remainingCards(){
        int r = 0;
        for(Castle c : castleMap.values()) r += c.remainingCards().size();
        return r;
    }

    public Team isWinningPosition(){
        Map <Team, Integer> nTowers = sumTowers();
        Team winner = null;
        if(islandList.size()<=3){
            winner = whoHasMoreTowers(nTowers);
        }
        else{
            for(Team t : Team.values()){
                if(nTowers.get(t) >= 8) winner = t;
            }
        }
        return winner;
    }

    public Team isWonByResources(){
        if(bag.remainingStudents() == 0 || remainingCards() == 0){
            Map <Team, Integer> nTowers = sumTowers();
            return whoHasMoreTowers(nTowers);
        }
        else return null;
    }
}
