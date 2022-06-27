package it.polimi.ingsw.communication.modelData.expertMode;

import it.polimi.ingsw.communication.modelData.IslandData;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;

import java.util.EnumMap;

public class ExpertIslandData extends IslandData {

    private final boolean isBlocked;

    public ExpertIslandData(Team ownership, EnumMap<StudentColor, Integer> students, int islandSize, boolean isBlocked) {
        super(ownership, students, islandSize);
        this.isBlocked = isBlocked;
    }
    //To add: Blocked island.

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

        s.append(isBlocked ? ", this island is blocked!" : "");

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
}
