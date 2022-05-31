package it.polimi.ingsw.communication.packet.message;

import java.util.Objects;

public class Preferences implements Message {
    private final String username;
    private final int nPlayer;
    private final boolean expertMode;

    public Preferences(String username, int nPlayer, boolean expertMode) {
        this.username = username;
        this.nPlayer = nPlayer;
        this.expertMode = expertMode;
    }

    public String username() {
        return username;
    }

    public int nPlayer() {
        return nPlayer;
    }

    public boolean expertMode() {
        return expertMode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Preferences) obj;
        return this.username.equals(that.username) &&
                this.nPlayer == that.nPlayer &&
                this.expertMode == that.expertMode;
    }

    @Override
    public String toString() {
        return "Preferences: " +
                "username: " + username + ", " +
                "number of players: " + nPlayer + ", " +
                (expertMode ? "expert mode" : "base mode");
    }
}