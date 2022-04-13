package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TurnTest{
    @Test
    public void testGetTurn() {
        List<String> players = Arrays.asList("pippo","pluto","paperino");
        Turn turn = new Turn(players);
        for (String pl: players) {
            assertEquals(turn.getTurn(), pl);
            turn.nextTurnPlanification();
        }
    }
    @Test
    public void testSetTurnAction() {
        List<String> players = Arrays.asList("pippo","pluto","paperino");
        Turn turn = new Turn(players);
        players = Arrays.asList("pluto","paperino","pippo");
        turn.setTurnAction(players);
        for (String pl: players) {
            assertEquals(turn.getTurn(), pl);
            turn.nextTurnAction();
        }

    }
    @Test
    public void testNextTurnPlanification() {
        List<String> players = Arrays.asList("pippo","pluto","paperino");
        Turn turn = new Turn(players);
        turn.setTurnAction(players);
        turn.nextTurnPlanification();
        assertEquals("pluto", turn.getTurn());
    }
    @Test
    public void testNextTurnAction() {
    }
}