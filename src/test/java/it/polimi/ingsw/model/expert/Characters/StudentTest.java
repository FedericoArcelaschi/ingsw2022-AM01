package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.expert.*;
import it.polimi.ingsw.model.expert.ExpertIsland;
import junit.framework.TestCase;
import java.lang.management.PlatformLoggingMXBean;
import java.util.List;
import java.util.Map;

public class StudentTest extends TestCase {
    /**
     * Tests adding a YELLOW to the island.
     * Problem: doesn't know for sure if there is a YELLOW in MONK
     */
    public void testApplyEffect4MONK() {
        ExpertBoard board = new ExpertBoard("pippo", "pluto");
        ExpertIsland testIsland = (ExpertIsland) board.getIslandList().get(1);
        board.setup4CharacterTesting(1);
        int countYellows =  testIsland.getStudents().get(Color.YELLOW).intValue();
        board.playExpertCard(1, testIsland, 0, List.of(Color.YELLOW));
        assertEquals(countYellows + 1, testIsland.getStudents().get(Color.YELLOW).intValue());
    }
}