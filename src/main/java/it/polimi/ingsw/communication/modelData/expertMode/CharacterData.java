package it.polimi.ingsw.communication.modelData.expertMode;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;

import java.util.ArrayList;
import java.util.List;

public class CharacterData {
    private String name;
    private int cost;
    private List<StudentColor> students;
    private String description;

    public CharacterData(String name, int cost, List<StudentColor> students, String description){
        this.name = name;
        this.cost = cost;
        if(students!=null)
            this.students = new ArrayList<>(students);
        else
            this.students = null;
        this.description = description;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append(name).append(", cost: ").append(cost);
        if(students!=null){
            output.append(", students on here: ").append(students);
        }
        return output.toString();
    }
}
