package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
        Game g = new Game(1234, players);
        String where = "Island";
        String what = "Yellow, Blue, Red";
        String id = "1";
        String[] string = {where, what, id};
        Command command = new Command(players.get(0), CommandType.MOVE_STUDENT, string);
        assertEquals( "The students have been moved to the chosen island.", g.executeCommand(command));
    }
}
