package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.expert.ExpertIsland;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Student extends Generic {
    Bag bag;
    List<Color> availableStudents;

    public Student(int idChar, Bag bag) {
        super(idChar);
        this.bag =bag;
        availableStudents =new ArrayList<>();
        switch (idChar){
            case 1:for(int i = 1; i<=4 ;i++)availableStudents.add(bag.extract()); //MONK
            case 7:for(int i = 1; i<=6 ;i++)availableStudents.add(bag.extract()); //JESTER
            case 11:for(int i = 1; i<=4 ;i++)availableStudents.add(bag.extract()); //QUEEN
        }

    }

    /**
     * MONK: Adds a student to the given island
     * @return if can place the student requested -> true else false
     */
    @Override
    public boolean applyEffect(ExpertIsland island, String player, Castle castle, Map<String, Color> professorMap, boolean payedToken, int move, List<Color> students) {
        Color c = students.get(0);
        switch (idChar){
            case 1://MONK
                if(availableStudents.contains(c)){
                    island.addStudent(c); //only needs one student.
                    availableStudents.remove(c);
                    availableStudents.add(bag.extract());
                    return true;
                }
            case 7://JESTER
            case 11://QUEEN
        }
        return false;
    }
}
