package it.polimi.ingsw.communication.packet.message;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class Ping implements Message {

    private final UUID id;
    private final Timestamp time;

    public Ping(UUID id, Timestamp time) {
        this.time = time;
        this.id = id;
    }

    public Ping(){
        this(UUID.randomUUID(), new Timestamp(System.currentTimeMillis()));
    }

    public Ping(UUID id){
        this(id, new Timestamp(System.currentTimeMillis()));
    }

    public Ping(@NotNull Ping ping) {
        this(ping.id(), ping.time());
    }

    public UUID id(){
        return id;
    }

    public Timestamp time() {
        return time;
    }

    @Override
    public String toString() {
        return "time: " + time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Ping) obj;
        return Objects.equals(this.id, that.id());
    }

}
