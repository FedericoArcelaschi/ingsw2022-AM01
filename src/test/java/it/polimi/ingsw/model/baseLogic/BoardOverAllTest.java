package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.server.model.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import it.polimi.ingsw.server.model.exceptions.TooManyStudentsException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class BoardOverAllTest {
    @Test
    void playingWithBoard() throws PhaseNotRightException, NotYourTurnException {
        final var player1 = "giovanni";
        final var player2 = "lorenzo";
        final var turn = new Turn(List.of(player1, player2));
        //play card complete testing
        Board board = new Board(player1, player2, turn, RandomGenerator.getDefault().nextLong());
        board.playCard(player1, 2);
        turn.changePhase();
        assertThrowsExactly(NotYourTurnException.class, () -> board.playCard(player1, 5));
        assertThrowsExactly(IllegalArgumentException.class, () -> board.playCard(player2, 2));
        assertThrowsExactly(IllegalArgumentException.class, () -> board.playCard(player2, 10000000));
        board.playCard(player2, 1);
        turn.changePhase();
        assertThrowsExactly(PhaseNotRightException.class, () -> board.playCard(player2, 3));

        //move student
        try {
            List<StudentColor> studentColors =  board.getCastle(player2).waitingRoom.subList(0,3);
            board.moveStudentsToDiningRoom(player2, studentColors);
        } catch (NoSuchStudentException | TooManyStudentsException e) {
            fail();
        }
        assertThrowsExactly(NoSuchStudentException.class, () -> board.moveStudentsToDiningRoom(player2, List.of(StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN, StudentColor.GREEN)));
        //
        assertThrowsExactly(PhaseNotRightException.class, () -> board.moveMotherNature(100));
        turn.changePhase();
        assertThrowsExactly(IllegalArgumentException.class, () -> board.moveMotherNature(100));
        board.moveMotherNature(2);
        assertEquals(2, board.motherNaturePosition);


    }
}
