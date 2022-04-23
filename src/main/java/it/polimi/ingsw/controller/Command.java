package it.polimi.ingsw.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * a representation of the command the player can compose and ask to be executed
 */
public class Command {
    private final CommandType type;
    private final String playerID;
    private final Map<CommandAttribute, String> attributesMap;

    public Command(String playerID, CommandType type, String[] attributes) {
        this.type = type;
        this.attributesMap = new HashMap<>();
        this.playerID = playerID;
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

    public CommandType getType() {
        return type;
    }

    public String getPlayerID() {
        return playerID;
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
