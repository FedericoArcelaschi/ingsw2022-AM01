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
            case MOVE_STUDENT -> {
                attributesMap.put(CommandAttribute.WHERE, attributes[0]); //Where is a number
                attributesMap.put(CommandAttribute.WHAT, attributes[1]); //What **has** to be a list of students
                attributesMap.put(CommandAttribute.ID, attributes[2]);
            }
            case MOVE_MOTHER_NATURE -> attributesMap.put(CommandAttribute.DISTANCE, attributes[0]);
            case GET -> attributesMap.put(CommandAttribute.WHAT, attributes[0]);
        }
    }

    public static Command createCommand(String username, String command){
        String[] splitCommand = command.split(" ");
        for (CommandType ct : CommandType.values()) {
            if(splitCommand[0].toLowerCase().equals(ct.getCommandString()))
                return new Command(username, ct, Arrays.copyOfRange(splitCommand, 1, splitCommand.length));
        }
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
