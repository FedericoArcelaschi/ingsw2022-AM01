package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.expert.characters.Block;

import java.util.Map;

public class BlockedIsland extends ExpertIsland {
    private final ExpertIsland island;
    private final Block witch;

    public BlockedIsland(ExpertIsland island, Block witch) {
        this.island = island;
        this.witch = witch;
    }

    /**
     * @return if blocked an unlocked island, if unlocked, the new island.
     */
    @Override
    public ExpertIsland setOwnership(Team ownership) {
        witch.addBlockTile();
        return island;
    }

    @Override
    public boolean isBlocked() {
        return true;
    }

    @Override
    public Map<Color, Integer> getStudents() {
        return island.getStudents();
    }

    @Override
    public boolean addStudent(Color c) {
        return island.addStudent(c);
    }

    @Override
    public boolean addStudent(Map<Color, Integer> s) {
        return island.addStudent(s);
    }
}
