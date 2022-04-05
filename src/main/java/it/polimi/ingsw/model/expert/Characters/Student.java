package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;
import java.util.ArrayList;
import java.util.List;

public class Student extends Generic {
    Bag bag;
    List<Color> availableStudents;
    public Student(int idChar, Bag bag){
        super(idChar);
        this.bag = bag;
        availableStudents = new ArrayList<>();
        for(int i=1; i<=4; i++) availableStudents.add(bag.extract());// vero per il MONK
    }

    /**
     * MONK: Adds a student to the given island
     * @param payedToken
     * @param island
     * @param student
     * @return if possible action requested -> true else false
     */
    public boolean applyEffect(boolean payedToken, Island island, Color student) {//effect 1: MONK
        if(availableStudents.contains(student) && payedToken) {
            island.addStudent(student);
            availableStudents.remove(student);
            availableStudents.add(bag.extract());
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean applyEffect() {
        return false;
    }
}
