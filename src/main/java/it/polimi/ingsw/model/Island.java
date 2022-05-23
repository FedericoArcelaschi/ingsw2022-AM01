package it.polimi.ingsw.model;

import java.util.*;

public class Island {
    private final Map<StudentColor, Integer> students;
    private Team ownership;

    public Island(StudentColor student){
        this.students = new HashMap<>();
        for(StudentColor c : StudentColor.values()){
            if(c == student) students.put(c, 1);
            else students.put(c, 0);
        }
        this.ownership = null;
    }

    public Island(){
        this.students = new HashMap<>();
        for(StudentColor c : StudentColor.values())
            students.put(c, 0);
        this.ownership = null;
    }

    public Team getOwnership(){
        return ownership;
    }

    public int getIslandNumber(){
        return 1;
    }

    public Map<StudentColor, Integer> getStudents() {
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
    public boolean addStudent(StudentColor c){
        students.put(c, students.get(c) + 1); //In the value of the color c, I'm putting the previous number of students + 1.
        return true;
    }

    /**
     * add multiple students to the island
     * @param s Map that contains how many students per color to add
     * @return check boolean
     */
    public boolean addStudent(Map<StudentColor, Integer> s){
        for(StudentColor c: s.keySet()) {
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
    private void studentInfluence(Map<Team, Integer> influence, Map<StudentColor, Team> professorMap){
        for(StudentColor c : StudentColor.values()){
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
    public Map<Team, Integer> calculateInfluence(Map<StudentColor, Team> professorMap){
        Map<Team, Integer> influence = new HashMap<>();
        for(Team t : Team.values())
            influence.put(t, 0);


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
}
