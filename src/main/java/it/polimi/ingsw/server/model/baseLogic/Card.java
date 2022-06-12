package it.polimi.ingsw.server.model.baseLogic;

public final class Card {
    private final int priority;
    private final int distance;
    private boolean isAvailable;

    public Card(int priority) {
        this.priority = priority;
        this.distance = (priority + 1 ) / 2;
        this.isAvailable = true;
    }

    public int priority() {
        return priority;
    }

    public int distance() {
        return distance;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Card) obj;
        return this.priority == that.priority;
    }

    @Override
    public String toString() {
        return "[" + priority + ", " + distance + "]";
    }
}
