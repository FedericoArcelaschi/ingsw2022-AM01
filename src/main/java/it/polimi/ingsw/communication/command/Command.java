package it.polimi.ingsw.communication.command;

import it.polimi.ingsw.server.model.baseLogic.StudentColor;
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterUtility;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;
import java.util.function.Supplier;

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
    private List<StudentColor> students = new ArrayList<>();
    private int islandId;
    private int charId;
    private int cloudId;

    public Command(String username, CommandType type, List<String> attributes) throws ParseException {
        this.type = type;
        this.username = username;
        getParameters(attributes);
    }

    public Command(String username, String command) throws ParseException {
        this.username = username;
        command = command.strip();
        List<String> commandAttributes = new ArrayList<>(Arrays.stream(command.split(" ")).toList()); // FIXME: more spaces break the command.
        if (commandAttributes.size() < 2)
            throw new ParseException(commandAttributes + "put a valid command. help to get more information.", 0);
        type = getCommandType(commandAttributes.remove(0));
        getParameters(commandAttributes);
    }

    private void getParameters(List<String> attributes) throws ParseException {
        try {
            switch (type) {
                case PLAY_CARD -> cardId = Integer.parseInt(attributes.get(0));
                case MOVE_STUDENT_TO_CASTLE -> {
                    for (String attribute : attributes)
                        students.add(StudentColor.parseColor(attribute));
                }
                case MOVE_STUDENT_TO_ISLAND -> {
                    islandId = Integer.parseInt(attributes.get(0).strip());
                    attributes.remove(0);
                    for (String attribute : attributes)
                        students.add(StudentColor.parseColor(attribute));
                }
                case MOVE_MOTHER_NATURE -> motherNaturePositionShift = Integer.parseInt(attributes.get(0));
                case CHOOSE_CLOUD -> cloudId = Integer.parseInt(attributes.get(0));
                case PAY_CHARACTER -> {
                    charId = CharacterUtility.getChar(attributes.remove(0)).getId();
                    for (String s : attributes) {
                        if (isInteger(s)) {
                            islandId = Integer.parseInt(s);
                        } else {
                            students.add(StudentColor.parseColor(s));
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            throw new ParseException("'"+attributes.get(0)+"' is not a valid number. Please insert a number. Type help for further information.", 0);
        }
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
