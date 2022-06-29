package it.polimi.ingsw.server.model.expertLogic.character.applyEffect;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.baseLogic.interfaces.StudentPlaces;
import it.polimi.ingsw.server.model.expertLogic.character.specializedCharacters.BlockingCharacter;
import it.polimi.ingsw.server.model.expertLogic.influence.ExpertInfluence;

import java.util.List;

public class ParametersForCharacter { //FIXME
    private StudentColor requestedStudent;
    private ExpertInfluence influence;
    private Team currentTeam;
    private Integer islandNumber;
    private Integer numberOfPlayers;
    private IntegerBoxing steps;
    private Bag bag;
    private BlockingCharacter blockingCharacter;
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
    public void setBlockCharacter(BlockingCharacter blockingCharacter) {
        this.blockingCharacter = blockingCharacter;
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

    public BlockingCharacter getBlockingCharacter() {
        return blockingCharacter;
    }

    public Bag getBag() {
        return bag;
    }

    public int getAvailableTiles() {
        return availableTiles;
    }
}
