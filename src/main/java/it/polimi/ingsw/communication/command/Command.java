package it.polimi.ingsw.communication.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.communication.command.CommandType.getCommandType;

/**
 * a representation of the command the player can compose and ask to be executed
 */
public class Command {
    //TODO: rework attributes map to invoke lambdas.
    private CommandType type;
    private String username;
    private Map<CommandAttribute, String> attributesMap;

    public Command(String username, CommandType type, String[] attributes) {
        this.type = type;
        this.attributesMap = new HashMap<>();
        this.username = username;
        if(Arrays.stream(attributes).toList().size() < 0)
            throw new IllegalArgumentException(" needed X arguments. actual: 0");
        switch (type) {
            case PLAY_CARD -> attributesMap.put(CommandAttribute.ID, attributes[0]);
            //e.g.: playcard 1
            case MOVE_STUDENT_TO_CASTLE -> {
                attributesMap.put(CommandAttribute.WHAT, String.join("", attributes)); //What will be a list of students.
                //System.out.println(attributesMap.get(CommandAttribute.WHAT));
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
                //For now, always students first, islandNumber later.
                if(isInteger(attributes[1]) && attributes.length==2){  //If there is only a number as parameter and nothing else
                    //then that number is where and the what is null.
                    attributesMap.put(CommandAttribute.WHERE, attributes[1]);
                    attributesMap.put(CommandAttribute.WHAT, "");
                }else {  //If neither the first parameter is a number and the list of parameters is exactly 2
                    //It means that there must be a list of students
                    attributesMap.put(CommandAttribute.WHAT, String.join("", Arrays.copyOfRange(attributes, 1, attributes.length - 2)));
                    //If the last element is numeric it must be an island index
                    if (isInteger(attributes[attributes.length - 1]))
                        attributesMap.put(CommandAttribute.WHERE, attributes[attributes.length - 1]);
                    else {
                        //If it is not then the user only wrote a list of students with no student place.
                        attributesMap.put(CommandAttribute.WHAT, attributesMap.get(CommandAttribute.WHAT) + attributes[attributes.length - 1]);
                        attributesMap.put(CommandAttribute.WHERE, "");
                    }
                }
            }
            //e.g.: payChar Monk Green 0
            case CHARACTER_INFO -> attributesMap.put(CommandAttribute.WHO, attributes[0]);
            //e.g: charinfo Monk
        }
    }

    public static Command createCommand(String username, String command) {
        String[] splitCommand = command.split(" ");
        //TODO: ct = getCommandString(splitCommand[0])
        CommandType commandType = getCommandType(splitCommand[0]);
        return new Command(username, commandType, Arrays.copyOfRange(splitCommand, 1, splitCommand.length));
        //FIXME : spiegare a Giovanni l'utilità di un metodi Constructor statico
        //StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append(splitCommand[0]).append(" is not a valid command type, available commands are:\n");
        //for (CommandType ct : CommandType.values()) {
        //    stringBuilder.append(ct.getCommandString()).append("\n");
        //}
        //System.out.println(stringBuilder);
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

    //There is no built-in way of doing this...
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        for(CommandAttribute a : attributesMap.keySet()) {
            output.append(a);
            output.append(attributesMap.get(a));
        }
        return "type "+ type + " " + output;
    }
}
