package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.expert.*;
import junit.framework.TestCase;

import java.lang.management.PlatformLoggingMXBean;
import java.util.List;

public class StudentTest extends TestCase {
    public void testApplyEffect4MONK() {
        ExpertBoard board = new ExpertBoard("pippo", "pluto");
        board.getExpIslandList();
        board.playExpertCard(1, board.getExpIslandList().get(1), null, 0, List.of(Color.YELLOW));
    }
}