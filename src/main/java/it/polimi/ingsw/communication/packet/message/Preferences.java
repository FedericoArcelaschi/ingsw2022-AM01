package it.polimi.ingsw.communication.packet.message;

import it.polimi.ingsw.server.controller.GameType;

import java.util.Objects;

public class Preferences extends Message {
    private final String username;
    private final GameType gameType;

    public Preferences(String username, int nPlayer, boolean expertMode) throws IllegalAccessException {
        super(MessageType.PREFERENCES);
        this.username = username;
        this.gameType = GameType.getGameType(nPlayer, expertMode);
    }

    public String username() {
        return username;
    }

    public int nPlayer() {
        return gameType.nPlayer;
    }

    public boolean expertMode() {
        return gameType.expertMode;
    }

    public GameType getGameType() {
        return gameType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Preferences) obj;
        return this.username.equals(that.username) &&
                this.gameType == that.gameType;
    }

    @Override
    public String toString() {
        return "Preferences: " +
                "username: " + username + ", " +
                "number of players: " + gameType.nPlayer + ", " +
                (expertMode() ? "expert mode" : "base mode");
    }
}