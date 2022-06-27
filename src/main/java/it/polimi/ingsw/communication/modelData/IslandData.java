package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;

import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

public class IslandData {
    protected final Team ownership;
    protected final EnumMap<StudentColor, Integer> students;
    protected final int islandSize;

    public IslandData(Team ownership, EnumMap<StudentColor, Integer> students, int islandSize) {
        this.ownership = ownership;
        this.students = students;
        this.islandSize = islandSize;
    }

    public Team getOwnership() {
        return ownership;
    }

    public EnumMap<StudentColor, Integer> getStudents() {
        return students;
    }

    public int getIslandSize() {
        return islandSize;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (StudentColor student : students.keySet()) {
            if (students.get(student) > 0) {
                s.append(" ")
                        .append(student.toStringColored())
                        .append(" ")
                        .append(students.get(student))
                        .append(", ");
            }
        }
        if (ownership == null) {
            s.append("no towers");
        } else {
            s.append(islandSize)
                    .append(" ")
                    .append(ownership)
                    .append((islandSize > 1) ? " towers" : " tower");
        }
        return s.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IslandData that)) return false;
        if (islandSize != that.islandSize) return false;
        if (ownership != that.ownership) return false;
        return students.equals(that.students);
    }

    public Team ownership() {
        return ownership;
    }

    public EnumMap<StudentColor, Integer> students() {
        return students;
    }

    public int islandSize() {
        return islandSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownership, students, islandSize);
    }

    public boolean isBlocked() {
        return false;
    }
}
