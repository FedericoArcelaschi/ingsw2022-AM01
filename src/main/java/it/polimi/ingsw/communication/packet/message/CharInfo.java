package it.polimi.ingsw.communication.packet.message;

public class CharInfo extends Message {
    private final String info;

    public CharInfo(String cause) {
        super(MessageType.CHARINFO);
        this.info = cause;
    }

    public String getInfo() {
        return info;
    }
}
