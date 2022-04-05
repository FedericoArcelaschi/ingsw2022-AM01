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
    }

    public void testNextTurnPlanification() {
    }

    public void testNextTurnAction() {
    }
}