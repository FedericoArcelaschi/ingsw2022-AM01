package it.polimi.ingsw.communication.command;

import java.text.ParseException;

public enum CommandType {
    PLAY_CARD("playcard"),
    CHOOSE_CLOUD("choosecloud"),
    MOVE_STUDENT_TO_CASTLE("movestudentcastle"),
    MOVE_STUDENT_TO_ISLAND("movestudentisland"),
    MOVE_MOTHER_NATURE("movemothernature"),
    PAY_CHARACTER("paychar");

    private final String commandString;

    CommandType(String commandString) {
        this.commandString = commandString;
    }

    static CommandType getCommandType(String commandString) throws ParseException {
        for (CommandType ct : CommandType.values()) {
            if (ct.commandString.equalsIgnoreCase(commandString)) {
                return ct;
            }
        }
        throw new ParseException("'" + commandString + "' is not a valid command. Please use 'help' to get a list of valid commands.", 0);
    }

    public String getCommandString(){
        return commandString;
    }
}
