package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.expert.Characters.CharactersList;
import it.polimi.ingsw.model.expert.Characters.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;


public class ExpertBoardTest{
    public ExpertBoard board;

    public void setUp() throws Exception {
        Turn t = new Turn(Arrays.asList("Lorenzo", "Federico", "Giovanni"));
        board = new ExpertBoard("Lorenzo", "Federico", "Giovanni", t);
    }

    public void testPlayExpertCard() {
    }

    @Test
    public void testSetup4CharacterTesting() {
        board.setup4CharacterTesting(1);
        Assertions.assertTrue(board.getAvailableCharacterCards()
                        .contains(
                        new Student(1, board.getBag())
                        ));
    }

}