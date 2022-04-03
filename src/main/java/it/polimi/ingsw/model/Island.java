package it.polimi.ingsw.model;

import java.util.*;

public class Island {
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

    public void setStudents(Map<Color, Integer> s){
        this.students.clear();
        for(Color c: s.keySet()) {
            this.students.put(c, s.get(c));
        }
    }

    public boolean addStudent(Color c){
        try {
            students.put(c, students.get(c) + 1); //In the value of the color c, I'm putting the previous number of students + 1.
            return true;
        }catch(NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean addStudent(Map<Color, Integer> s){
        try {
            for(Color c: s.keySet()) {
                students.put(c, students.get(c) + s.get(c));
            }
            return true;
        }catch(NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    private void towerInfluence(Map<Team, Integer> influence){
        if(ownership != null){
            influence.put(ownership,getIslandNumber());
        }
    }

    private void studentInfluence(Map<Team, Integer> influence, Map<Color, Castle> professorMap){
        for(Color c : Color.values()){
            Team t = professorMap.get(c).getTeam(); //take team of the owner of the professor
            influence.put(t, influence.get(t) + students.get(c));  //add influence for the color to the owner of the professor
        }
    }

    public Map<Team, Integer> calculateInfluence(Map<Color, Castle> professorMap){
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
