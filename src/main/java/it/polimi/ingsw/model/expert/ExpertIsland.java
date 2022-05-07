package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.expert.boardInterfaces.StudentPlaces;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * A decorator to the normal Island.
 * Adds the Blocked boolean check.
 */
public class ExpertIsland extends Island implements StudentPlaces {

    private Island island;

    public ExpertIsland(@NotNull Island island) {
        this.island = island;
    }

    public ExpertIsland() {
    }

//For Witch Effect:
    public boolean isBlocked() {
        return false;
    }

//Interface overriding:
    @Override
    public void adds(Color student, int place) throws IllegalAccessException {
        island.addStudent(student);
    }
    @Override
    public void removes(Color student, int place) throws IllegalAccessException {
        island.removes(student, place);
    }

    //As for Base mode:
    @Override
    public Team getOwnership() {
        return island.getOwnership();
    }

    @Override
    public int getIslandNumber() {
        return island.getIslandNumber();
    }

    @Override
    public Map<Color, Integer> getStudents() {
        return island.getStudents();
    }

    @Override
    public ExpertIsland setOwnership(Team ownership) {
        return new ExpertIsland(island.setOwnership(ownership));
    }

    @Override
    public void addStudent(Color c) {
        island.addStudent(c);
    }

    @Override
    public boolean addStudent(Map<Color, Integer> s) {
        return island.addStudent(s);
    }

    @Override
    public String toString() {
        return island.toString();
    }


}
