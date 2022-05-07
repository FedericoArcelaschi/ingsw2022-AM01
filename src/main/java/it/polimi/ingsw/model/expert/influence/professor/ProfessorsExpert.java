package it.polimi.ingsw.model.expert.influence.professor;

import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.influence.Professors;
import it.polimi.ingsw.model.influence.functionalInterfaces.ProfessorComputing;
import org.jetbrains.annotations.NotNull;

import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;

/**
 * Decorator class for FARMER IMPLEMENTATION.
 * Can be extended with ease.
 * As of ri
 */

public class ProfessorsExpert<T> extends Professors{
    private T var;
    private final ProfessorsComputingExpert<T> function;
    private final Professors professors;


    /**
     * Cosn
     * @param professors the oblject to decorate
     * @param function the function o decorate it with.
     * @param var the possible value of the other parameter for the ProfessorComputingExpert.
     */

    public ProfessorsExpert(Professors professors, ProfessorsComputingExpert<T> function, Optional<T> var) {
        super();
        this.professors = professors;
        this.function = function;
        var.ifPresent(t -> this.var = t);
    }

    @Override
    public void updateProfessorsAssigning() {
        professorsMap = function.computeProfessorsMap(castleMap, professorsMap, var);
    }

    @Override
    public Map<Color, Team> getProfessorsAssigning() {
        return this.professors.getProfessorsAssigning();
    }

    public Professors undecorate(){
        return professors;
    }

}
