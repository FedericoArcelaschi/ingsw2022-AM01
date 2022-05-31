package it.polimi.ingsw.communication.packet;

public enum CommandAttribute {
    ID, //Numeric attribute
    DISTANCE, //Distance, expressed in number of islands, based on how far we are moving Mother Nature
    STUDENTS, //student list
    WHERE, //new student location
    WHAT,
    WHO
}
