package it.polimi.ingsw.model.baseLogic;

public final class Card {
    private final int priority;
    private final int distance;
    private boolean isAvailable;

    public Card(int priority, int distance, boolean isAvailable) {
        this.priority = priority;
        this.distance = distance;
        this.isAvailable = isAvailable;
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
        return this.priority == that.priority &&
                this.distance == that.distance &&
                this.isAvailable == that.isAvailable;
    }

    @Override
    public String toString() {
        return "[" + priority + ", " + distance + "]";
    }
}
