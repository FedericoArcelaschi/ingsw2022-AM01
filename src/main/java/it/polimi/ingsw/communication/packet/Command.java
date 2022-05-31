package it.polimi.ingsw.communication.packet;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.communication.packet.CommandType.getCommandType;

/**
 * a representation of the command the player can compose and ask to be executed
 */
public class Command {
    private CommandType type;
    private String username;
    private Map<CommandAttribute, String> attributesMap;

    public Command(String username, @NotNull CommandType type, String[] attributes) {
        this.type = type;
        this.attributesMap = new HashMap<>();
        this.username = username;
        switch (type) {
            case PLAY_CARD -> attributesMap.put(CommandAttribute.ID, attributes[0]);
            //e.g.: playcard 1
            case MOVE_STUDENT_TO_CASTLE -> {
                attributesMap.put(CommandAttribute.WHAT, String.join(",", attributes)); //What will be a list of students.
                System.out.println(attributesMap.get(CommandAttribute.WHAT));
            }
            //e.g.: moveStudentCastle Green, Blue, Pink
            case MOVE_STUDENT_TO_ISLAND -> {
                attributesMap.put(CommandAttribute.WHERE, attributes[0]);  //Number of the island
                attributesMap.put(CommandAttribute.WHAT, String.join("", Arrays.copyOfRange(attributes, 1, attributes.length)));  //List of students
            }
            //e.g.: moveStudentIsland 4, green, blue, pink
            case MOVE_MOTHER_NATURE -> attributesMap.put(CommandAttribute.DISTANCE, attributes[0]);  //How far mother nature will move
            //e.g.: moveMotherNature 1
            case CHOOSE_CLOUD -> attributesMap.put(CommandAttribute.ID, attributes[0]);
            //e.g.: cloud 1
            case PAY_CHARACTER -> {
                attributesMap.put(CommandAttribute.WHO, attributes[0]);
                attributesMap.put(CommandAttribute.WHAT, String.join("", Arrays.copyOfRange(attributes, 1, attributes.length)));
            }
            //e.g.: payChar Monk Green
            case MORE -> {}
            //e.g: more Monk
        }
    }

    public static Command createCommand(String username, String command) {
        String[] splitCommand = command.split(" ");
        //TODO: ct = getCommandString(splitCommand[0])
        CommandType commandType = getCommandType(splitCommand[0]);
        if(commandType != null)
            return new Command(username, commandType, Arrays.copyOfRange(splitCommand, 1, splitCommand.length));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(splitCommand[0]).append(" is not a valid command type, available commands are:");
        for (CommandType ct : CommandType.values()) {
            stringBuilder.append(ct.getCommandString()).append("\n");
        }
        System.out.println(stringBuilder);
        return null;
        //Returns null if it doesn't find the specified command.
    }


    public String getUsername() {
        return username;
    }

    public CommandType getType() {
        return type;
    }

    public Map<CommandAttribute, String> getAttributesMap() {
        return attributesMap;
    }

    public String toString(){
        StringBuilder attribute = new StringBuilder();
        for(String a : attributesMap.values()) attribute.append(" ").append(a);
        //FIXME: don't think was required return type.getCommandString() + attribute;
        return attribute.toString();
    }
}
