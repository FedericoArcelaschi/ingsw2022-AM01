package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.expert.ExpertIsland;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends Generic {
    private static Bag bag;
    private static final List<Color> availableStudents = new ArrayList<>();

    public Student(int idChar, Bag bag) {
        super(idChar);
        this.bag = bag;
        switch (idChar){
            case 1, 11: availableStudents.addAll(bag.multipleExtract(4)); //MONK, QUEEN
            case 7: availableStudents.addAll(bag.multipleExtract(6)); //JESTER
        }
    }
    /**
     * MONK: Adds a student to the given island
     * @return if can place the student requested -> true else false
     * @param 
     */
    @Override
    public boolean applyEffect(Map<Parameters, Object> parameterMap) { //Note: the map could be used to return errors.
        switch(idChar) {
            case 1://MONK
                List<Color> studentList = (ArrayList) parameterMap.get(Parameters.STUDENTLIST);
                Color student = studentList.get(0);
                ExpertIsland island = (ExpertIsland) parameterMap.get(Parameters.ISLAND);
                if (availableStudents.contains(student)) {
                    island.addStudent(student);//Adds one student per use.
                    availableStudents.remove(student);
                    availableStudents.add(bag.extract());
                    return true;
                }
            case 7://JESTER

            case 11://QUEEN
        }
        return false;
    }

    public Map<Parameters, Object> getEffect(){
        Map<Parameters, Object> parameterMap = new HashMap<>();
        List<Color> students = new ArrayList<>(availableStudents);
        parameterMap.put(Parameters.STUDENTLIST, students);
        return parameterMap;
    }

}
