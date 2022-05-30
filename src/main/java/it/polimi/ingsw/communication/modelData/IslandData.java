package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.model.baseLogic.Team;

import java.util.Map;
import java.util.Objects;

public class IslandData {
    private final Team ownership;
    private final Map<StudentColor, Integer> students;
    private final int islandSize;

    public IslandData(Team ownership, Map<StudentColor, Integer> students, int nIslands) {
        this.ownership = ownership;
        this.students = students;
        this.islandSize = nIslands;
    }

    public Team getOwnership() {
        return ownership;
    }

    public Map<StudentColor, Integer> getStudents() {
        return students;
    }

    public int getIslandSize() {
        return islandSize;
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
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (StudentColor key: students.keySet()) {
            s.append(key).append(" ").append(students.get(key)).append(", ");
        }
        if(ownership == null){
            s.append("0 towers");
        }
        else{
            //TODO: change placeholder
            s       .append(islandSize)
                    .append(" ")
                    .append(ownership)
                    .append((islandSize > 1) ? " towers":" tower");
        }
        return s.toString();
    }

}
