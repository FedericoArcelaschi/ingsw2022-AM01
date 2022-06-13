package it.polimi.ingsw.communication.command;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

import java.util.*;

import static it.polimi.ingsw.communication.command.CommandType.CHARINFO;
import static it.polimi.ingsw.communication.command.CommandType.getCommandType;

/**
 * a representation of the command the player can compose and ask to be executed
 */
public class Command {
    //all numbers should be passed as they are showed on the view
    private final CommandType type;
    private final String username;
    private int motherNaturePositionShift;
    private int cardId;
    private List<StudentColor> students;
    private int islandId;
    private int charId;
    private int cloudId;

    public Command(String username, CommandType type, List<String> attributes) {
        this.type = type;
        this.username = username;
        switch (type) {
            case PLAY_CARD -> cardId = Integer.parseInt(attributes.get(0).strip());
            //e.g.: playcard 1

            case MOVE_STUDENT_TO_CASTLE -> students = attributes.stream().map(StudentColor::getColor).toList();
            //e.g.: moveStudentCastle Green, Blue, Pink

            case MOVE_STUDENT_TO_ISLAND -> {
                islandId = Integer.parseInt(attributes.get(0).strip());
                students = attributes.subList(1, attributes.size()).stream().map(StudentColor::getColor).toList();
            }
            //e.g.: moveStudentIsland 4, green, blue, pink

            case MOVE_MOTHER_NATURE -> motherNaturePositionShift = Integer.parseInt(attributes.get(0).strip());
            //e.g.: moveMotherNature 1

            case CHOOSE_CLOUD -> cloudId = Integer.parseInt(attributes.get(0).strip());
            //e.g.: cloud 1

            case PAY_CHARACTER -> {
                charId = CharacterUtility.getChar(attributes.get(0).strip()).getId();
                if(attributes.size()==2){  //If there is only a number as parameter and nothing else
                    //then that number is where and the what is null.
                    if(isInteger(attributes.get(1))) {
                        islandId = Integer.parseInt(attributes.get(1).strip());
                        students = new ArrayList<>();
                    } else {
                        students = new ArrayList<>();
                        students.add(StudentColor.getColor(attributes.get(1).strip()));
                        islandId = 0;
                    }
                }else if (attributes.size()>2) {  //If neither the first parameter is a number and the list of parameters is exactly 2
                    //It means that there must be a list of students
                    students = new ArrayList<>(attributes.subList(1, attributes.size()-1).stream().map(StudentColor::getColor).toList());
                    //If the last element is numeric it must be an island index
                    if (isInteger(attributes.get(attributes.size() - 1)))
                        islandId = Integer.parseInt(attributes.get(attributes.size() - 1));
                    else {
                        //If it is not then the user only wrote a list of students with no student place.
                        students.add(StudentColor.getColor(attributes.get(attributes.size() - 1)));
                    }
                } else {
                    students = new ArrayList<>();
                    islandId = 0;
                }
            }
            //e.g.: payChar Monk Green 0
            case CHARINFO -> charId = CharacterUtility.getChar(attributes.get(0).strip()).getId();
        }
    }

    public static Command createCommand(String username, String command) {
        String[] splitCommand = command.split(" ");
        CommandType commandType = getCommandType(splitCommand[0]);
        List<String> commandAttributes = new ArrayList<>(Arrays.stream(splitCommand).toList());
        commandAttributes.remove(0);
        command = command.strip();
        if (commandAttributes.size() < 1)
            throw new IllegalArgumentException("put a valid command. help to get more information.");
        return new Command(
                username,
                commandType,
                commandAttributes);
    }


    public String getUsername() {
        return username;
    }

    public CommandType getType() {
        return type;
    }

    public int getMotherNaturePositionShift() {
        return motherNaturePositionShift;
    }

    public int getCardId() {
        return cardId;
    }

    public List<StudentColor> getStudents() {
        return students;
    }

    public int getIslandId() {
        return islandId;
    }

    public int getCharId() {
        return charId;
    }

    public int getCloudId() {
        return cloudId;
    }

    //There is no built-in way of doing this...
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Command{")
                .append("type=").append(type)
                .append(", username='").append(username).append('\'')
                .append(", motherNaturePositionShift=").append(motherNaturePositionShift)
                .append(", cardId=").append(cardId)
                .append(", students=").append(students)
                .append(", islandId=").append(islandId)
                .append(", charId='").append(charId != 0 ? CharacterUtility.getChar(charId) : "null").append('\'')
                .append(", cloudId=").append(cloudId)
                .append("}").toString();
    }
}
