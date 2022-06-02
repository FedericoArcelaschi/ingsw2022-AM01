package it.polimi.ingsw.communication.packet.message.command;

import java.awt.*;
import java.lang.reflect.Type;

public enum CommandAttribute {
    ID(Integer.class), //Numeric attribute
    DISTANCE(Integer.class), //Distance, expressed in number of islands, based on how far we are moving Mother Nature
    STUDENTS(List.class), //student list
    WHERE, //new student location
    WHAT,
    WHO;

    private final Type t;
    CommandAttribute(Type t) {
        this.t = t;
    }

    CommandAttribute() {
        t = null;
    }
}
