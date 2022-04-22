package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertCastle;
import it.polimi.ingsw.model.expert.ExpertIsland;

import java.util.*;

public class Student extends Generic {
    private static Bag bag;
    private final List<Color> availableStudents = new ArrayList<>();
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
    @SuppressWarnings("unchecked")
    public void applyEffect(Map<Parameters, Object> parameterMap) throws NoSuchStudentException, TooManyStudentsException { //Note: the map could be used to return errors.
        List<Color> studentList = (List<Color>) parameterMap.get(Parameters.STUDENTLIST);
        ExpertIsland island;
        Color student;
        ExpertCastle playerCastle;
        switch (idChar) {
            case 1 -> {//MONK
                student = studentList.get(0);
                if (student == null)
                    throw new IllegalArgumentException("no student");
                island = (ExpertIsland) parameterMap.get(Parameters.ISLAND);
                if (island == null)
                    throw new IllegalArgumentException("no island");
                if (!availableStudents.contains(student)) {
                    throw new NoSuchStudentException("Students not available");
                }
                island.addStudent(student);//Adds one student per use.
                availableStudents.remove(student);
                availableStudents.add(bag.extract());
                cost = characterName.getCost() + 1;
            }
            case 7 -> { //JESTER
                if (studentList == null)
                    throw new IllegalArgumentException("no students list");
                if (!Arrays.asList(2, 4, 6) //legal studentList sizes
                        .contains(studentList.size()))
                    throw new IllegalArgumentException("should receive a list of 2, 4 or 6 students");
                int numberOfStudentsToMove = studentList.size() / 2;
                //Passaggio parametri: i primi 3 studenti della list sono quelli del giocoliere(da spostare nel castello)
                //i seocondi tre studenti sono quelli da togliere dal castello e mettere nel giocoliere
                List<Color> studentsToAdd = studentList.subList(0, numberOfStudentsToMove);
                List<Color> studentsToRemove = studentList.subList(numberOfStudentsToMove, numberOfStudentsToMove * 2);
                if (!availableStudents.containsAll(studentsToAdd)) {
                    List<Color> temp = new ArrayList<>(availableStudents);
                    for (Color c : studentsToAdd) {
                        if (!temp.contains(c))
                            throw new NoSuchStudentException("jester doesn't contain this student: ", c);
                        temp.remove(c);
                    }
                }
                String currentPlayer = (String) parameterMap.get(Parameters.PLAYERID);
                playerCastle
                        = ((Map<String, ExpertCastle>) parameterMap
                        .get(Parameters.CASTLEMAP))
                        .get(currentPlayer);
                playerCastle.removeStudentsFromWaitingRoom(studentsToRemove); //here is thrown the StudentException
                playerCastle.addStudentsInWaitingRoom(studentsToAdd);
                for (Color c : studentsToAdd)
                    availableStudents.remove(c);
                availableStudents.addAll(3, studentsToRemove);
                cost = characterName.getCost() + 1;
            }
            case 11 -> {//QUEEN
                cost = characterName.getCost() + 1;
            }
        }
    }

    public Map<Parameters, Object> getEffect(){
        Map<Parameters, Object> parameterMap = new HashMap<>();
        parameterMap.put(Parameters.STUDENTLIST, new ArrayList<>(availableStudents));
        return parameterMap;
    }
}
