package it.polimi.ingsw.model.expert.Characters;

import it.polimi.ingsw.model.BoardFactory;
import it.polimi.ingsw.model.Castle;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.model.exceptions.TooManyStudentsException;
import it.polimi.ingsw.model.expert.ExpertBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test for sixth character
 */
class InfluenceTestCentaur {

    private ExpertBoard board;

    @BeforeEach
    void setUp() {
        Turn t = new Turn(Arrays.asList("piero", "angela"));
        board = (ExpertBoard) BoardFactory.getBoard(Arrays.asList("piero", "angela"), true, t);
    }

    @Test
    void applyEffect() throws NoSuchStudentException, NotYourTurnException, TooManyStudentsException {
        board.extract4CharacterTesting(6);
        List<Color> students = board.getCastle("piero").getWaitingRoom();
        board.moveStudentToDiningRoom("piero", students);
        //TODO: add 2 oins to the castle to invoke the method is unpleasant
    }
}