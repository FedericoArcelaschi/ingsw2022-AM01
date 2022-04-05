package it.polimi.ingsw.model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class TurnTest extends TestCase {

    public void testGetTurn() {
        List<String> players = new ArrayList<String>();
        String[] player = new String[3];
        player[0] = "pippo"; player[1]="pluto"; player[2]= "paperino";
        for (String pl: player) {players.add(pl);}
        Turn turn = new Turn(players);
        for (String pl: player) {
            assertEquals(turn.getTurn(), pl);
            turn.nextTurnPlanification();
            //loop all'infinito
        }
    }

    public void testSetTurnAction() {
    }

    public void testNextTurnPlanification() {
    }

    public void testNextTurnAction() {
    }
}