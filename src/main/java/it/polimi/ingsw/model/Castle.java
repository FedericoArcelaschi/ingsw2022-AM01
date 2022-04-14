package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;

import java.util.*;

public class Castle {
    private final List<Color> waitingRoom;
    protected final Map<Color, Integer> diningRoom;
    private final List<Card> cards;
    private Card lastPlayedCard;
    private final Team towerColor;
    private final int waitingRoomSize;
    //constants
    private static final int diningRoomSize = 9;
    private static final int waitingRoomSize2Players = 9;
    private static final int waitingRoomSize3Players = 7;

    public Castle(String PLayerID, Team team, int nPlayer, List<Color> students){
        if(nPlayer == 3) this.waitingRoomSize = waitingRoomSize3Players;
        else this.waitingRoomSize = waitingRoomSize2Players;
        this.waitingRoom = new ArrayList<>(students);
        this.diningRoom = new HashMap<>();
        for(Color c : Color.values()){
            diningRoom.put(c, 0);
        }
        this.cards = new ArrayList<>();
        this.towerColor = team;
        this.lastPlayedCard = null;
        for(int i=1; i<=10; i++) cards.add(new Card(i,(i+1)/2));
    }


    public List<Color> getWaitingRoom(){
        return new ArrayList<>(waitingRoom);
    }

    public Map<Color, Integer> getDiningRoom(){
        return new HashMap<>(diningRoom);
    }

    public Team getTeam(){
        return towerColor;
    }

    public Card getLastCardPlayed(){
        return lastPlayedCard;
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    /**
     * Add a list of students to the waiting room
     * @param students The list of students to add to the waiting room.
     * @return boolean that checks whether the operation was successful or not.
     */
    public boolean addStudentsInWaitingRoom(List<Color> students) throws TooManyStudentsException{
        if(students.size()+waitingRoom.size() > waitingRoomSize) {
            System.out.println(getWaitingRoom());
            System.out.println(students);
            System.out.println(waitingRoomSize);
            throw new TooManyStudentsException();
        }
        waitingRoom.addAll(students);
        return true;
    }

    /**
     * add a single student to the dining room
     * @param student color
     * @return if the student is added correctly
     */
    public boolean addStudentInDiningRoom(Color student) throws TooManyStudentsException{
        if (diningRoom.get(student) == diningRoomSize) {
            throw new TooManyStudentsException();
        }
        diningRoom.put(student, diningRoom.get(student) + 1);
        return true;
    }

    /**
     * Add a list of students to the dining room
     * @param students – The list of students to add to the dining room.
     * @return boolean that checks whether the operation was successful.
     */
    public boolean addStudentsInDiningRoom(List<Color> students)throws TooManyStudentsException {
        for (Color student : students) {
            if(!addStudentInDiningRoom(student))
                return false;
        }
        return true;
    }

    /**
     * Removes a list of students from the waiting room.
     * @param students – The list of students to remove.
     * @return boolean, true if method was successful, false if it wasn't
     * @throws NoSuchStudentException Exception thrown if the waiting room doesn't contain all the students in c.
     */
    public boolean removeStudentsFromWaitingRoom(List<Color> students) throws NoSuchStudentException{
        if(!waitingRoom.containsAll(students) || students.size()>waitingRoomSize){
            throw new NoSuchStudentException();
        }
        else{
            List<Color> temp = getWaitingRoom();
            for(Color s : students){
                if(!temp.remove(s)){
                    throw new NoSuchStudentException();
                }
            }
        }
        for (Color col : students) {
            waitingRoom.remove(col);
        }
        return true;
    }



    /**Method that allows the player to play the card.
     * @param i priority of the card
     * @return true if the card was played correctly
     */
    public boolean playCard(int i){
        Card c;
        c = cards.get(i-1);
        if(c.isPlayed()) return false;
        else {
            c.setPlayed(true);
            lastPlayedCard = c;
            return true;
        }
    }
}
