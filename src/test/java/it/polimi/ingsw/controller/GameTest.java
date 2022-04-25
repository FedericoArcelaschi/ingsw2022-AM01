package it.polimi.ingsw.controller;

import it.polimi.ingsw.communication.Command;
import it.polimi.ingsw.communication.CommandType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testExecuteCommand(){
        List<String> players = new ArrayList<>();
        players.add("L");
        players.add("F");
        players.add("G");
        Game g = new Game(1234, players, null);
        String where = "Island";
        String what = "Yellow, Blue, Red";
        String id = "1";
        String[] string = {where, what, id};
        Command command = new Command(players.get(0), CommandType.MOVE_STUDENT, string);
        assertEquals( "The students have been moved to the chosen island.", g.executeCommand(command));
    }
}
