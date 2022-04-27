package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

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

    public Island setOwnership(Team ownership) {
        this.ownership = ownership;
        return this;
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
     * Adds multiple students to the island
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
     * @param influenceMap map that contains the sum of influences per team
     */
    private void towerInfluence(Map<Team, Integer> influenceMap){
        if(ownership != null) {
            int influence = influenceMap.get(ownership);
            influenceMap.replace(ownership, influence + getIslandNumber());
        }
    }

    /**
     * Calculates the influence that the students have on the island
     *
     * @param influenceMap map that contains the sum of influences per team
     * @param professorMap map that contains the team owners of each professor
     */
    protected void studentInfluence(Map<Team, Integer> influenceMap, Map<Color, Team> professorMap) {
        for (Color c : Color.values()) {
            if (professorMap.get(c) != null) {
                Team t = professorMap.get(c); //gets the team of professor's owner
                influenceMap.replace
                        (t, influenceMap.get(t) + students.get(c));  //add influence for the color to the owner of the professor
            }
        }
    }

    /**
     * Calculates the influence on the island per team
     * @param professorsMap map that contains the sum of influences per team
     * @return influenceMap map containing the sum of influences per team
     */
    public Map<Team, Integer> calculateInfluence(Map<Color, Team> professorsMap){
        Map<Team, Integer> influenceMap = new HashMap<>();
        for(Team t : Team.values())
            influenceMap.put(t, 0);
        studentInfluence(influenceMap, professorsMap);
        towerInfluence(influenceMap);
        return influenceMap;
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
