package it.polimi.ingsw.client.userInterface.gui;

import it.polimi.ingsw.communication.message.subclasses.Preferences;

public record LoginPreferences(String IP, int port, Preferences preferences) {}
