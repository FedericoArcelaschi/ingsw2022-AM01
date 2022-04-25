package it.polimi.ingsw.communication;

public enum CommandType {
    PLAY_CARD("playcard"),
    CHOOSE_CLOUD("choosecloud"),
    MOVE_STUDENT("movestudent"),
    MOVE_MOTHER_NATURE("movemothernature"),
    GET("get");

    private final String commandString;

    CommandType(String commandString) {
        this.commandString = commandString;
    }

    public String getCommandString(){
        return commandString;
    }
}
