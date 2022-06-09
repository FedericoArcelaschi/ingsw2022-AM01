package it.polimi.ingsw.server.model.baseLogic;

import it.polimi.ingsw.communication.modelData.IslandData;
import it.polimi.ingsw.communication.modelData.ModelDataBuilder;
import it.polimi.ingsw.server.model.exceptions.NotYourTurnException;
import it.polimi.ingsw.server.model.exceptions.PhaseNotRightException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {

    Turn turn;
    Board board;

    @BeforeEach
    void setUp() {
        turn = new Turn(List.of("prova", "qwerty"));
        board = new Board("prova", "qwerty", turn, RandomGenerator.getDefault().nextLong());
    }

    @Test
    void moveMotherNatureTest() throws PhaseNotRightException, NotYourTurnException {
        System.out.println(turn);
        board.playCard("prova", 3);
        turn.changePhase();
        System.out.println(board.castleMap);
        board.playCard("qwerty", 10);
        turn.changePhase();
        //List<StudentColor> availableStudents = board.getCastle("prova").waitingRoom.subList(0, 3);
        //System.out.println(availableStudents);
        //board.moveStudentsToDiningRoom("prova", availableStudents);
        turn.changePhase();
        board.moveMotherNature(1);
        assertEquals(1, board.motherNaturePosition);
        System.out.println(ModelDataBuilder.newBoardData(board, board.getCurrentPlayer()));
    }

    @Test
    void joinIslands() {
        System.out.println( board.islandList);
        board.joinIslands(List.of(1, 2, 3));
        System.out.println(board.islandList);

    }
}