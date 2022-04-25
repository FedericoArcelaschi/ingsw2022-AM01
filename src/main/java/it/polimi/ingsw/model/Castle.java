package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;

import java.util.*;

public class Castle {
    private final List<Color> waitingRoom;
    protected final Map<Color, Integer> diningRoom;
    private final List<Card> deck;
    private Card lastPlayedCard;
    private final Team towerColor;
    private final int waitingRoomSize;
    //constants
    private static final int diningRoomSize = 9;
    private static final int waitingRoomSize2Players = 7;
    private static final int waitingRoomSize3Players = 9;
    private static final int numberOfCards = 10;

    public Castle(Team team, int nPlayer, List<Color> students) {
        if(nPlayer == 3) this.waitingRoomSize = waitingRoomSize3Players;
        else this.waitingRoomSize = waitingRoomSize2Players;
        this.waitingRoom = new ArrayList<>(students);
        this.diningRoom = new HashMap<>();
        for(Color c : Color.values()){
            diningRoom.put(c, 0);
        }
        this.deck = new ArrayList<>();
        this.towerColor = team;
        this.lastPlayedCard = null;
        for(int i = 1; i <= 10; i++) deck.add(new Card(i,(i+1)/2,true));
    }

    /**
     * Add a list of students to the waiting room
     * @param students The list of students to add to the waiting room.
     * @return boolean that checks whether the operation was successful or not.
     */
    public boolean addStudentsInWaitingRoom(List<Color> students) throws TooManyStudentsException {
        if(students.size() + waitingRoom.size() > waitingRoomSize) {
            throw new TooManyStudentsException();
        }
        waitingRoom.addAll(students);
        return true;
    }

    /**
     * Adds a single student to the dining room
     * @param student color
     */
    public void addStudentInDiningRoom(Color student) throws TooManyStudentsException {
        if (diningRoom.get(student) == diningRoomSize) {
            throw new TooManyStudentsException();
        }
        diningRoom.put(student, diningRoom.get(student) + 1);
    }

    /**
     * Adds a list of students to the dining room
     * @param students – The list of students to add to the dining room.
     */
    public void addStudentsInDiningRoom(List<Color> students)throws TooManyStudentsException {
        for (Color c : students) {
            addStudentInDiningRoom(c);
        }
    }

    /**
     * Removes a list of students from the waiting room.
     * @param students – The list of students to remove.
     * @throws NoSuchStudentException Exception thrown if the waiting room doesn't contain all the students in c.
     */
    public void removeStudentsFromWaitingRoom(List<Color> students) throws NoSuchStudentException {
        if(!new HashSet<>(waitingRoom).containsAll(students) || students.size() > waitingRoomSize){
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
    }

    /**
     * Method that allows the player to play the card.
     * @param i priority of the card
     * @return true if the card was played correctly
     */
    public boolean playCard(int i) {
        if(i<1 || i>10) throw new IllegalArgumentException();
        Card play = deck.get(i-1);
        if(play.isAvailable()){
            play.setAvailable(false);
            lastPlayedCard = play;
            return true;
        }
        else return false;
    }


    public List<Color> getWaitingRoom(){
        return new ArrayList<>(waitingRoom);
    }

    public Map<Color, Integer> getDiningRoom() {
        return new HashMap<>(diningRoom);
    }

    public Team getTeam() {
        return towerColor;
    }

    public Card getLastCardPlayed() {
        return lastPlayedCard;
    }

    public List<Card> getDeck() {
        return new ArrayList<>(deck);
    }

    @Override
    public String toString() {
        return "Castle{" +
                "waitingRoom=" + waitingRoom +
                ", diningRoom=" + diningRoom +
                ", deck=" + deck +
                ", lastPlayedCard=" + lastPlayedCard +
                ", towerColor=" + towerColor +
                '}';
    }
}
