package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class ExpertIsland extends Island{
    private Island island;
    private boolean isBlocked;

    public ExpertIsland(Color students, Island island, boolean isBlocked){
        super(students);
        this.island = island;
        this.isBlocked = false;
    }

    /**
     *
     * @param c
     * @return
     */
    public boolean removeStudent(Color c){
        try {
            Map<Color, Integer> students = new HashMap<>(getStudents());
            students.put(c, students.get(c) - 1);
            setStudents(students);
            return true;
        }catch(NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @return
     */
    //public Map getExpertInfluence(){
    //
    //}

    /**Blocks the island.
     *
     * @return boolean
     */
    public boolean block(){
        isBlocked = true;
        return true;
    }
}
