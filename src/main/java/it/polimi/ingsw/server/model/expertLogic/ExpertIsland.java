package it.polimi.ingsw.server.model.expertLogic;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Island;
import it.polimi.ingsw.server.model.baseLogic.Team;
import it.polimi.ingsw.server.model.baseLogic.interfaces.StudentPlaces;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

/**
 * A decorator to the normal Island or Archipelago.
 * Adds the Blocked boolean check.
 */
public class ExpertIsland extends Island implements StudentPlaces {

    private Island island;

    public ExpertIsland(@NotNull Island island) {
        this.island = island;
    }

    public ExpertIsland() {}

    //For Witch Effect:
    @Override
    public boolean isBlocked() {
        return false;
    }

    //Interface overriding:
    @Override
    public void adds(StudentColor student, int place) throws IllegalAccessException {
        island.addStudent(student);
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
    public EnumMap<StudentColor, Integer> getStudents() {
        return island.getStudents();
    }


    @Override
    public Island setOwnership(Team ownership) {
        island.setOwnership(ownership);
        return this;
    }

    @Override
    public void addStudent(StudentColor c) {
        island.addStudent(c);
    }

    @Override
    public boolean addStudent(EnumMap<StudentColor, Integer> s) {
        return island.addStudent(s);
    }

    @Override
    public String toString() {
        return island.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ExpertIsland) obj;
        return this.island.equals(that.island) &&
                this.isBlocked() == that.isBlocked();
    }
}