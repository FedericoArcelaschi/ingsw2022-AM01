package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;

import java.util.*;

public class Castle {
    private final List<Color> waitingRoom;
    protected final Map<Color, Integer> diningRoom;
    private final Boolean[] cards;
    private int lastPlayedCard;
    private final Team towerColor;
    private final int waitingRoomSizePerColor;
    //constants
    private static final int diningRoomSize = 10;
    private static final int waitingRoomSize2Players = 9;
    private static final int waitingRoomSize3Players = 7;
    private static final int numberOfCards = 10;

    public Castle(String PLayerID, Team team, int nPlayer, List<Color> students){
        if(nPlayer == 3) this.waitingRoomSizePerColor = waitingRoomSize3Players;
        else this.waitingRoomSizePerColor = waitingRoomSize2Players;
        this.waitingRoom = new ArrayList<>(students);
        this.diningRoom = new HashMap<>();
        for(Color c : Color.values()){
            diningRoom.put(c, 0);
        }
        this.cards = new Boolean[numberOfCards];
        this.towerColor = team;
        this.lastPlayedCard = -1;
        for(int i=0; i<10; i++) cards[i] = false;
    }

    /**
     * Add a list of students to the waiting room
     * @param students The list of students to add to the waiting room.
     * @return boolean that checks whether the operation was successful or not.
     */
    public boolean addStudentsInWaitingRoom(List<Color> students) throws TooManyStudentsException{
        if(students.size() + waitingRoom.size() > waitingRoomSizePerColor) {
            throw new TooManyStudentsException("there are already " + waitingRoomSizePerColor + " students in the waiting room");
        }
        waitingRoom.addAll(students);
        return true;
    }

    /**
     * Adds a single student to the dining room
     * @param student color
     */
    public void addStudentInDiningRoom(Color student) throws TooManyStudentsException{
        if (diningRoom.get(student) == diningRoomSize) {
            throw new TooManyStudentsException("There are alredy 10 students of" + student + "color in the Dining Room");
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
    public void removeStudentsFromWaitingRoom(List<Color> students) throws NoSuchStudentException{
        List<Color> temp = getWaitingRoom();
        for(Color s : students){
            if(!temp.remove(s)){
                throw new NoSuchStudentException("Student " + s + " not in the WaitingRoom");
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
    public boolean playCard(int i){
        if(cards[i-1]) return false;
        else {
            cards[i-1] = true;
            lastPlayedCard = i;
            return true;
        }
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

    public int getLastCardPlayed(){
        return lastPlayedCard;
    }

    public Boolean[] getCards() {
        return Arrays.copyOf(cards, cards.length);
    }

}
