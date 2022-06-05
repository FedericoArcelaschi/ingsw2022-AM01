package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.server.model.baseLogic.interfaces.StudentPlaces;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Castle implements StudentPlaces {

    protected final List<StudentColor> waitingRoom;
    protected final EnumMap<StudentColor, Integer> diningRoom;
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
        this.diningRoom = new EnumMap<>(StudentColor.class);
        for (StudentColor color : StudentColor.values()) {
            diningRoom.put(color, 0);
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
            x = waitingRoom.size(); //FIXME: is needed?
        if (students.size() + x > waitingRoomSize)
            throw new TooManyStudentsException("there are already " + waitingRoomSize +
                    " students in the waiting room");
        waitingRoom.addAll(students);
        return true;
    }

    /**
     * Adds a single student to the dining room
     *
     * @param color color
     */
    public void addStudentInDiningRoom(StudentColor color) throws TooManyStudentsException {
        if (diningRoom.get(color) == diningRoomSizePerColor) {
            throw new TooManyStudentsException("There are already " + diningRoomSizePerColor + " " + color + " students  in your diningRoom");
        }
        diningRoom.put(color, diningRoom.get(color) + 1);
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

    public List<StudentColor> getWaitingRoom() {
        return new ArrayList<>(waitingRoom);
    }

    public EnumMap<StudentColor, Integer> getDiningRoom() {
        return new EnumMap<>(diningRoom);
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

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Castle) obj;
        return  this.diningRoom == that.diningRoom &&
                this.waitingRoom == that.waitingRoom &&
                this.deck == that.deck &&
                this.lastPlayedCard == that.lastPlayedCard &&
                this.towerColor == that.towerColor;
    }
}