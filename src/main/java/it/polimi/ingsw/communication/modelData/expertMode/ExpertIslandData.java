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
        for (StudentColor student : getStudents().keySet()) {
            if (getStudents().get(student) > 0) {
                s.append(" ")
                        .append(student.toStringColored())
                        .append(" ")
                        .append(getStudents().get(student))
                        .append(", ");
            }
        }

        s.append(isBlocked ? "This island is blocked!" : "");

        if (getOwnership() == null) {
            s.append("no towers");
        } else {
            s.append(getIslandSize()) //TODO: here could color all this part to the team color!
                    .append(" ")
                    .append(getOwnership())
                    .append((getIslandSize() > 1) ? " towers" : " tower");
        }
        return s.toString();
    }
}
