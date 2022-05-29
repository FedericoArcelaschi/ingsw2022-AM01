package it.polimi.ingsw.model.expertLogic.character.applyEffect;

import it.polimi.ingsw.model.baseLogic.*;
import it.polimi.ingsw.model.baseLogic.interfaces.StudentPlaces;
import it.polimi.ingsw.model.expertLogic.character.charTypes.BlockCharacter;
import it.polimi.ingsw.model.expertLogic.influence.ExpertInfluence;

import java.util.List;

public class ParametersForCharacter { //FIXME
    private StudentColor requestedStudent;
    private ExpertInfluence influence;
    private Team currentTeam;
    private Integer islandNumber;
    private Integer numberOfPlayers;
    private IntegerBoxing steps;
    private Bag bag;
    private BlockCharacter blockChar;
    private List<StudentColor> requestedStudentList;
    private List<StudentColor> availableStudentsList;
    private List<StudentPlaces> placesList;
    private List<Island> islandList;
    private Integer availableTiles;

    public ParametersForCharacter() {
    }

//SETTER

    public void setIslandList(List<Island> islandList) {
        this.islandList = islandList;
    }

    public void setInfluence(ExpertInfluence influence) {
        this.influence = influence;
    }

    public void setRequestedStudent(StudentColor requestedStudent) {
        this.requestedStudent = requestedStudent;
    }

    public void setRequestedStudentList(List<StudentColor> requestedStudentList) {
        this.requestedStudentList = requestedStudentList;
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }

    public void setAvailableStudentsList(List<StudentColor> availableStudentsList) {
        this.availableStudentsList = availableStudentsList;
    }

    public void setPlacesList(List<StudentPlaces> placesList) {
        this.placesList = placesList;
    }

    public void setIslandIndex(Integer islandNumber) {
        this.islandNumber = islandNumber;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setSteps(IntegerBoxing steps) {
        this.steps = steps;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }
    public void setAvailableTiles(int availableTiles) {
        this.availableTiles = availableTiles;
    }
    public void setBlockChar(BlockCharacter blockChar) {
        this.blockChar = blockChar;
    }

//GETTER

    public List<Island> getIslandList() {
        return islandList;
    }

    public ExpertInfluence getInfluence() {
        return influence;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public List<StudentColor> getAvailableStudentsList() {
        return availableStudentsList;
    }

    public List<StudentPlaces> getPlacesList() {
        return placesList;
    }

    public Integer getIslandIndex() {
        return islandNumber;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public IntegerBoxing getSteps() {
        return steps;
    }

    public StudentColor getRequestedStudent() {
        return requestedStudent;
    }

    public List<StudentColor> getRequestedStudentList() {
        return requestedStudentList;
    }

    public BlockCharacter getBlockChar() {
        return blockChar;
    }

    public Bag getBag() {
        return bag;
    }

    public int getAvailableTiles() {
        return availableTiles;
    }
}
