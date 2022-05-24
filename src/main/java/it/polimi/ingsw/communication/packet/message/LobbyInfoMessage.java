package it.polimi.ingsw.communication.packet.message;

public class LobbyInfoMessage extends Message{
    private String message;

    public LobbyInfoMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
