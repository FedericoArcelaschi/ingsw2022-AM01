package it.polimi.ingsw.model;

import java.util.*;

public class Island{
    private final Map<Color, Integer> students;
    private Team ownership;

    public Island(Color student){
        this.students = new HashMap<>();
        for(Color c : Color.values()){
            if(c == student) students.put(c, 1);
            else students.put(c, 0);
        }
        this.ownership = null;
    }

    public Island(){
        this.students = new HashMap<>();
        for(Color c : Color.values())
            students.put(c, 0);
        this.ownership = null;
    }

    public Team getOwnership(){
        return ownership;
    }

    public int getIslandNumber(){
        return 1;
    }

    public Map<Color, Integer> getStudents() {
        return new HashMap<>(students);
    }

    public void setOwnership(Team ownership) {
        this.ownership = ownership;
    }

    /**
     * add a student to the island
     * @param c color of the student to add
     * @return check boolean
     */
    public boolean addStudent(Color c){
        students.put(c, students.get(c) + 1); //In the value of the color c, I'm putting the previous number of students + 1.
        return true;
    }

    /**
     * add multiple students to the island
     * @param s Map that contains how many students per color to add
     * @return check boolean
     */
    public boolean addStudent(Map<Color, Integer> s){
        for(Color c: s.keySet()) {
            students.put(c, students.get(c) + s.get(c));
        }
        return true;
    }

    /**
     * calculate the influence that the towers have on the island
     * @param influence map that contains the sum of influences per team
     */
    private void towerInfluence(Map<Team, Integer> influence){
        if(ownership != null){
            influence.put(ownership,getIslandNumber());
        }
    }

    /**
     * calculate the influence that the students have on the island
     * @param influence map that contains the sum of influences per team
     * @param professorMap map that contains the team owners of each professor
     */
    private void studentInfluence(Map<Team, Integer> influence, Map<Color, Team> professorMap){
        for(Color c : Color.values()){
            if(professorMap.get(c) != null) {
                Team t = professorMap.get(c); //take team of the owner of the professor
                influence.replace(t, influence.get(t) + students.get(c));  //add influence for the color to the owner of the professor
            }
        }
    }

    /**
     * calculate the influence on the island per team
     * @param professorMap map that contains the sum of influences per team
     * @return an object containing the influence per team
     */
    public Map<Team, Integer> calculateInfluence(Map<Color, Team> professorMap){
        Map<Team, Integer> influence = new HashMap<>();
        for(Team t : Team.values()) influence.put(t, 0);
        studentInfluence(influence, professorMap);
        towerInfluence(influence);
        return influence;
    }

    @Override
    public String toString() {
        return "Island{" +
                "students=" + students +
                ", ownership=" + ownership +
                '}';
    }

    public Island joinTo(Island next) {
        return next;
    }
    //TODO: method toJson!!
}
