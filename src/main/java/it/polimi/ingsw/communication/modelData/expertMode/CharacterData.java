package it.polimi.ingsw.communication.modelData.expertMode;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.expertLogic.character.charTypes.StandardCharacter;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

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

    public String toString(CharacterUtility characterUtility){
        StringBuilder output = new StringBuilder();
//        if(characterUtility.name().equals(name))
//            output  .append("\u0033[48;2;252;233;79m")
//                    .append(name.charAt(0))
//                    .append(name.substring(1).toLowerCase())
//                    .append("\u0033[0m");
//        else
//            output  .append(name.charAt(0))
//                    .append(name.substring(1).toLowerCase());
//        output  .append(", cost: ")
//                .append(cost);
        if(students!=null){
            output.append(", students on here: ").append(students);
        }
        return output.toString();
    }
}
