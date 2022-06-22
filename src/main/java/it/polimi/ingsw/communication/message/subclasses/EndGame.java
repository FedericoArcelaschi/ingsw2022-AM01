package it.polimi.ingsw.communication.message.subclasses;

import it.polimi.ingsw.communication.message.Message;
import it.polimi.ingsw.communication.message.MessageType;
import it.polimi.ingsw.server.model.baseLogic.Team;
import javafx.scene.control.Alert.AlertType;

public class EndGame extends Message {
    private final String cause;
    private final AlertType alertType;
    private final String endGamePlayer;
    private final Team winnerTeam;

    public EndGame(String cause, AlertType alertType, String winner, Team winnerTeam) {
        super(MessageType.END);
        this.cause = cause;
        this.alertType = alertType;
        this.endGamePlayer = winner;
        this.winnerTeam = winnerTeam;
        //this.cause = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    }

    public String getCause() {
        return cause;
    }

}
