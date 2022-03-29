package it.polimi.ingsw.model;

import java.util.*;

public class Castle {
    private List<Color> waitingRoom;
    private Map<Color, Integer> diningRoom;
    private List<Card> cards;
    private Card lastCardPlayed;
    private final Team towerColor;
    private final int WRSize;

    public Castle(String PLayerID, Team team, int nPlayer){
        if(nPlayer == 3) WRSize = 7;
        else WRSize = 9;
        this.waitingRoom = new ArrayList<>();
        this.diningRoom = new HashMap<>();
        this.cards = new ArrayList<>();
        this.towerColor = team;
        lastCardPlayed = null;
        for(int i=1; i<=10; i++) cards.add(new Card(i,(i+1)/2));
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

    public boolean playCard(int i){ // con i mi riferisco alla priority della carta non alla sua posizione nell'arrayList
        Card c;
        try{
            c = cards.get(i-1);
            if(c.isPlayed()) return false;
            else {
                c.setPlayed(false);
                lastCardPlayed = c;
                return true;
            }
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            return false;
        }
    }

    public Team getTeam(){
        return towerColor;
    }
}
