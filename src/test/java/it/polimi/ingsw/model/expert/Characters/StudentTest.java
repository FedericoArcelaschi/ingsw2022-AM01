package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.expert.*;
import it.polimi.ingsw.model.expert.ExpertIsland;
import junit.framework.TestCase;
import java.lang.management.PlatformLoggingMXBean;
import java.util.List;
import java.util.Map;

public class StudentTest extends TestCase {
    public void testApplyEffect4MONK() {
        ExpertBoard board = new ExpertBoard("pippo", "pluto");
        board.setup4CharacterTesting(1);

        int countYellows = testIsland.getStudents().get(Color.YELLOW);
        for (int i = 0; i < 4; i++) {
            System.out.println(board.getAvailableCharacterCards().get(i).idChar+ "\n");
        }
        board.playExpertCard(1, testIsland, 0, List.of(Color.YELLOW));
        assertEquals(countYellows + 1, testIsland.getStudents().get(Color.YELLOW).intValue());
    }
}