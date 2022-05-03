package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;

import java.util.Map;
import java.util.Objects;

public final class IslandData {
    private final Team ownership;
    private final Map<Color, Integer> students;

    public IslandData(
            Team ownership,
            Map<Color, Integer> students
    ) {
        this.ownership = ownership;
        this.students = students;
    }

    public IslandData(Island island) {
        this(island.getOwnership(), island.getStudents());
    }

    public Team ownership() {
        return ownership;
    }

    public Map<Color, Integer> students() {
        return students;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (IslandData) obj;
        return Objects.equals(this.ownership, that.ownership) &&
                Objects.equals(this.students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownership, students);
    }

    @Override
    public String toString() {
        return "IslandData[" +
                "ownership=" + ownership + ", " +
                "students=" + students + ']';
    }

}
