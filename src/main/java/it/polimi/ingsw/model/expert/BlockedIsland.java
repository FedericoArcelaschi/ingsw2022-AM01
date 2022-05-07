package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.expert.character.type.IslandCharacter;
import it.polimi.ingsw.model.expert.boardInterfaces.StudentPlaces;

import java.util.Map;

/**
 * Decorator to ExpertIsland.
 * As of right now is used only by the witch.
 * Decorates both Island and Archipelago
 */
public class BlockedIsland extends ExpertIsland implements StudentPlaces {

    private final ExpertIsland island;

    /**
     * Needed to restore the "Block Tile" when the island is unlocked.
     */
    private final IslandCharacter witch;

    public BlockedIsland(ExpertIsland island, IslandCharacter witch) {
        super();
        this.island = island;
        this.witch = witch;
    }

    /**
     * If someone tries to conquer the island the owner doesn't change but the island is now unlocked.
     * Restores the block Tile to the witch.
     * @return the unlocked island
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
    public void addStudent(Color c) {
        island.addStudent(c);
    }

    @Override
    public boolean addStudent(Map<Color, Integer> s) {
        return island.addStudent(s);
    }

    /*
     * For ExpertCharacter implementation.
     */
    @Override
    public void adds(Color student, int place) throws IllegalAccessException {
        throw new IllegalAccessException("This method should only be called for Castle classes.");
    }

    @Override
    public void removes(Color student, int place) throws IllegalAccessException {
        throw new IllegalAccessException("This method should only be called for Castle classes.");
    }
}
