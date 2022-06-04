package it.polimi.ingsw.communication.command;

public enum CommandType {
    PLAY_CARD("playcard"),
    CHOOSE_CLOUD("choosecloud"),
    MOVE_STUDENT_TO_CASTLE("movestudentcastle"),
    MOVE_STUDENT_TO_ISLAND("movestudentisland"),
    MOVE_MOTHER_NATURE("movemothernature"),
    PAY_CHARACTER("paychar"),
    CHARACTER_INFO("charinfo");

    private final String commandString;

    CommandType(String commandString) {
        this.commandString = commandString;
    }

    static CommandType getCommandType(String commandString) {
        for (CommandType ct : CommandType.values()) {
            if (ct.commandString.equalsIgnoreCase(commandString)) {
                return ct;
            }
        }
        throw new IllegalArgumentException("Command not valid. Please use 'help' to get a list of valid commands.");
    }

    public String getCommandString(){
        return commandString;
    }
}
