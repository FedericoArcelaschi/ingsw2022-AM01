package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;

import java.util.Map;
import java.util.Objects;

public final class IslandData {
    private final Team ownership;
    private final Map<Color, Integer> students;
    private final int nIslands;

    public IslandData(
            Team ownership,
            Map<Color, Integer> students,
            int nIslands
    ) {
        this.ownership = ownership;
        this.students = students;
        this.nIslands = nIslands;
    }

    public IslandData(Island island) {
        this(island.getOwnership(), island.getStudents(), island.getIslandNumber());
    }

    public Team ownership() {
        return ownership;
    }

    public Map<Color, Integer> students() {
        return students;
    }

    public int nIslands() {
        return nIslands;
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
        StringBuilder s = new StringBuilder();
        for (Color key: students.keySet()) {
            s.append(key.str).append(" ").append(students.get(key)).append(", ");
        }
        if(ownership == null){
            s.append("0 towers");
        }
        else{
            //TODO: change placeholder
            s.append(nIslands).append(" ").append(ownership).append(nIslands>1?" towers":" tower");
        }
        return s.toString();
    }

}
