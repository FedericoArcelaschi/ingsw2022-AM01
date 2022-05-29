package it.polimi.ingsw.model.baseLogic;

import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.baseLogic.interfaces.StudentPlaces;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Castle implements StudentPlaces {

    protected final List<StudentColor> waitingRoom;
    protected final Map<StudentColor, Integer> diningRoom;
    protected final List<Card> deck;
    protected Card lastPlayedCard;
    protected final Team towerColor;
    protected final int waitingRoomSize;
    //constants
    private static final int diningRoomSizePerColor = 9;
    private static final int waitingRoomSize2Players = 7;
    private static final int waitingRoomSize3Players = 9;
    private static final int numberOfCards = 10;

    public Castle(Team team, int nPlayer, List<StudentColor> students) {
        if (nPlayer == 3) this.waitingRoomSize = waitingRoomSize3Players;
        else this.waitingRoomSize = waitingRoomSize2Players;
        this.waitingRoom = new ArrayList<>(students);
        this.diningRoom = new HashMap<>();
        for (StudentColor c : StudentColor.values()) {
            diningRoom.put(c, 0);
        }
        this.deck = new ArrayList<>();
        this.towerColor = team;
        this.lastPlayedCard = null;
        for (int i = 1; i <= numberOfCards; i++) deck.add(new Card(i, (i + 1) / 2, true));
    }

    /**
     * Add a list of students to the waiting room
     *
     * @param students The list of students to add to the waiting room.
     * @return boolean that checks whether the operation was successful or not.
     */
    public boolean addStudentsInWaitingRoom(List<StudentColor> students) throws TooManyStudentsException {
        int x;
        if (waitingRoom.isEmpty())
            x = 0;
        else
            x = waitingRoom.size();
        if (students.size() + x > waitingRoomSize)
            throw new TooManyStudentsException("there are already " + waitingRoomSize +
                    " students in the waiting room");
        waitingRoom.addAll(students);
        return true;
    }

    /**
     * Adds a single student to the dining room
     *
     * @param student color
     */
    public void addStudentInDiningRoom(StudentColor student) throws TooManyStudentsException {
        if (diningRoom.get(student) == diningRoomSizePerColor) {
            throw new TooManyStudentsException("THere are already " + diningRoomSizePerColor + " in your diningRoom");
        }
        diningRoom.put(student, diningRoom.get(student) + 1);
    }

    /**
     * Adds a list of students to the dining room
     *
     * @param students – The list of students to add to the dining room.
     */
    public void addStudentsInDiningRoom(List<StudentColor> students) throws TooManyStudentsException {
        for (StudentColor c : students) {
            addStudentInDiningRoom(c);
        }
    }

    /**
     * Removes a list of students from the waiting room.
     *
     * @param students – The list of students to remove.
     * @throws NoSuchStudentException Exception thrown if the waiting room doesn't contain all the students in c.
     */
    public void removeStudentsFromWaitingRoom(@NotNull List<StudentColor> students) throws NoSuchStudentException {
        if (students.size() > waitingRoom.size()) {
            if (students.size() == 1)
                throw new NoSuchStudentException("There isn't any student in the waiting room");
            throw new NoSuchStudentException("There aren't " + students.size() + " students in waiting room. " +
                    "There are only " + waitingRoom.size() + " students.");
        }
        if (!waitingRoom.containsAll(students)) {
            List<StudentColor> temp = new ArrayList<>(waitingRoom);
            for (StudentColor s : students)
                if (!temp.remove(s))
                    throw new NoSuchStudentException("StudentCharacter " + s + " not in the WaitingRoom");
        }
        for (StudentColor col : students) {
            waitingRoom.remove(col);
        }
    }

    /**
     * Method that allows the player to play the card.
     *
     * @param i priority of the card
     * @return true if the card was played correctly
     */
    public boolean playCard(int i) {
        if (i < 1 || i > 10) throw new IllegalArgumentException();
        Card play = deck.get(i - 1);
        if (play.isAvailable()) {
            play.setAvailable(false);
            lastPlayedCard = play;
            return true;
        } else
            return false;
    }

    public boolean equals(Castle c) {
        return this.waitingRoom.equals(c.waitingRoom) && this.diningRoom.equals(c.diningRoom)
                && this.towerColor == c.towerColor;
    }

    public List<StudentColor> getWaitingRoom() {
        return new ArrayList<>(waitingRoom);
    }

    public Map<StudentColor, Integer> getDiningRoom() {
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