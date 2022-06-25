package it.polimi.ingsw.server.model.expertLogic.character.applyEffect;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.baseLogic.interfaces.GreaterTeam;
import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.StudentException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.server.model.expertLogic.BlockedIsland;
import it.polimi.ingsw.server.model.expertLogic.ExpertIsland;
import it.polimi.ingsw.server.model.baseLogic.interfaces.PossibleParameters;
import it.polimi.ingsw.server.model.baseLogic.interfaces.StudentPlaces;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.BlockCharacter;
import it.polimi.ingsw.server.model.expertLogic.influence.ExpertInfluence;
import it.polimi.ingsw.server.model.expertLogic.influence.InfluenceComputingFunction;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ProfessorsComputingExpert;
import it.polimi.ingsw.server.model.expertLogic.influence.professor.ProfessorsMapComputingFunctions;
import it.polimi.ingsw.server.model.baseLogic.influence.Influence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CharacterFunction {
    MONK(
        (ParametersForCharacter par)
        -> {
            //TODO: throw an exceptions for each wrong input!
            List<StudentColor> availableStudents = par.getAvailableStudentsList();
            List<StudentColor> requestedStudents = par.getRequestedStudentList();
            Bag bag = par.getBag();
            List<StudentPlaces> placesList = par.getPlacesList();
            final int StudentToMovePosition = 0;

            if (availableStudents == null)
                throw new IllegalArgumentException("No student in monk");
            if (requestedStudents == null)
                throw new IllegalArgumentException("No students to move");
            StudentPlaces island = placesList.get(par.getIslandIndex() + par.getNumberOfPlayers());
            if (island == null)
                throw new IllegalArgumentException("No island in monk");

            List<StudentColor> availableStudentsCopy = new ArrayList<>(availableStudents);
            for (StudentColor color : requestedStudents) {
                if(!availableStudentsCopy.remove(color))
                    throw new NoSuchStudentException("Students not available on monk card");
            }

            try {
                island.adds(requestedStudents.get(StudentToMovePosition), -1); //Adds one student per use.
            } catch (IllegalAccessException | TooManyStudentsException e) {
                throw new AssertionError();
            }
            availableStudents.remove(requestedStudents);
            availableStudents.add(bag.extract());
        }
    ),
    FARMER(
        (ParametersForCharacter par)
        -> {
            ExpertInfluence influence = par.getInfluence();
            Team currTeam = par.getCurrentTeam();
            ProfessorsComputingExpert<PossibleParameters> function = ProfessorsMapComputingFunctions.FARMER.getFunction();
            influence.decorateProfessors(function, currTeam);
            }
    ),
        GUARD( //FIXME /*
            (ParametersForCharacter par)
            -> {
                List<Island> islandList = par.getIslandList();
                Influence influence = par.getInfluence();
                int islandIndex = par.getIslandIndex();
                Island island = islandList.get(islandIndex);
                Team teamBeforeComputing = island.getOwnership();
                Team t = GreaterTeam.findGreaterTeam(influence.getInfluenceMap(island));
                if (t == null || t.equals(teamBeforeComputing))
                    return; //no island is conquered.
                island = island.setOwnership(t);

                ExpertIsland previous, next;
                if (islandIndex == 0) {
                    previous = (ExpertIsland) islandList.get(islandList.size() - 1);
                    next = (ExpertIsland) islandList.get(1);
                } else if (islandIndex == islandList.size() - 1) {
                    previous = (ExpertIsland) islandList.get(islandList.size() - 2);
                    next = (ExpertIsland) islandList.get(0);
                } else {
                    previous = (ExpertIsland) islandList.get(islandIndex - 1);
                    next = (ExpertIsland) islandList.get(islandIndex + 1);
                }
                List<Island> neightbouringIsland = Arrays.asList(previous, island, next);

                //checks the ownership
                List<Island> islandToJoin = null;
                if (neightbouringIsland.get(0).getOwnership() != null) {
                    if (neightbouringIsland.get(0).getOwnership() == neightbouringIsland.get(1).getOwnership()) {
                        islandToJoin = neightbouringIsland.subList(0, 2);
                        islandIndex--;
                    }
                    if (neightbouringIsland.get(1).getOwnership() == neightbouringIsland.get(2).getOwnership()) {
                        assert islandToJoin != null;
                        islandToJoin.add(neightbouringIsland.get(2));
                    }
                } else if (neightbouringIsland.get(1).getOwnership() == neightbouringIsland.get(2).getOwnership())//not the first one for sure.
                    islandToJoin = neightbouringIsland.subList(1, 3);

                if (islandToJoin == null)
                    return; //another escape if the conquered island won't join with the neighbours
                //islands are joined
                islandList
                        .add(islandIndex,
                                new ExpertIsland(
                                        new Archipelago(islandToJoin)));
                islandList.removeAll(islandToJoin);
                par.getSteps().setInt(islandIndex);
            }
        ),
    MAILMAN(
        (ParametersForCharacter par)
        -> {
            IntegerBoxing steps = par.getSteps();
            steps.affect(2);
        }
    ),
    WITCH(
        (ParametersForCharacter par)
        -> {
            int availableBlockTile = par.getAvailableTiles();
            List<Island> islandList = par.getIslandList();
            int islandIndex = par.getIslandIndex();
            ExpertIsland islandToBlock = (ExpertIsland) islandList.get(islandIndex);
            BlockCharacter blockChar = par.getBlockChar();
            if (availableBlockTile == 0)
                throw new IllegalArgumentException("4 islands are already blocked");
            if (islandToBlock.isBlocked())
                throw new IllegalArgumentException("Island is already blocked");
            BlockedIsland blockedIsland = new BlockedIsland(islandToBlock, blockChar);
            islandList.remove(islandToBlock);
            islandList.add(islandIndex, blockedIsland);
        }
    ),

    CENTAUR(
        (ParametersForCharacter par)
        -> {
            ExpertInfluence influence = par.getInfluence();
            influence.decorateInfluence(
                    InfluenceComputingFunction.CENTAUR.getFunction(),
                    null);
        }
    ),
    JESTER(
        (ParametersForCharacter par)
        -> {
            List<StudentColor> requestedStudents = par.getRequestedStudentList();
            List<StudentColor> availableStudents = par.getAvailableStudentsList();
            StudentPlaces castle = par.getPlacesList().get(0);

            if (requestedStudents == null)
                throw new IllegalArgumentException("no students list");
            if (!Arrays.asList(2, 4, 6) //legal requested student list sizes.
                    .contains(requestedStudents.size()))
                throw new IllegalArgumentException("Player should give a list of 2, 4 or 6 students");

            int numberOfStudentsToMove = requestedStudents.size() / 2;
            //First three (or less) students in the list are the ones
                //to add in the Castle-Waiting Room and take from the Jester's available students.
            List<StudentColor> studentsToAdd = requestedStudents.subList(0, numberOfStudentsToMove);
            //Second three (or less) students are the ones
                //to remove from waiting room and put in the Jester's available students.
            List<StudentColor> studentsToRemove = requestedStudents.subList(numberOfStudentsToMove, numberOfStudentsToMove * 2);
            if (!availableStudents.containsAll(studentsToAdd)) {
                List<StudentColor> temp = new ArrayList<>(availableStudents);
                for (StudentColor c : studentsToAdd) {
                    if (!temp.contains(c))
                        throw new NoSuchStudentException("jester doesn't contain a " + c + " student!");
                    temp.remove(c);
                } //initial checking
            }
            for (StudentColor c: studentsToRemove) {
                try {
                    castle.removes(c, 1);
                } catch (NoSuchStudentException nsse ) {
                    castle.adds(c, 1);
                    throw new NoSuchStudentException(nsse.getMessage());
                }
            } //here could be thrown the StudentException

            for (StudentColor c: studentsToAdd) {
                castle.adds(c, 1);
            }

            for(StudentColor c : studentsToAdd)
                availableStudents.remove(c);
            availableStudents.addAll(3, studentsToRemove);
        }
    ),
    KNIGHT(
        (ParametersForCharacter par)
        -> {
            ExpertInfluence influence = par.getInfluence();
            Team currTeam = par.getCurrentTeam();
            if (influence == null)
                throw new IllegalArgumentException("influence is null");
            influence.decorateInfluence(
                    InfluenceComputingFunction.KNIGHT.getFunction(),
                    currTeam);
        }
    ),
    COOK(
        (ParametersForCharacter par)
        -> {
            ExpertInfluence influence = par.getInfluence();
            StudentColor student = par.getRequestedStudent();
            if (student == null)
                throw new IllegalArgumentException("no student given for cook card");
            if (influence == null)
                throw new IllegalArgumentException("influence problem (model-side)");
            influence.decorateInfluence(
                InfluenceComputingFunction.COOK.getFunction(),
                student);
        }
    ),
    STORYTELLER (
        (ParametersForCharacter par)
        -> {
            List<StudentColor> students = par.getRequestedStudentList();
            List<StudentPlaces> placesList = par.getPlacesList();
            final int maxStudentsToMove = 4;
            if (students.size() != maxStudentsToMove && students.size() != maxStudentsToMove / 2)
                throw new IllegalArgumentException("wrong student input");

            //first student(s) are the ones in the waiting room, to be moved to the dining room
            List<StudentColor> studentsToDiningRoom = new ArrayList<>(students.subList(0, students.size() / 2));
            //the other(s) are the ones to be moved from the dining room to the waiting room
            List<StudentColor> studentsToWaitingRoom = new ArrayList<>(students.subList(students.size() / 2, students.size()));

            StudentPlaces currentPlayerCastle = placesList.get(0);

            int removedStudents = 0;
            int addedStudents = 0;
            try { // I simulated a transaction to have consistency!
                for (StudentColor c : students) {
                    if (removedStudents < studentsToWaitingRoom.size())
                        currentPlayerCastle.removes(c, 1); // * students from the waiting room *
                    else
                        currentPlayerCastle.removes(c, 0); // §  students from the dining room §
                    removedStudents++;
                }
                //after all the students are removed, then they are added in the other place.
                //this implementation was necessary as putting too many students in the waiting room would throw an error.
                for (StudentColor c : students) {
                    if (addedStudents < studentsToWaitingRoom.size())
                        currentPlayerCastle.adds(c, 0); // * students to the dining room *
                    else
                        currentPlayerCastle.adds(c, 1); // § students to the waiting room §
                    addedStudents++;
                }
            } catch (StudentException se) {
                removedStudents--;
                for (int i = removedStudents; i >= 0; i--) {
                    if(i < studentsToWaitingRoom.size())
                        currentPlayerCastle.adds(students.get(i), 1); // * restoring students in waiting room *
                    else
                        currentPlayerCastle.adds(students.get(i), 0); // § restoring students in dining room §
                }
                addedStudents--;
                for (int i = addedStudents; i >= students.size()/2; i--) {
                    if (i < studentsToWaitingRoom.size())
                        currentPlayerCastle.removes(students.get(i), 0); // § removing students in waiting room §
                    else
                        currentPlayerCastle.removes(students.get(i), 1); // * removing students in diningroom *
                }
                throw se;
            }
        }
    ),
    QUEEN(
        (ParametersForCharacter par)
        -> {
            List<StudentColor> requestedStudents = par.getRequestedStudentList();
            List<StudentColor> availableStudents = par.getAvailableStudentsList();
            Bag bag = par.getBag();
            List<StudentPlaces> placesList = par.getPlacesList();
            final int studentsToMove = 1;
            final int availableStudentsSize = 4;
            if(availableStudents.size() != availableStudentsSize)
                throw new IllegalArgumentException("wrong number of students available on Queen character.");
            if(requestedStudents.get(0) == null)
                throw new IllegalArgumentException("No student given to the queen: impossible to play the card.");
            StudentPlaces castle = placesList.get(0);
            if (castle == null)
                throw new IllegalArgumentException("no castle.");
            if (!availableStudents.contains(requestedStudents.get(0)))
                throw new NoSuchStudentException(requestedStudents.get(0).toString().toLowerCase() + " students not available in the Queen.\n" +
                        "Available students are: " + availableStudents.toString().replace(']', ' ').replace('[', ' '));
            castle.adds(requestedStudents.get(0), 0);
            availableStudents.add(bag.extract());
        }
    ),
    TAXMAN(
        (ParametersForCharacter par)
        -> {
            if(par.getRequestedStudentList() == null)
                throw new IllegalArgumentException("TaxMan requires a color to be activated.");
            if(par.getRequestedStudentList().isEmpty())
                throw new IllegalArgumentException("TaxMan requires a color to be activated.");
            StudentColor student = par.getRequestedStudentList().get(0);
            List<StudentPlaces> placesList = par.getPlacesList();
            int players = par.getNumberOfPlayers();
            final int studentsToRemove = 3;
            for (int i = 0; i < players; i++) {
                if(placesList.get(i) == null)
                    throw new IllegalArgumentException("a castle is missing");
                for (int j = 0; j < studentsToRemove; j++)
                    try {
                        placesList.get(i).removes(student, 0);
                    } catch (NoSuchStudentException ignored) {} //It's possible that a given player doesn't have 3 students of the requested color.
            }
        }
    );

    /**
     *
     * @param idChar between 1 and 12
     */
    public static ApplyEffect getCharFunction(int idChar){
        return CharacterFunction.values()[idChar-1].function;
    }

    private final ApplyEffect function;

    CharacterFunction(ApplyEffect function) {
        this.function = function;
    }

}
