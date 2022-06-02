package it.polimi.ingsw.communication.packet.message;

public class Error implements Message {
    private final int errorCode; //TODO: UPDATE ERROR CODES
    private final String message;

    public Error(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
