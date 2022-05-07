package it.polimi.ingsw.model;

import it.polimi.ingsw.model.expert.boardInterfaces.StudentPlaces;

import java.util.HashMap;
import java.util.Map;

public class Island implements StudentPlaces {
    private final Map<Color, Integer> students;
    private Team ownership;

    public Island(Color student) {
        this.students = new HashMap<>();
        for (Color c : Color.values()) {
            if (c == student) students.put(c, 1);
            else students.put(c, 0);
        }
        this.ownership = null;
    }

    public Island() {
        this.students = new HashMap<>();
        for (Color c : Color.values())
            students.put(c, 0);
        this.ownership = null;
    }

    public Team getOwnership() {
        return ownership;
    }

    public int getIslandNumber() {
        return 1;
    }

    public Map<Color, Integer> getStudents() {
        return new HashMap<>(students);
    }

    public Island setOwnership(Team ownership) {
        this.ownership = ownership;
        return this;
    }

    /**
     * add a student to the island
     *
     * @param c color of the student to add
     * @return check boolean
     */
    public void addStudent(Color c) {
        students.put(c, students.get(c) + 1); //In the value of the color c, I'm putting the previous number of students + 1.
    }

    /**
     * Adds multiple students to the island
     *
     * @param s Map that contains how many students per color to add
     * @return check boolean
     */
    public boolean addStudent(Map<Color, Integer> s) {
        for (Color c : s.keySet()) {
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

//For expert mode: would like to remove those from here TODO
    @Override
    public void adds(Color student, int place) throws IllegalAccessException {
        throw new IllegalAccessException("only for Expert Mode");
    }

    @Override
    public void removes(Color student, int place) throws IllegalAccessException {
        throw new IllegalAccessException("only for Expert Mode");
    }
}
