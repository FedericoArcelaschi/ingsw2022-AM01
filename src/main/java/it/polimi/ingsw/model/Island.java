package it.polimi.ingsw.model;

import java.util.*;

public class Island {
    private int id;
    private Map<Color, Integer> students;
    private Team ownership;

    public Island(Set<Color> students, Team ownership){
        this.id = id;
        this.students = new HashMap<>();
        this.ownership = null;
    }

    public Team getOwnership(){
        return ownership;
    }

    public int getIslandNumber(){
        return id;
    }

    public boolean addStudent(Color c, int n){
        try {
            students.put(c, students.get(c) + n);
            return true;
        }catch(NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    //MANCA IL toJson!!!!!!!!!!!!!!!!!
}
