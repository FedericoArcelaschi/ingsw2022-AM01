package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.model.baseLogic.Team;

import java.util.HashMap;
import java.util.List;

public class IslandData {
    private final Team ownership;
    private final List<StudentColor> students;
    private final int islandSize;

    public IslandData(Team ownership, List<StudentColor> students, int nIslands) {
        this.ownership = ownership;
        this.students = students;
        this.islandSize = nIslands;
    }

    public Team getOwnership() {
        return ownership;
    }

    public List<StudentColor> getStudents() {
        return students;
    }

    public int getIslandSize() {
        return islandSize;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (StudentColor student: students) {
            s.append(" ").append(student).append(", ");
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
