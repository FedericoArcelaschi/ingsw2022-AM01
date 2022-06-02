package it.polimi.ingsw.communication.packet.message;

public class CharInfoMessage implements Message {
    private final String info;

    public CharInfoMessage(String cause) {
        this.info = cause;
    }

    public String getInfo() {
        return info;
    }
}
