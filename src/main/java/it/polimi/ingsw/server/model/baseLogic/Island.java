package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.baseLogic.interfaces.StudentPlaces;
import it.polimi.ingsw.server.model.exceptions.NotTheRightGameModeException;

import java.util.EnumMap;

public class Island implements StudentPlaces {

    private final EnumMap<StudentColor, Integer> students;
    private Team ownership;

    public Island(StudentColor student) {
        this.students = new EnumMap<>(StudentColor.class);
        for (StudentColor c : StudentColor.values()) {
            if (c == student) students.put(c, 1);
            else students.put(c, 0);
        }
        this.ownership = null;
    }

    public Island() {
        this.students = new EnumMap<>(StudentColor.class);
        for (StudentColor c : StudentColor.values())
            students.put(c, 0);
        this.ownership = null;
    }

    public Team getOwnership() {
        return ownership;
    }

    public int getIslandNumber() {
        return 1;
    }

    public EnumMap<StudentColor, Integer> getStudents() {
        return new EnumMap<>(students);
    }

    public Island setOwnership(Team ownership) {
        this.ownership = ownership;
        return this;
    }

    public boolean isBlocked() throws NotTheRightGameModeException {
        throw new NotTheRightGameModeException("You can't use this command now.");
    }

    /**
     * add a student to the island
     *
     * @param c color of the student to add
     * @return check boolean
     */
    public void addStudent(StudentColor c) {
        students.put(c, students.get(c) + 1); //In the value of the color c, I'm putting the previous number of students + 1.
    }

    /**
     * Adds multiple students to the island
     * Used for Achipelago constructor and expertIsland removes
     * @param s Map that contains how many students per color to add
     * @return check boolean
     */
    public boolean addStudent(EnumMap<StudentColor, Integer> s) {
        for (StudentColor c : s.keySet()) {
            students.put(c, students.get(c) + s.get(c));
        }
        return true;
    }

    @Override
    public String toString() {
        return "Island{" +
                "students=" + students +
                ", ownership=" + ownership +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Island) obj;
        return this.students.equals(that.students) &&
                this.ownership == that.ownership &&
                this.getIslandNumber() == that.getIslandNumber();
    }
}
