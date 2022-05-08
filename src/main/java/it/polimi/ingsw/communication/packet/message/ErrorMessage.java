package it.polimi.ingsw.communication.packet.message;

public class ErrorMessage extends Message {
    private int errorCode; //TODO: UPDATE ERROR CODES
    private String message;

    public ErrorMessage(int errorCode, String message) {
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
