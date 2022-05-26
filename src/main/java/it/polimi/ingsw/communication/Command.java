package it.polimi.ingsw.communication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * a representation of the command the player can compose and ask to be executed
 */
public class Command {
    private CommandType type;
    private String username;
    private Map<CommandAttribute, String> attributesMap;

    public Command(String username, CommandType type, String[] attributes) {
        this.type = type;
        this.attributesMap = new HashMap<>();
        this.username = username;
        switch (type){
            case PLAY_CARD -> attributesMap.put(CommandAttribute.ID, attributes[0]);
            //e.g.: playcard 1
            case MOVE_STUDENT_TO_DININGROOM -> {
                attributesMap.put(CommandAttribute.WHAT, String.join("", attributes)); //What **has** to be a list of students
                System.out.println(attributesMap.get(CommandAttribute.WHAT));
            }
            //e.g.: movestudentdiningroom green, blue, pink
            case MOVE_STUDENT_TO_ISLAND -> {
                attributesMap.put(CommandAttribute.WHERE, attributes[0]);  //Number of the island
                attributesMap.put(CommandAttribute.WHAT, String.join("", Arrays.copyOfRange(attributes, 1, attributes.length)));  //List of students
            }
            //e.g.: movestudentisland 4, green, blue, pink
            case MOVE_MOTHER_NATURE -> attributesMap.put(CommandAttribute.DISTANCE, attributes[0]);  //How far mother nature will move
            //e.g.: movemothernature 1
            case CHOOSE_CLOUD ->  attributesMap.put(CommandAttribute.ID, attributes[0]);
            //e.g.: cloud 1
        }
    }

    public static Command createCommand(String username, String command){
        String[] splitCommand = command.split(" ");
        for (CommandType ct : CommandType.values()) {
            if(splitCommand[0].toLowerCase().equals(ct.getCommandString()))
                return new Command(username, ct, Arrays.copyOfRange(splitCommand, 1, splitCommand.length));
        }
        //Returns null if it doesn't find the specified command.
        return null;
    }

    public CommandType getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public Map<CommandAttribute, String> getAttributesMap() {
        return attributesMap;
    }

    public String toString(){
        StringBuilder attribute = new StringBuilder();
        for(String a : attributesMap.values()) attribute.append(" ").append(a);
        return type.getCommandString() + attribute;
    }
}
