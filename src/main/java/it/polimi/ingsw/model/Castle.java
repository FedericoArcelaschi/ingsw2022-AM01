package it.polimi.ingsw.model;

import java.util.*;

public class Castle {
    private List<Color> waitingRoom;
    private Map<Color, Integer> diningRoom;
    private boolean[] cardsPlayed;
    private int lastCardPlayed;
    private Team towerColor;

    public Castle(String PLayerID, Team team){
        this.waitingRoom = new ArrayList<>();
        this.diningRoom = new HashMap<>();
        this.cardsPlayed = new boolean[10];
        this.towerColor = team;
    }

    public boolean addStudentWR(List<Color> c){
        try {
            waitingRoom.addAll(c);
            return true;
        }catch(ArrayIndexOutOfBoundsException a){
            a.printStackTrace();
            return false;
        }
    }

    public boolean addStudentDR(List<Color> c){
        try {
            for (Color col : c) {
                diningRoom.put(col, diningRoom.get(col) + 1);
            }
            return true;
        }catch(NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeWR(List<Color> c) {
        try {
            for (Color col : c) {
                waitingRoom.remove(col);
            }
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean playCard(int i){
        try{
            cardsPlayed[i] = false;
            return true;
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            return false;
        }
    }

    public Team getTeam(){
        return towerColor;
    }

    //MANCA IL toJson!!!!!!!!!!
}
