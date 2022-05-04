package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class TurnTest{
    @Test
    public void testChangePhase() {
        try {
            List<String> players = Arrays.asList("lore", "fede", "gio");
            Turn t = new Turn(players);
            Board b = BoardFactory.getBoard(players, t);
            b.playCard("lore", 3);
            t.changePhase();
            b.playCard("fede", 4);
            t.changePhase();
            b.playCard("gio", 1);
            t.changePhase();
            List<String> order = Arrays.asList("gio", "lore", "fede");
            assertEquals(order, t.getActionOrder());
            assertEquals(TurnPhase.STUDENTS, t.getCurrentPhase());
            assertEquals("gio", t.getCurrentPlayer());
            t.changePhase();
            assertEquals(TurnPhase.MOTHERNATURE, t.getCurrentPhase());
            assertEquals("gio", t.getCurrentPlayer());
            t.changePhase();
            assertEquals(TurnPhase.CLOUD, t.getCurrentPhase());
            assertEquals("gio", t.getCurrentPlayer());
            t.changePhase();
            assertEquals(TurnPhase.STUDENTS, t.getCurrentPhase());
            assertEquals("lore", t.getCurrentPlayer());
            t.changePhase();
            assertEquals(TurnPhase.MOTHERNATURE, t.getCurrentPhase());
            assertEquals("lore", t.getCurrentPlayer());
            t.changePhase();
            t.changePhase();
            assertEquals("fede", t.getCurrentPlayer());
            assertEquals(TurnPhase.STUDENTS, t.getCurrentPhase());
            t.changePhase();
            t.changePhase();
            t.changePhase();
            assertEquals(TurnPhase.PLANNING, t.getCurrentPhase());
            assertEquals("gio", t.getCurrentPlayer());
            b.playCard("gio", 10);
            t.changePhase();
            b.playCard("lore", 1);
            t.changePhase();
            b.playCard("fede", 9);
            t.changePhase();
            List<String> newOrder = Arrays.asList("lore", "fede", "gio");
            assertEquals(newOrder, t.getActionOrder());
        } catch (NotYourTurnException e) {
            e.printStackTrace();
        }
    }
}