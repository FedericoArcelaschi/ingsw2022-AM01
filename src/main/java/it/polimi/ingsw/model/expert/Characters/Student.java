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
    private final int numberOfAvailableStudents;

    public Student(int idChar, Bag bag) {
        super(idChar);
        this.bag = bag;
        int k = 0;
        switch (idChar){
            case 1, 11: k = 4;
                        break;
            case 7:     k = 6;
                        break;
        }
        numberOfAvailableStudents = k;
        availableStudents.addAll(bag.multipleExtract(numberOfAvailableStudents));
    }

    /**
     * MONK: Adds one student from the card to the given island
     * JESTER: Switches three or less students form the card to the dining room
     * QUEEN: Adds one student from the card to the dining room
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
                    cost = characterName.getCost() +1;
                    return true;
                }
                return false;
            case 7: //JESTER
                    //Passaggio parametri: i primi 3 studenti della list sono quelli del giocoliere(da spostare nel castello)
                    //i seocondi tre studenti sono quelli da togliere dal castello e mettere nel giocoliere
                studentList = (ArrayList) parameterMap.get(Parameters.STUDENTLIST);
                if(!availableStudents.containsAll(studentList.subList(0,3)))return false;
                    //needs to handle the case with less than three students to move.(null in list or number of students in Move)
                String currentPlayer = (String)parameterMap.get(Parameters.PLAYERID);
                playerCastle = ((Map<String, ExpertCastle>) parameterMap
                                    .get(Parameters.CASTLEMAP))
                                        .get(currentPlayer);
                playerCastle.removeStudentsFromWaitingRoom(studentList.subList(3,6));
                playerCastle.addStudentsInWaitingRoom(studentList.subList(0,3));
                availableStudents.addAll(studentList.subList(3,6));
                availableStudents.removeAll(studentList.subList(0,3));
                cost = characterName.getCost() +1;
                return true;
            case 11://QUEEN

                cost = characterName.getCost() +1;
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
