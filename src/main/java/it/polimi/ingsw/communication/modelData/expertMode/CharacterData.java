package it.polimi.ingsw.communication.modelData.expertMode;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CharacterData {
    private final String name;
    private final int cost;
    private final List<StudentColor> students;
    private final String description;
    private final CharacterUtility characterUtility;

    public CharacterData(CharacterUtility characterUtility, int cost, List<StudentColor> students, String description){
        this.name = characterUtility.name();
        this.characterUtility = characterUtility;
        this.cost = cost;
        this.students = students;
        this.description = description;
    }

    public String toString(@Nullable CharacterUtility characterUtility){
        StringBuilder output = new StringBuilder();
        if(Objects.equals(this.characterUtility, characterUtility))
            output  .append("\u0033[38;5;2;252;233;79m")
                    .append(name.charAt(0))
                    .append(name.substring(1).toLowerCase())
                    .append("\u0033[0m");
        else
            output  .append(name.charAt(0))
                    .append(name.substring(1).toLowerCase());
        output  .append(", cost: ")
                .append(cost);
        if(!students.isEmpty())
            output.append(", students on here: ").append(students);
        return output.toString();
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public List<StudentColor> getStudents() {
        return students;
    }

    public String getDescription() {
        return description;
    }

}
