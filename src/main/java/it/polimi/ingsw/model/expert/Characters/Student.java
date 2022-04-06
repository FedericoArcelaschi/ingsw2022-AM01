package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;

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
            case 1, 11: for(int i = 1; i<=4 ;i++)availableStudents.add(bag.extract()); //MONK, QUEEN
            case 7: for(int i = 1; i<=6 ;i++)availableStudents.add(bag.extract()); //JESTER
        }
    }

    /**
     * MONK: Adds a student to the given island
     * @return if can place the student requested -> true else false
     * @param 
     */
    @Override
    public boolean applyEffect(Map<Parameters, Object> parameterMap) {
        if((boolean) parameterMap.get(Parameters.PAY_TOKEN)){
            switch (idChar) {
                case 1://MONK
                    List<Color> colorList = (ArrayList) parameterMap.get(Parameters.STUDENTLIST);
                    Color c = colorList.get(0);
                    Island island = (Island) parameterMap.get(Parameters.ISLAND);
                    if (availableStudents.contains(c)) {
                        island.addStudent(c); //only needs one student.
                        availableStudents.remove(c);
                        availableStudents.add(bag.extract());
                        return true;
                    }
                case 7://JESTER
                case 11://QUEEN
            }
        }
        return false;
    }
}
