package it.polimi.ingsw.model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TurnTest extends TestCase {

    public void testGetTurn() {
        List<String> players = Arrays.asList("pippo","pluto","paperino");
        Turn turn = new Turn(players);
        for (String pl: players) {
            assertEquals(turn.getTurn(), pl);
            turn.nextTurnPlanification();
        }
    }

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

    public void testNextTurnPlanification() {
        List<String> players = Arrays.asList("pippo","pluto","paperino");
        Turn turn = new Turn(players);
        turn.setTurnAction(players);
        turn.nextTurnPlanification();
        assertEquals("pluto", turn.getTurn());
    }

    public void testNextTurnAction() {
    }
}