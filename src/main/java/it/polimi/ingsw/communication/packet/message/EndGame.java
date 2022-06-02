package it.polimi.ingsw.communication.packet.message;

public class EndGame implements Message{
    private final String cause;

    public EndGame(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}
