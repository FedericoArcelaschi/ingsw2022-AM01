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

    public List<Color> removeWR(Set<Color> c){
        List<Color> removedStudents = new ArrayList<>();
        try {
            int[] arr = new int[c.size()];
            for(int i : arr){
                i = 0;
            }
            for(int i = 0; i<c.size(); i++) { //a ogni elemento dell'array arr associo un colore: lo rimuovo dalla WR
                for (Color col : c) {         //se e solo se l'elemento dell'array corrispondente al colore è pari a 0
                    if (i == 0) {                //(prima iterazione).
                        waitingRoom.remove(col);
                        removedStudents.add(col);
                        i++;
                    }
                }
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return removedStudents;
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
