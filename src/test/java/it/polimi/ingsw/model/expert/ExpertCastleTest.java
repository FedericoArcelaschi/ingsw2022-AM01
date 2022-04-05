package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Team;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertCastleTest extends TestCase {

    public void testAddStudentDR_testpayCharacter() {

        List<String> players = Arrays.asList("pippo","pluto","paperino");
        ExpertBoard board = new ExpertBoard("pippo","pluto","paperino");
        ExpertCastle justACastle = board.getExpCastleMap().get("pippo");

        List<Color> yellows_3 = Arrays.asList(Color.YELLOW,Color.YELLOW,Color.YELLOW);

        assertTrue(justACastle.payCharacter(1));
        assertFalse(justACastle.payCharacter(1));

        justACastle.addStudentDR(yellows_3);
        assertTrue(justACastle.payCharacter(1));
        assertFalse(justACastle.payCharacter(1));
    }
}