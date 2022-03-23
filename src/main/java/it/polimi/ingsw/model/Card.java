package it.polimi.ingsw.model;

public class Card {
    private final int priority;
    private final int distance;
    private boolean played;

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public boolean isPlayed() {
        return played;
    }

    public Card(int priority, int distance) {
        this.priority = priority;
        this.distance = distance;
        this.played = false;
    }

    public int getPriority() {
        return priority;
    }

    public int getDistance() {
        return distance;
    }
}
