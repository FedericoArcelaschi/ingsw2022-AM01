package it.polimi.ingsw.communication.command;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

import java.util.*;

import static it.polimi.ingsw.communication.command.CommandType.getCommandType;

/**
 * a representation of the command the player can compose and ask to be executed
 */
public class Command {
    private final CommandType type;
    private final String username;
    private int motherNaturePositionShift = 0;
    private int cardId = 0;
    private List<StudentColor> students;
    private int islandId = 0;
    private int charId;
    private int cloudId = 0;

    public Command(String username, CommandType type, String[] attributes) {
        this.type = type;
        this.username = username;
        if (Arrays.stream(attributes).toList().size() < 1)
            throw new IllegalArgumentException(" needed X arguments. actual: 0");
        switch (type) {
            case PLAY_CARD -> cardId = Integer.parseInt(attributes[0].strip());
            //e.g.: playcard 1
            case MOVE_STUDENT_TO_CASTLE -> {
                students = Arrays.stream(attributes).map(StudentColor::getColor).toList();
            }
            //e.g.: moveStudentCastle Green, Blue, Pink
            case MOVE_STUDENT_TO_ISLAND -> {
                if(isInteger(attributes[0])) {
                    islandId = Integer.parseInt(attributes[0].strip()); //TODO try catch exception.
                    students = Arrays.stream(Arrays.copyOfRange(attributes, 1, attributes.length)).map(StudentColor::getColor).toList();
                }else{
                    students = Arrays.stream(Arrays.copyOfRange(attributes, 1, attributes.length-1)).map(StudentColor::getColor).toList();
                    islandId = Integer.parseInt(attributes[attributes.length-1].strip());
                }
            }
            //e.g.: moveStudentIsland 4, green, blue, pink
            case MOVE_MOTHER_NATURE -> motherNaturePositionShift = Integer.parseInt(attributes[0].strip());
            //e.g.: moveMotherNature 1
            case CHOOSE_CLOUD -> cloudId = Integer.parseInt(attributes[0].strip());
            //e.g.: cloud 1
            case PAY_CHARACTER -> {
                charId = CharacterUtility.getChar(attributes[0].strip()).getId();
                //For now, always students first, islandNumber later.
                if(attributes.length==2){  //If there are only 2 attributes (name of character + something else)
                    if(isInteger(attributes[1])) {  //if the first attribute is an island index
                        islandId = Integer.parseInt(attributes[1].strip());  //save island id
                        students = new ArrayList<>();  //the student list is empty
                    } else {
                        islandId = 0;  //then the island id is null
                        students = new ArrayList<>();
                        students.add(StudentColor.getColor(attributes[1]));  //and I insert a single student in the list
                    }
                }else if (attributes.length>2){  //if the list of attributes is not 2
                    //It means that there must be a list of students
                    students = new ArrayList<>(Arrays.stream(Arrays.copyOfRange(attributes, 1, attributes.length-1)).map(StudentColor::getColor).toList());
                    //If the last element is numeric it must be an island index
                    if (isInteger(attributes[attributes.length - 1])) {
                        islandId = Integer.parseInt(attributes[attributes.length - 1]);
                    }
                    else {
                        //If it is not then the user only wrote a list of students with no student place.
                        students.add(StudentColor.getColor(attributes[attributes.length-1]));
                        islandId = 0; //0 is NOT in island range
                    }
                }else {
                    students = new ArrayList<>();
                }
            }
            //e.g.: payChar Monk Green 0
        }
    }

    public Command(String username, CommandType type, List<String> attributes) {
        this.type = type;
        this.username = username;
        if (attributes.size() < 1)
            throw new IllegalArgumentException(" needed X arguments. actual: 0");
        switch (type) {
            case PLAY_CARD -> cardId = Integer.parseInt(attributes.get(0).strip());
            //e.g.: playcard 1
            case MOVE_STUDENT_TO_CASTLE -> {
                students = attributes.stream().map(StudentColor::getColor).toList();
            }
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
                if(isInteger(attributes.get(1)) && attributes.size()==2){  //If there is only a number as parameter and nothing else
                    //then that number is where and the what is null.
                    islandId = Integer.parseInt(attributes.get(1).strip())-1;
                    students = new ArrayList<>();
                }else {  //If neither the first parameter is a number and the list of parameters is exactly 2
                    //It means that there must be a list of students
                    students = attributes.subList(1, attributes.size()-2).stream().map(StudentColor::getColor).toList();
                    //If the last element is numeric it must be an island index
                    if (isInteger(attributes.get(attributes.size() - 1)))
                        islandId = Integer.parseInt(attributes.get(attributes.size() - 1))-1;
                    else {
                        //If it is not then the user only wrote a list of students with no student place.
                        students.add(StudentColor.getColor(attributes.get(attributes.size() - 1)));
                    }
                }
            }
            //e.g.: payChar Monk Green 0
        }
    }


    public static Command createCommand(String username, String command) {
        String[] splitCommand = command.split(" ");
        //TODO: ct = getCommandString(splitCommand[0])
        CommandType commandType = getCommandType(splitCommand[0]);
        return new Command(username, commandType, Arrays.copyOfRange(splitCommand, 1, splitCommand.length));
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
        return "Command{" +
                "type=" + type +
                ", username='" + username + '\'' +
                ", motherNaturePositionShift=" + motherNaturePositionShift +
                ", cardId=" + cardId +
                ", students=" + students +
                ", islandId=" + islandId +
                ", charName='" + CharacterUtility.getChar(charId) + '\'' +
                ", cloudId=" + cloudId +
                "}";
    }
}
