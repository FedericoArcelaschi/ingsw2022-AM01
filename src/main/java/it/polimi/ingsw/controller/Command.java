package it.polimi.ingsw.controller;

import java.util.HashMap;
import java.util.Map;

public class Command {
    private CommandType type;
    private Map<CommandAttribute, String> attributesMap;

    public Command(CommandType type, String[] attributes) {
        this.type = type;
        this.attributesMap = new HashMap<>();
        switch (type){
            case PLAY_CARD -> attributesMap.put(CommandAttribute.ID, attributes[1]);
            case MOVE_STUDENT_TO_ISLAND -> attributesMap.put(CommandAttribute.ID, attributes[1]);
        }
    }
}
