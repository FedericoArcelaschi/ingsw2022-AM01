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
        castleMap.put(playerID1, new Castle(playerID1, Team.WHITE, nPlayer));
        castleMap.put(playerID2, new Castle(playerID2, Team.BLACK, nPlayer));
        setupIslands();
    }

    public Board(String playerID1, String playerID2, String playerID3){
        nPlayer = 3;
        setupClouds();
        castleMap.put(playerID1, new Castle(playerID1, Team.WHITE, nPlayer));
        castleMap.put(playerID2, new Castle(playerID2, Team.BLACK, nPlayer));
        castleMap.put(playerID3, new Castle(playerID3, Team.GREY, nPlayer));
        setupIslands();
    }

    public Board(String playerID1, String playerID2, String playerID3, String playerID4){
        nPlayer = 4;
        setupClouds();
        castleMap.put(playerID1, new Castle(playerID1, Team.WHITE, nPlayer));
        castleMap.put(playerID2, new Castle(playerID2, Team.BLACK, nPlayer));
        castleMap.put(playerID3, new Castle(playerID3, Team.WHITE, nPlayer));
        castleMap.put(playerID4, new Castle(playerID4, Team.BLACK, nPlayer));
        setupIslands();
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
        for(int i=0; i<12; i++){
            if(i%6 == 0) islandList.add(new Island());
            else islandList.add(new Island(bag.extract()));
        }
    }

    public boolean resetClouds(){
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

    public Team isWinningPosition(){
        Map <Team, Integer> nTowers = new HashMap<>();
        int max;
        Team winner = null;
        for(Team t : Team.values()){
            nTowers.put(t,0);
        }
        for(Island i : islandList){
            if(i.getOwnership() != null){
                nTowers.replace(i.getOwnership(),nTowers.get(i.getOwnership()) + i.getIslandNumber());
            }
        }
        if(islandList.size()<=3){
            max = nTowers.get(Team.WHITE);
            winner = Team.WHITE;
            if(nTowers.get(Team.BLACK) > max){
                max = nTowers.get(Team.BLACK);
                winner = Team.BLACK;
            }
            if(nTowers.get(Team.GREY) > max){
                winner = Team.GREY;
            }
        }
        else{
            for(Team t : Team.values()){
                if(nTowers.get(t) >= 8) winner = t;
            }
        }
        return winner;
    }
}
