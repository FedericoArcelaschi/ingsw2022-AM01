package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

public class TurnTest{
    @Test
    public void testChangePhase() {
        try {
            List<String> players = Arrays.asList("lore", "fede", "gio");
            Turn t = new Turn(players);
            Board b = new Board("lore", "fede", "gio", t, RandomGenerator.getDefault().nextLong());
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
            //chooseCloud
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
        } catch (NotYourTurnException | PhaseNotRightException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setNewRoundTest() {
        List<String> players = Arrays.asList("lore", "fede", "gio");
        Turn t = new Turn(players);
        Map<String, Card> mapPlayersCard
                = new HashMap<>(
                        Map.of("fede", new Card(1),
                    "lore", new Card(2),
                    "gio", new Card(3)));
        t.setNewRound(mapPlayersCard);
        System.out.println(t.getActionOrder());
    }
}