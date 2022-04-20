package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
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
    private int numberOfAvailableStudents;

    public Student(int idChar) {
        super(idChar);
    }

    public Student(int idChar, Bag bag) {
        super(idChar);
        Student.bag = bag;
        switch (idChar) {
            case 1, 11 -> numberOfAvailableStudents = 4;
            case 7 -> numberOfAvailableStudents = 6;
            default -> throw new IllegalArgumentException("Wrong character-id: can only be 1, 7, 11. Actual: " + idChar);
        }
        availableStudents.addAll(bag.multipleExtract(numberOfAvailableStudents));
    }

    /**
     * MONK: Adds one student from the card to the given island
     * JESTER: Switches three or less students form the card to the dining room
     * QUEEN: Adds one student from the card to the dining room
     */
    @Override
    public void applyEffect(Map<Parameters, Object> parameterMap) throws NoSuchStudentException, TooManyStudentsException { //Note: the map could be used to return errors.
        List<Color> studentList;
        ExpertIsland island;
        Color student;
        ExpertCastle playerCastle;
        switch (idChar) {
            case 1 -> {//MONK
                studentList = (List<Color>) parameterMap.get(Parameters.STUDENTLIST);
                student = studentList.get(0);
                island = (ExpertIsland) parameterMap.get(Parameters.ISLAND);
                if (availableStudents.contains(student)) {
                    island.addStudent(student);//Adds one student per use.
                    availableStudents.remove(student);
                    availableStudents.add(bag.extract());
                    cost = characterName.getCost() + 1;
                    break;
                } else
                    System.out.println("monk test print");
                throw new NoSuchStudentException("Students not available");
            }
            case 7 -> { //JESTER
                studentList = (List<Color>) parameterMap.get(Parameters.STUDENTLIST);
                if (studentList.size() == 6) {
                    //Passaggio parametri: i primi 3 studenti della list sono quelli del giocoliere(da spostare nel castello)
                    //i seocondi tre studenti sono quelli da togliere dal castello e mettere nel giocoliere
                    if (availableStudents.containsAll(studentList.subList(0, 3))) {
                        //needs to handle the case with less than three students to move.(null in list or number of students in Move)
                        String currentPlayer = (String) parameterMap.get(Parameters.PLAYERID);
                        playerCastle = ((Map<String, ExpertCastle>) parameterMap
                                .get(Parameters.CASTLEMAP))
                                .get(currentPlayer);
                        playerCastle.removeStudentsFromWaitingRoom(studentList.subList(3, 6));
                        playerCastle.addStudentsInWaitingRoom(studentList.subList(0, 3));
                        availableStudents.addAll(studentList.subList(3, 6));
                        availableStudents.removeAll(studentList.subList(0, 3));
                        cost = characterName.getCost() + 1;
                    } else
                        throw new IllegalArgumentException("JESTER doesn't contain these students" + availableStudents);
                } else
                    throw new IllegalArgumentException("should receive a list of 2 or more student");
            }
            case 11 ->//QUEEN
                    cost = characterName.getCost() + 1;
        }
    }

    public Map<Parameters, Object> getEffect(){
        Map<Parameters, Object> parameterMap = new HashMap<>();
        List<Color> students = new ArrayList<>(availableStudents);
        parameterMap.put(Parameters.STUDENTLIST, students);
        return parameterMap;
    }
}
