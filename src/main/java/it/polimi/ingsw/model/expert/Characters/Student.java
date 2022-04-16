package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertCastle;
import it.polimi.ingsw.model.expert.ExpertIsland;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends Generic {
    private static Bag bag;
    private static final List<Color> availableStudents = new ArrayList<>();

    protected int cost;
    protected String explaination;
    protected int idChar;
    protected CharactersList characterName;

    public Student(int idChar, Bag bag) {
        super(idChar);
        cost = super.cost;
        explaination = super.explaination;
        idChar = super.idChar;
        characterName = super.characterName;
        this.bag = bag;
        switch (idChar){
            case 1, 11: availableStudents.addAll(bag.multipleExtract(4)); //MONK, QUEEN
                return;
            case 7: availableStudents.addAll(bag.multipleExtract(6)); //JESTER
                return;
        }
    }

    /**
     * MONK: Adds a student to the given island
     * @return if can place the student requested -> true else false
     * @param 
     */
    @Override
    public boolean applyEffect(Map<Parameters, Object> parameterMap) throws NoSuchStudentException, TooManyStudentsException { //Note: the map could be used to return errors.
        List<Color> studentList;
        ExpertIsland island;
        Color student;
        ExpertCastle playerCastle;
        switch(idChar) {
            case 1://MONK
                studentList = (List<Color>) parameterMap.get(Parameters.STUDENTLIST);
                student = studentList.get(0);
                island = (ExpertIsland) parameterMap.get(Parameters.ISLAND);
                if (availableStudents.contains(student)){
                    island.addStudent(student);//Adds one student per use.
                    availableStudents.remove(student);
                    availableStudents.add(bag.extract());
                    cost += CharactersList.MONK.getCost() + 1;
                    return true;
                }
                return false;
            case 7 ://JESTER
                //Passaggio parametri: i primi 3 studenti della list sono quelli del giocoliere(da spostare nel castello)
                //i seocondi tre studenti sono quelli da togliere dal castello e mettere nel giocoliere
                studentList = (ArrayList) parameterMap.get(Parameters.STUDENTLIST);
                if(!availableStudents.containsAll(studentList.subList(0,3)))return false;
                //need to handle the case with less that three students to move.(null in list or number of students in Move)

                playerCastle = ((Map<String, ExpertCastle>) parameterMap
                                    .get(Parameters.CASTLEMAP))
                                        .get(
                                            (String)parameterMap
                                            .get(Parameters.PLAYERID));
                playerCastle.removeStudentsFromWaitingRoom(studentList.subList(3,6));
                playerCastle.addStudentsInWaitingRoom(studentList.subList(0,3));
                availableStudents.addAll(studentList.subList(3,6));
                availableStudents.removeAll(studentList.subList(0,3));
                return true;
            case 11://QUEEN
                return true;
            default:
                throw new IllegalStateException("Unexpected value: " + idChar);
        }
    }

    public Map<Parameters, Object> getEffect(){
        Map<Parameters, Object> parameterMap = new HashMap<>();
        List<Color> students = new ArrayList<>(availableStudents);
        parameterMap.put(Parameters.STUDENTLIST, students);
        return parameterMap;
    }
}
