package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;

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


    public List<Color> getWaitingRoom(){
        return waitingRoom;
    }

    public Map<Color, Integer> getDiningRoom(){
        return diningRoom;
    }

    public Team getTeam(){
        return towerColor;
    }

    public void setDiningRoom(Map<Color, Integer> diningRoom){
        this.diningRoom = diningRoom;
    }

    public void setWaitingRoom(List<Color> waitingRoom){
        this.waitingRoom = waitingRoom;
    }

    public Card getLastCardPlayed(){
        return lastCardPlayed;
    }

    public List<Card> getCards() {
        return cards;
    }

    /**
     * Add a list of students to the waiting room
     * @param c
     * @return boolean that checks whether or not the operation was succesful.
     */

    public boolean addStudentWR(List<Color> c){
        waitingRoom.addAll(c);
        return true;
    }
    /**
     * Add a list of students to the dining room
     * @param c
     * @return boolean that checks whether the operation was successful.
     */
    public boolean addStudentDR(List<Color> c){
        for (Color col : c) {
            diningRoom.put(col, diningRoom.get(col) + 1);
        }
        return true;
    }

    /**
     * removes a list of students from the waiting room.
     * @param c
     * @return boolean, true if method was successful, false if it wasn't
     * @throws NoSuchStudentException TODO
     */
    public boolean removeWR(List<Color> c) throws NoSuchStudentException{
        if(!waitingRoom.containsAll(c)){
            throw new NoSuchStudentException();
        }
        for (Color col : c) {
            waitingRoom.remove(col);
        }
        return true;
    }

    /**
     * Method that allows the player to play the card.
     * @param i
     * @return boolean
     */
    public boolean playCard(int i){ // con i mi riferisco alla priority della carta non alla sua posizione nell'arrayList
        Card c;

        c = cards.get(i-1);
        if(c.isPlayed()) return false;
        else {
            c.setPlayed(false);
            lastCardPlayed = c;
            return true;
        }
    }

}
