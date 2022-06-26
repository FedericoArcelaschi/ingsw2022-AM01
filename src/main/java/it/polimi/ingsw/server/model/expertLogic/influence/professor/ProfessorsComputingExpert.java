package it.polimi.ingsw.server.model.expertLogic.influence.professor;

import it.polimi.ingsw.server.model.baseLogic.Castle;
import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.baseLogic.Team;

import java.util.Map;

@FunctionalInterface
public interface ProfessorsComputingExpert<T> {
    void computeProfessorsMap(Map<String, Castle> castle,
                                          Map<StudentColor, Team> professorsMap,
                                          T var);
}
