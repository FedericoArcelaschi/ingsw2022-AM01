package it.polimi.ingsw.communication.command;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterInputs;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.*;

import static it.polimi.ingsw.communication.command.CommandType.getCommandType;

/**
 * a representation of the command the player can compose and ask to be executed
 */
public class Command {
    //all numbers should be passed as they are showed on the view
    private final CommandType type;
    private String username;
    private final List<StudentColor> students = new ArrayList<>();
    private int motherNaturePositionShift;
    /**
     * cardId between 1 and 10
     */
    private int cardId;
    /**
     * islandId between 1 and 12
     */
    private int islandId;
    /**
     * islandId between 1 and 12
     */
    private int charId;
    /**
     * islandId between 1 and nPlayers
     */
    private int cloudId;

    public Command(CommandType type, List<String> attributes) throws ParseException {
        this.type = type;
        parseParameters(attributes);
    }

    public Command(String command) throws ParseException {
        command = command.strip();
        List<String> commandAttributes = new ArrayList<>(Arrays.stream(command.split("\\s+")).toList());
        type = getCommandType(commandAttributes.remove(0));
        if (commandAttributes.size() < 1)

            throw new ParseException("'" + command + "' isn't a valid command. Type 'help' to get more information.", 0);
        parseParameters(commandAttributes);
    }

    private void parseParameters(List<String> attributes) throws ParseException {
        try {
            switch (type) {
                case PLAY_CARD -> cardId = Integer.parseInt(attributes.get(0));
                case MOVE_STUDENT_TO_CASTLE -> {
                    for (String attribute : attributes)
                        students.add(StudentColor.parseColor(attribute));
                }
                case MOVE_STUDENT_TO_ISLAND -> {
                    islandId = Integer.parseInt(attributes.get(0));
                    attributes.remove(0);
                    for (String attribute : attributes)
                        students.add(StudentColor.parseColor(attribute));
                }
                case MOVE_MOTHER_NATURE -> motherNaturePositionShift = Integer.parseInt(attributes.get(0));
                case CHOOSE_CLOUD -> cloudId = Integer.parseInt(attributes.get(0));
                case PAY_CHARACTER -> {
                    charId = CharacterUtility.getChar(attributes.remove(0)).getId();
                    Set<Integer> studentsToGet = CharacterInputs.getChar(charId).getNumberOfStudent();
                    Set<Type> inputToGet = CharacterInputs.getChar(charId).getTypes();
                    Set<Type> inputs = new HashSet<>();
                    for (String s : attributes) {
                        if (isInteger(s)) {
                            islandId = Integer.parseInt(s);
                            inputs.add(Integer.class);
                        } else {
                            students.add(StudentColor.parseColor(s));
                            inputs.add(StudentColor.class);
                        }
                    }
                    if(!inputs.containsAll(inputToGet) || !inputToGet.containsAll(inputs))
                        throw new IllegalArgumentException("not the right parameters. Required: " +
                                (inputToGet.isEmpty() ? "none" :
                                inputToGet.toString().replace("[", "").replace("]", "")));
                    if(studentsToGet != null)
                        if(!studentsToGet.contains(students.size()))
                            throw new IllegalArgumentException("not the right number of students. Required: " + studentsToGet.toString().replace("[", "").replace("]", ""));
                }
            }
        } catch (NumberFormatException e) {
            throw new ParseException("'"+attributes.get(0)+"' is not a valid number. Please insert a number. Type help for further information.", 0);
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String username() {
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

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
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
