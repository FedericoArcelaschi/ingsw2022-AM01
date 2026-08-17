package it.polimi.ingsw.communication;

/**
 * Generic severity tag for end-of-game / informational messages sent over the wire.
 * Mirrors the value set of {@code javafx.scene.control.Alert.AlertType} without
 * depending on JavaFX, so it can be shared by non-JavaFX clients.
 */
public enum AlertType {
    NONE,
    INFORMATION,
    WARNING,
    CONFIRMATION,
    ERROR
}
