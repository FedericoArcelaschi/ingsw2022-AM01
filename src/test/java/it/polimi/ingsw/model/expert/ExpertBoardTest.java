package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.expert.Characters.CharactersList;
import it.polimi.ingsw.model.expert.Characters.Student;
import junit.framework.TestCase;

public class ExpertBoardTest extends TestCase {
    public ExpertBoard board;

    public void setUp() throws Exception {
        board = new ExpertBoard("Lorenzo", "Federico", "Giovanni");
    }

    public void testPlayExpertCard() {
    }

    public void testSetup4CharacterTesting() {
        board.setup4CharacterTesting(1);
        assertTrue(board.getAvailableCharacterCards()
                        .contains(
                        new Student(1, board.getBag())
                        ));
    }

}