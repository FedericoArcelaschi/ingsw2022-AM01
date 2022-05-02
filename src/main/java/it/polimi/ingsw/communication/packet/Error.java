package it.polimi.ingsw.communication.packet;

public final class Error extends Message {
    private int errorCode;
    private String message;

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
