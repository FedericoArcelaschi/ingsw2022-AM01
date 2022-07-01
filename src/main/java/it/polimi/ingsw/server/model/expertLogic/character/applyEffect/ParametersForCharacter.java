package it.polimi.ingsw.server.model.expertLogic.character.applyEffect;

import it.polimi.ingsw.server.model.baseLogic.*;
import it.polimi.ingsw.server.model.baseLogic.interfaces.StudentPlaces;
import it.polimi.ingsw.server.model.expertLogic.character.specializedCharacters.BlockingCharacter;
import it.polimi.ingsw.server.model.expertLogic.influence.ExpertInfluence;

import java.util.List;
import java.util.Optional;

public class ParametersForCharacter {
    private List<StudentColor> requestedStudentList;
    private List<StudentColor> availableStudentsList;
    private List<StudentPlaces> placesList;
    private List<Island> islandList;
    private BlockingCharacter blockingCharacter;
    private StudentColor requestedStudent;
    private ExpertInfluence influence;
    private PossibleMovingSteps steps;
    private Integer numberOfPlayers;
    private Integer availableTiles;
    private Integer islandNumber;
    private Team currentTeam;
    private Bag bag;

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

    public void setSteps(PossibleMovingSteps steps) {
        this.steps = steps;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public void setAvailableTiles(int availableTiles) {
        this.availableTiles = availableTiles;
    }

    public void setBlockChar(BlockingCharacter blockingCharacter) {
        this.blockingCharacter = blockingCharacter;
    }

//GETTER
    public Optional<List<Island>> getIslandList() {
        return Optional.ofNullable(islandList);
    }

    public Optional<ExpertInfluence> getInfluence() {
        return Optional.ofNullable(influence);
    }

    public Optional<Team> getCurrentTeam() {
        return Optional.ofNullable(currentTeam);
    }

    public Optional<List<StudentPlaces>> getPlacesList() {
        return Optional.ofNullable(placesList);
    }


    public Optional<PossibleMovingSteps> getSteps() {
        return Optional.ofNullable(steps);
    }

    public Optional<StudentColor> getRequestedStudent() {
        return Optional.ofNullable(requestedStudent);
    }

    public Optional<List<StudentColor>> getRequestedStudentList() {
        return Optional.ofNullable(requestedStudentList);
    }

    public Optional<List<StudentColor>> getAvailableStudentsList() {
        return Optional.ofNullable(availableStudentsList);
    }

    public Optional<BlockingCharacter> getBlockingCharacter() {
        return Optional.ofNullable(blockingCharacter);
    }

    public Optional<Bag> getBag() {
        return Optional.ofNullable(bag);
    }

    public Optional<Integer> getAvailableTiles() {
        return Optional.ofNullable(availableTiles);
    }

    public Optional<Integer> getIslandIndex() {
        return Optional.ofNullable(islandNumber);
    }

    public Optional<Integer> getNumberOfPlayers() {
        return Optional.ofNullable(numberOfPlayers);
    }
}
