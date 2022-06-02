package it.polimi.ingsw.communication.packet.message;

public class CharInfo implements Message {
    private final String info;

    public CharInfo(String cause) {
        this.info = cause;
    }

    public String getInfo() {
        return info;
    }
}
