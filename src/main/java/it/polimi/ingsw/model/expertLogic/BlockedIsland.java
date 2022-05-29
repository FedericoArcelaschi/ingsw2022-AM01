package it.polimi.ingsw.model.expertLogic;

import it.polimi.ingsw.model.baseLogic.StudentColor;
import it.polimi.ingsw.model.baseLogic.Team;
import it.polimi.ingsw.model.expertLogic.character.charTypes.BlockCharacter;
import it.polimi.ingsw.model.baseLogic.interfaces.StudentPlaces;

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
    private final BlockCharacter witch;

    public BlockedIsland(ExpertIsland island, BlockCharacter witch) {
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
        return island; //unchanged ownership.
    }

    @Override
    public boolean isBlocked() {
        return true;
    }

    //as for normal ExpertIsland.
    @Override
    public Map<StudentColor, Integer> getStudents() {
        return island.getStudents();
    }

    @Override
    public void addStudent(StudentColor c) {
        island.addStudent(c);
    }

    @Override
    public boolean addStudent(Map<StudentColor, Integer> s) {
        return island.addStudent(s);
    }

    /*
     * For ExpertCharacter implementation.
     */
    @Override
    public void adds(StudentColor student, int place) throws IllegalAccessException {
        throw new IllegalAccessException("This method should only be called for Castle classes.");
    }

    @Override
    public void removes(StudentColor student, int place) throws IllegalAccessException {
        throw new IllegalAccessException("This method should only be called for Castle classes.");
    }

}
