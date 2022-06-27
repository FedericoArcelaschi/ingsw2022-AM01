package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.expertLogic.character.specializedCharacters.charTypes.BlockingCharacter;
import it.polimi.ingsw.server.model.baseLogic.interfaces.StudentPlaces;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

/**
 * Decorator to ExpertIsland.
 * As of right now is used only by the witch.
 * Decorates both Island and Archipelago
 */
public class BlockedIsland extends ExpertIsland implements StudentPlaces {

    private final @NotNull ExpertIsland island;

    /**
     * Needed to restore the "Block Tile" when the island is unlocked.
     */
    private final BlockingCharacter witch;

    public BlockedIsland(@NotNull ExpertIsland island, BlockingCharacter witch) {
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
    public @Nullable Team getOwnership() {
        return island.getOwnership();
    }

    @Override
    public int getIslandNumber() {
        return island.getIslandNumber();
    }

    @Override
    public boolean isBlocked() {
        return true;
    }

    //as for normal ExpertIsland.
    @Override
    public EnumMap<StudentColor, Integer> getStudents() {
        return island.getStudents();
    }

    @Override
    public void addStudent(StudentColor c) {
        island.addStudent(c);
    }

    @Override
    public boolean addStudent(EnumMap<StudentColor, Integer> s) {
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
