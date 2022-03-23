package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Castle {
    private List waitingRoom;
    private Map diningRoom;
    private boolean cardsPlayed[];
    private int lastCardPlayed;
    private Team towerColor;

    public Castle(String PLayerID, Team team){
        this.waitingRoom = new ArrayList<Color>();
        this.diningRoom = new HashMap<Color, Integer>();
        this.cardsPlayed = new boolean[10];
        this.towerColor = team;
    }

    public boolean addStudent(List<Color> c){
        try {
            waitingRoom.addAll(c); //aggiungi ramo try except
            return true;
        }catch(ArrayIndexOutOfBoundsException a){
            a.printStackTrace();
            return false;
        }
    }

    public boolean removeWR(List<Color> c){ //remove students from waiting room
        for(Color col : waitingRoom){

        }
    }

    public boolean playCard(int i){

    }

    public Team getTeam(){
        return towerColor;
    }
}
