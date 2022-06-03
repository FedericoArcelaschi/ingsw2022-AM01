package it.polimi.ingsw.communication.packet.message;
public class EndGame extends Message {
    private final String cause;

    public EndGame (String cause) {
        super(MessageType.END);
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

}
