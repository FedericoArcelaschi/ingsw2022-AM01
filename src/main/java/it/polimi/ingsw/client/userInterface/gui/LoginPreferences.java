package it.polimi.ingsw.client.userInterface.gui;

import it.polimi.ingsw.communication.message.subclasses.Preferences;

public class LoginPreferences {
    private final String IP;
    private final int port;
    private final Preferences preferences;

    public LoginPreferences(String IP, int port, Preferences preferences) {
        this.IP = IP;
        this.port = port;
        this.preferences = preferences;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return port;
    }

    public Preferences getPreferences() {
        return preferences;
    }
}
