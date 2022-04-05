package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import java.util.*;

public class Castle {
    private List<Color> waitingRoom;
    protected Map<Color, Integer> diningRoom;
    private List<Card> cards;
    private Card lastCardPlayed;
    private final Team towerColor;
    private final int WRSize;

    public Castle(String PLayerID, Team team, int nPlayer) {
        if (nPlayer == 3) WRSize = 7;
        else WRSize = 9;
        this.waitingRoom = new ArrayList<>();
        this.diningRoom = new HashMap<>();
        this.cards = new ArrayList<>();
        this.towerColor = team;
        lastCardPlayed = null;
        for (int i = 1; i <= 10; i++) cards.add(new Card(i, (i + 1) / 2));
    }

    public List<Color> getWaitingRoom() {
        return new ArrayList<>(waitingRoom);
    }

    public Map<Color, Integer> getDiningRoom() {
        return new HashMap<>(diningRoom);
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public Card getLastCardPlayed() {
        return lastCardPlayed;
    }

    /**
     * Add a list of students to the waiting room
     * @param c
     * @return boolean that checks whether or not the operation was succesful.
     */

    public boolean addStudentWR (List < Color > c) {
        waitingRoom.addAll(c);
        return true;
    }

    public boolean addStudentDR(List<Color> c) {
        try {
            for (Color col : c) {
                if (diningRoom.containsKey(col))
                    diningRoom.put(col, diningRoom.get(col) + 1);
                else diningRoom.put(col, 1);
            }
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * removes a list of students from the waiting room.
     * @param c
     * @return boolean, true if method was successful, false if it wasn't
     * @throws NoSuchStudentException TODO
     */
    public boolean removeWR (List < Color > c) throws NoSuchStudentException {
        if (!waitingRoom.containsAll(c)) {
            throw new NoSuchStudentException();
        }
        for (Color col : c) {
            waitingRoom.remove(col);
        }
        return true;
    }

    /**
     *
     * @param i: number on the top left of the card (priority)
     * @return
     */
    public boolean playCard(int i){
        Card c;
        try{
            c = cards.get(i-1);
            if(c.isPlayed())
                return false;
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

    /**
     * Gives the remaining planification Cards.
     * @return
     */
    public List<Card> remainingCards() {
        List<Card> cards=  new ArrayList<>(this.cards);
        return cards;
    }

    public Team getTeam() {
        return towerColor;
    }
}