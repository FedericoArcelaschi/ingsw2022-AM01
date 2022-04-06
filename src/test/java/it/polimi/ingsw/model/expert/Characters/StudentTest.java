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

        if(board.playExpertCard(1, testIsland, 0, List.of(Color.YELLOW)))
            assertEquals(countYellows + 1, testIsland.getStudents().get(Color.YELLOW).intValue());
        //else
            //assertEquals(countYellows, testIsland.getStudents().get(Color.YELLOW).intValue());

        int countRed =  testIsland.getStudents().get(Color.RED).intValue();
        if(board.playExpertCard(1, testIsland, 0, List.of(Color.RED)))
            assertEquals(countYellows + 1, testIsland.getStudents().get(Color.RED).intValue());
        //else
            //assertEquals(countYellows, testIsland.getStudents().get(Color.YELLOW).intValue());

        int countBlue =  testIsland.getStudents().get(Color.BLUE).intValue();
        if(board.playExpertCard(1, testIsland, 0, List.of(Color.BLUE)))
            assertEquals(countYellows + 1, testIsland.getStudents().get(Color.BLUE).intValue());
        //else
            //assertEquals(countYellows, testIsland.getStudents().get(Color.YELLOW).intValue());

    }
}