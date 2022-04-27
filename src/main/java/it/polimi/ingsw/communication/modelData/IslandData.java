package it.polimi.ingsw.communication.modelData;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Team;

import java.util.Map;

public record IslandData(
        Team ownership,
        Map<Color, Integer> students
) {
    public IslandData(Island island){
        this(island.getOwnership(), island.getStudents());
    }
}
