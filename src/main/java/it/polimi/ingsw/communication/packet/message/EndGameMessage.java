package it.polimi.ingsw.communication.packet.message;

public class EndGameMessage implements Message{
    private final String cause;

    public EndGameMessage(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}
