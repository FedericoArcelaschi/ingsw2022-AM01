package it.polimi.ingsw.communication;

public enum CommandType {
    PLAY_CARD("playcard"),
    CHOOSE_CLOUD("choosecloud"),
    MOVE_STUDENT_TO_DININGROOM("movestudentdiningroom"),
    MOVE_STUDENT_TO_ISLAND("movestudentisland"),
    MOVE_STUDENT("movestudent"),
    MOVE_MOTHER_NATURE("movemothernature"),
    PAY_CHARACTER("paychar");

    private final String commandString;

    CommandType(String commandString) {
        this.commandString = commandString;
    }

    public String getCommandString(){
        return commandString;
    }
}
