package it.polimi.ingsw.communication;

import java.util.Objects;

public final class Preferences {
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
        return Objects.equals(this.username, that.username) &&
                this.nPlayer == that.nPlayer &&
                this.expertMode == that.expertMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, nPlayer, expertMode);
    }

    @Override
    public String toString() {
        return "Preferences[" +
                "username=" + username + ", " +
                "nPlayer=" + nPlayer + ", " +
                "expertMode=" + expertMode + ']';
    }
}