package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    int motherNature = 0;
    Bag bag = new Bag(24);
    List<Cloud> cloudList = new ArrayList<>();
    List<Island> islandList = new ArrayList<>();
    Map<String, Castle> castleMap = new HashMap<>();
    Map<Color, Castle> professorMap; //Map<professorColor, Castle> to handle professors assignment, null if no castle has the professor

    public Board(String playerID1, String playerID2){
        for(int i=0; i<2;i++)   cloudList.add(new Cloud(bag,4));
        castleMap.put(playerID1, new Castle(playerID1, Team.WHITE));
        castleMap.put(playerID2, new Castle(playerID2, Team.BLACK));
        setupIslands();
    }

    public Board(String playerID1, String playerID2, String playerID3){
        for(int i=0; i<3;i++)   cloudList.add(new Cloud(bag,3));
        castleMap.put(playerID1, new Castle(playerID1, Team.WHITE));
        castleMap.put(playerID2, new Castle(playerID2, Team.BLACK));
        castleMap.put(playerID3, new Castle(playerID3, Team.GREY));
        setupIslands();
    }

    public Board(String playerID1, String playerID2, String playerID3, String playerID4){
        for(int i=0; i<4;i++)   cloudList.add(new Cloud(bag,4));
        castleMap.put(playerID1, new Castle(playerID1, Team.WHITE));
        castleMap.put(playerID2, new Castle(playerID2, Team.BLACK));
        castleMap.put(playerID3, new Castle(playerID3, Team.WHITE));
        castleMap.put(playerID4, new Castle(playerID4, Team.BLACK));
        setupIslands();
    }

    private boolean setupIslands(){
        for(int i=0; i<12; i++){
            if(i%6 == 0) islandList.add(new Island());
            else islandList.add(new Island(bag.extract()));
        }
        return true;
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

    public boolean playCard(String PlayerID, int card){//true if the move is legal, false otherwise
        Castle castle = castleMap.get(PlayerID);
        return castle.playCard(card);
    }
}
